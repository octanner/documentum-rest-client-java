/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.emc.documentum.rest.client.sample.client.DCTMRestClient;
import com.emc.documentum.rest.client.sample.client.DCTMRestErrorException;
import com.emc.documentum.rest.client.sample.client.converter.MultipartBatchHttpMessageConverter;
import com.emc.documentum.rest.client.sample.client.util.Debug;
import com.emc.documentum.rest.client.sample.client.util.Headers;
import com.emc.documentum.rest.client.sample.client.util.SupportedMediaTypes;
import com.emc.documentum.rest.client.sample.client.util.UriHelper;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.HomeDocument;
import com.emc.documentum.rest.client.sample.model.LinkRelation;
import com.emc.documentum.rest.client.sample.model.Linkable;
import com.emc.documentum.rest.client.sample.model.Repository;
import com.emc.documentum.rest.client.sample.model.RestError;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.batch.Batch;
import com.emc.documentum.rest.client.sample.model.batch.Operation;

import static com.emc.documentum.rest.client.sample.client.util.Headers.ACCEPT_ATOM_HEADERS;
import static com.emc.documentum.rest.client.sample.client.util.Headers.ACCEPT_JSON_HEADERS;
import static com.emc.documentum.rest.client.sample.client.util.Headers.ACCEPT_JSON_HEADERS_WITH_CONTENT;
import static com.emc.documentum.rest.client.sample.client.util.Headers.ACCEPT_JSON_HEADERS_WITH_MULTIPART_CONTENT;
import static com.emc.documentum.rest.client.sample.client.util.Headers.ACCEPT_XML_HEADERS;
import static com.emc.documentum.rest.client.sample.client.util.Headers.ACCEPT_XML_HEADERS_WITH_CONTENT;
import static com.emc.documentum.rest.client.sample.client.util.Headers.ACCEPT_XML_HEADERS_WITH_MULTIPART_CONTENT;
import static com.emc.documentum.rest.client.sample.client.util.Headers.JSON_CONTENT;
import static com.emc.documentum.rest.client.sample.client.util.Headers.XML_CONTENT;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.BATCHES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.EDIT;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SELF;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

/**
 * the basic implementation with Spring RestTemplate
 */
@NotThreadSafe
public abstract class AbstractRestTemplateClient implements DCTMRestClient {
    protected static final String CSRF_HEADER_NAME_HEADER = "DOCUMENTUM-CSRF-HEADER-NAME";
    protected static final String CLIENT_TOKEN_NAME = "DOCUMENTUM-CLIENT-TOKEN";
    
    protected final RestTemplate restTemplate = new RestTemplate();
    
    protected final String contextRoot;
    protected final boolean useFormatExtension;
    protected final String repositoryName;
    protected final String username;
    protected final String password;
    protected HomeDocument homeDocument;
    protected RestObject productInfo;
    protected Feed<Repository> repositories;
    protected Repository repository;
    protected boolean enableStreaming = false;
    protected boolean debug;
    protected boolean enableCSRFClientToken = true;
    
    protected HttpHeaders headers;
    protected HttpStatus status;
    protected RequestProcessor requestProcessor;
    protected String clientToken;
    protected String csrfHeader;
    protected String csrfToken;
    
    protected final RequestProcessor defaultRequestProcessor = new DefaultRequestProcessor();
    
    public AbstractRestTemplateClient(String contextRoot, String repositoryName, String username, String password, boolean useFormatExtension) {
        this.contextRoot = contextRoot;
        this.repositoryName = repositoryName;
        this.username = username;
        this.password = password;
        this.useFormatExtension = useFormatExtension;
        initRestTemplate(restTemplate);
    }
    
    /**
     * HomeDocument resource uri is the only fixed uri
     * it must be: "Rest context path"/services
     * e.g.
     * http://localhost:8080/dctm-rest/services 
     * @return the HomeDocument resource uri
     */
    protected String getHomeDocumentUri() {
        String servicesUri = contextRoot + "/services";
        if(useFormatExtension) {
            switch(getClientType()) {
                case XML:
                    servicesUri += ".xml";
                    break;
                case JSON:
                    servicesUri += ".json";
                    break;
            }
        }
        return servicesUri;
    }
    
    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
    
    @Override
    public HttpStatus getStatus() {
        return status;
    }
    
    public AbstractRestTemplateClient debug(boolean debug) {
        this.debug = debug;
        return this;
    }
    
    protected void initRestTemplate(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new MultipartBatchHttpMessageConverter());
    }
    
    public abstract ClientType getClientType();
    
    private void setupHttp(ResponseEntity<?> entity) {
        if(entity == null) {
            headers = null;
            status = null;
        } else {
            headers = entity.getHeaders();
            status = entity.getStatusCode();
            setupCSRFClientToken(headers);
            if(debug) {
                Debug.debug("Response status: " + getStatus());
                Debug.debug("Response headers: " + getHeaders());
            }
        }
    }
    
    private void setupHttp(DCTMRestErrorException exception) {
        if(exception == null) {
            headers = null;
            status = null;
        } else {
            headers = exception.getHeaders();
            status = exception.getStatus();
            setupCSRFClientToken(headers);
            if(debug) {
                Debug.debug("Response status: " + getStatus());
                Debug.debug("Response headers: " + getHeaders());
            }
        }        
    }
    
    private void setupCSRFClientToken(HttpHeaders headers) {
        if(enableCSRFClientToken) {
            String cookie = headers.getFirst(HttpHeaders.SET_COOKIE);
            if(cookie != null) {
                if(CLIENT_TOKEN_NAME.equals(cookie.substring(0, cookie.indexOf('=')))) {
                    clientToken = cookie.substring(cookie.indexOf('=') + 1, cookie.indexOf(','));
                    if(debug) {
                        Debug.debug(CLIENT_TOKEN_NAME + "=" + clientToken);
                    }
                }
            }
            if(headers.containsKey(CSRF_HEADER_NAME_HEADER)) {
                csrfHeader = headers.getFirst(CSRF_HEADER_NAME_HEADER);
                if(csrfHeader != null) {
                    csrfToken = headers.getFirst(csrfHeader);
                    if(debug) {
                        Debug.debug(csrfHeader + "=" + csrfToken);
                    }
                }
            }
        }
    }
    
    private HttpHeaders setupAuthentication(HttpHeaders headers) {
        HttpHeaders result = headers;
        if(enableCSRFClientToken && clientToken != null) {
            result = new HttpHeaders();
            result.putAll(headers);
            //the cookie is already processed by HttpComponentsClientHttpRequestFactory, need not to set client token header
            //otherwise, the header has to be set (or cookie)
            result.set(CLIENT_TOKEN_NAME, clientToken);
            if(debug) {
                Debug.debug("Authenticate with " + CLIENT_TOKEN_NAME + "=" + clientToken);
            }
            if(csrfHeader != null && csrfToken != null) {
                result.set(csrfHeader, csrfToken);
                if(debug) {
                    Debug.debug("Authenticate with csrf " + csrfHeader + "=" + csrfToken);
                }
            }
        } else if(username != null && password != null) {
            result = new HttpHeaders();
            result.putAll(headers);
            String usernameAndPassword = username + ":" + password;
            result.set(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.encodeBase64(usernameAndPassword.getBytes())));
            if(debug) {
                Debug.debug("Authenticate with Basic " + new String(Base64.encodeBase64(usernameAndPassword.getBytes())));
            }
        }
        return result;
    }
        
    /**
     * send the request and process the response by Spring RestTemplate
     * @param uri the resource uri
     * @param httpMethod the HTTP method, e.g. GET, POST, DELETE...
     * @param headers the request http headers
     * @param requestBody the request http body
     * @param responseBodyClass the class to represent response
     * @param params request parameters
     * @param <T> the response type
     * @return the response
     */
    protected <T> T sendRequest(String uri,
                             HttpMethod httpMethod,
                             HttpHeaders headers,
                             Object requestBody,
                             Class<T> responseBodyClass,
                             String... params) {
        if(debug) {
            Debug.debug("Resource URI: " + uri);
            Debug.debug("HTTP method: " + httpMethod);
            Debug.debug("Request headers: " + headers);
            Debug.debugObject(requestBody);
            if(responseBodyClass != null) {
                Debug.debug("Expected response body class: " + responseBodyClass.getSimpleName());
            }
            if(params != null) {
                Debug.debug("Request parameters: " + UriHelper.queryString(params));
            }
        }
        RequestProcessor processor = requestProcessor == null ? defaultRequestProcessor : requestProcessor;
        requestProcessor = null;
        if(uri == null || uri.length() == 0) {
            throw new IllegalStateException("The resource URI is empty, or you do not have priviledge");
        }
        uri = UriHelper.decode(uri);
        headers = setupAuthentication(headers);
        HttpEntity<Object> requestEntity = requestBody == null ? 
                new HttpEntity<Object>(headers) :
                new HttpEntity<Object>(requestBody, headers);
        String requestUri = UriHelper.appendQueryString(uri, params);
        if(debug) {
            Debug.debug("Sending " + httpMethod + " request to " + uri);
        }
        ResponseEntity<T> entity = null;
        if(enableStreaming) {
            ClientHttpRequestFactory factory = restTemplate.getRequestFactory();
            if(factory instanceof SimpleClientHttpRequestFactory) {
                ((SimpleClientHttpRequestFactory)factory).setBufferRequestBody(false);
                if(debug) {
                    Debug.debug("Enable streaming mode for request body transfer");
                }
            } else if(factory instanceof HttpComponentsClientHttpRequestFactory) {
                ((HttpComponentsClientHttpRequestFactory)factory).setBufferRequestBody(false);
                if(debug) {
                    Debug.debug("Enable streaming mode for request body transfer");
                }
            }
        }
        try {
            entity = processor.process(requestUri, httpMethod, requestEntity, responseBodyClass);
            setupHttp(entity);
        } catch(DCTMRestErrorException e) {
            setupHttp(e);
            RestError error = e.getError();
            if(error != null) {
                Debug.error("status [" + error.getStatus() + "]");
                Debug.error("code [" + error.getCode() + "]");
                Debug.error("message [" + error.getMessage() + "]");
                Debug.error("detail [" + error.getDetails() + "]");
                Debug.error("id [" + error.getId() + "]");
            }
            throw e;
        } finally {
            if(enableStreaming) {
                enableStreaming = false;
                ClientHttpRequestFactory factory = restTemplate.getRequestFactory();
                if(factory instanceof SimpleClientHttpRequestFactory) {
                    ((SimpleClientHttpRequestFactory)factory).setBufferRequestBody(true);
                    if(debug) {
                        Debug.debug("Disable streaming mode for request body transfer");
                    }
                } else if(factory instanceof HttpComponentsClientHttpRequestFactory) {
                    ((HttpComponentsClientHttpRequestFactory)factory).setBufferRequestBody(true);
                    if(debug) {
                        Debug.debug("Disable streaming mode for request body transfer");
                    }
                }
            }
        }
        return entity == null ? null : entity.getBody();
    }
    
    @Override
    public double getMajorVersion() {
        try {
            return Double.parseDouble((String)getProductInfo().getProperties().get("major"));
        } catch(Exception e) {
            return 7.2;
        }
    }

    /**
     * construct a new RestObject based on oldObject's class with new properties
     * @param oldObject the old object
     * @param newObject the new object
     * @return new object instance
     */
    protected RestObject newRestObject(RestObject oldObject, RestObject newObject) {
        try {
            return oldObject.getClass().getConstructor(RestObject.class).newInstance(newObject);
        } catch (Exception e) {
            throw new IllegalArgumentException(oldObject.getClass().getName());
        }
    }
    
    @Override
    public RestObject createObject(Linkable parent, RestObject objectToCreate, Object content, String contentMediaType, String... params) {
        return createObject(parent, LinkRelation.OBJECTS, objectToCreate, content, contentMediaType, params);
    }

    @Override
    public RestObject createObject(Linkable parent, RestObject objectToCreate) {
        return createObject(parent, LinkRelation.OBJECTS, objectToCreate, null, null);
    }
    
    @Override
    public RestObject createObject(Linkable parent, LinkRelation rel, RestObject objectToCreate) {
        return createObject(parent, rel, objectToCreate, null, null);
    }
    
    @Override
    public RestObject createDocument(Linkable parent, RestObject objectToCreate) {
        return createDocument(parent, objectToCreate, null, null);
    }

    protected <T> T get(String uri, HttpHeaders headers, Class<? extends T> responseBodyClass, String... params) {
        return sendRequest(uri, GET, headers, null, responseBodyClass, params);
    }
    
    protected <T> T get(String uri, boolean isCollection, Class<? extends T> responseBodyClass, String... params) {
        return get(uri, isXml()?(isCollection?ACCEPT_ATOM_HEADERS:ACCEPT_XML_HEADERS):ACCEPT_JSON_HEADERS, responseBodyClass, params);
    }
    
    public <T> T get(String uri, Class<T> clazz, String... params) {
        return get(uri, false, clazz, params);
    }

    public void delete(String uri, String... params) {
        sendRequest(uri, DELETE, isXml()?ACCEPT_XML_HEADERS:ACCEPT_JSON_HEADERS, null, null, params);
    }
    
    @Override
    public byte[] getContentBytes(String uri) {
        return get(uri, byte[].class);
    }

    @Override
    public void delete(Linkable linkable, String... params) {
        if(linkable.getHref(LinkRelation.DELETE) != null) {
            delete(linkable.getHref(LinkRelation.DELETE), params);
        } else if(linkable.getHref(SELF) != null) {
            delete(linkable.getHref(SELF), params);
        } else if(linkable.getHref(EDIT) != null) {
            delete(linkable.getHref(EDIT), params);
        } else {
            throw new IllegalArgumentException(String.valueOf(linkable));
        }
    }
    
    protected <T> T post(String uri, Object body, Class<? extends T> responseBodyClass, String... params) {
        return sendRequest(uri, POST, isXml()?ACCEPT_XML_HEADERS_WITH_CONTENT:ACCEPT_JSON_HEADERS_WITH_CONTENT, body, responseBodyClass, params);
    }
    
    protected <T> T post(String uri, Object content, String contentMediaType, Class<? extends T> responseBodyClass, String... params) {
        return sendRequest(uri, POST, new Headers().accept(isXml()?SupportedMediaTypes.APPLICATION_VND_DCTM_XML_VALUE:SupportedMediaTypes.APPLICATION_VND_DCTM_JSON_VALUE).contentType(contentMediaType).toHttpHeaders(),
                content, responseBodyClass, params);
    }
    
    protected <T> T put(String uri, Class<? extends T> responseBodyClass, String... params) {
        return sendRequest(uri, PUT, isXml()?ACCEPT_XML_HEADERS:ACCEPT_JSON_HEADERS, null, responseBodyClass, params);
    }
    
    @Override
    public RestObject update(RestObject oldObject, LinkRelation rel, RestObject newObject, HttpMethod method, String... params) {
        try {
            RestObject newRestObject = newRestObject(oldObject, newObject);
            if(method == PUT) {
                return put(oldObject.getHref(rel), newRestObject, newRestObject.getClass(), params);
            } else  {
                return post(oldObject.getHref(rel), newRestObject, newRestObject.getClass(), params);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(oldObject.getClass().getName());
        }
    }
    
    protected <T> T put(String uri, Object body, Class<? extends T> responseBodyClass, String... params) {
        return sendRequest(uri, PUT, isXml()?ACCEPT_XML_HEADERS_WITH_CONTENT:ACCEPT_JSON_HEADERS_WITH_CONTENT, body, responseBodyClass, params);
    }
    
    protected <T> T post(String uri, T object, Object content, String contentMediaType, Class<? extends T> responseBodyClass, String... params) {
        T t = null;
        if(content == null) {
            t = post(uri, object, responseBodyClass, params);
        } else if(object != null && content != null) {
            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
            parts.add("metadata", new HttpEntity<Object>(object, isXml()?XML_CONTENT:JSON_CONTENT));
            parts.add("binary", contentMediaType==null?content:new HttpEntity<Object>(content, new Headers().contentType(contentMediaType).toHttpHeaders()));
            t = sendRequest(uri, POST, isXml()?ACCEPT_XML_HEADERS_WITH_MULTIPART_CONTENT:ACCEPT_JSON_HEADERS_WITH_MULTIPART_CONTENT, parts, responseBodyClass, params);
        }
        return t;
    }
    
    protected Batch post(Batch batch, Class<? extends Batch> responseBodyClass) {
        Batch result = null;
        if(batch.hasAttachment()) {
            MultiValueMap<String, HttpEntity<InputStream>> parts = new LinkedMultiValueMap<String, HttpEntity<InputStream>>();
            HttpHeaders batchHeaders = new Headers().
                    contentType(isXml() ?
                            String.format("%s;type=\"%s\"",
                                SupportedMediaTypes.APPLICATION_XOP_STRING, SupportedMediaTypes.APPLICATION_VND_DCTM_XML_VALUE):
                            String.format("%s;type=\"%s\"",
                                SupportedMediaTypes.APPLICATION_JOP_STRING, SupportedMediaTypes.APPLICATION_VND_DCTM_JSON_VALUE)).
                    header("Content-ID", "batch").
                    header("Content-disposition", "form-data; name=batch").
                    toHttpHeaders();
            parts.add("batch", new HttpEntity<InputStream>(toInputStream(batch), batchHeaders));
            for(Operation op : batch.getOperations()) {
                if(op.getRequest().getAttachment() != null) {
                    HttpHeaders opHeaders = new Headers().
                            contentType(op.getRequest().getAttachment().getContentType()).
                            header("Content-ID", op.getRequest().getAttachment().getInclude().getRawHref()).
                            header("Content-disposition", "form-data; name=" + op.getRequest().getAttachment().getInclude().getRawHref()).
                            toHttpHeaders();
                    parts.add(op.getRequest().getAttachment().getInclude().getRawHref(), new HttpEntity<InputStream>(op.getRequest().getAttachment().getContentStream(), opHeaders));
                }
            }
            HttpHeaders relatedHeaders = new Headers().accept(isXml()?
                    SupportedMediaTypes.APPLICATION_VND_DCTM_XML_VALUE :
                    SupportedMediaTypes.APPLICATION_VND_DCTM_JSON_VALUE).
                    contentType(String.format("%s;type=\"%s\";start=\"batch\";start-info=\"%s\"",
                            SupportedMediaTypes.MULTIPART_RELATED_STRING,
                            isXml() ?
                                    SupportedMediaTypes.APPLICATION_XOP_STRING :
                                    SupportedMediaTypes.APPLICATION_JOP_STRING,
                            isXml() ?
                                    SupportedMediaTypes.APPLICATION_VND_DCTM_XML_VALUE :
                                    SupportedMediaTypes.APPLICATION_VND_DCTM_JSON_VALUE)).
                    toHttpHeaders();
            result = sendRequest(getRepository().getHref(BATCHES), POST, relatedHeaders, parts, responseBodyClass);
        } else {
            result = post(getRepository().getHref(BATCHES), batch, responseBodyClass);
        }
        return result;
    }
    
    public abstract void serialize(Object object, OutputStream os);

    private InputStream toInputStream(Object o) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(50*1024);
        serialize(o, os);
        return new ByteArrayInputStream(os.toByteArray());
    }
    
    @Override
    public DCTMRestClient enableStreamingForNextRequest() {
        this.enableStreaming = true;
        return this;
    }

    public DCTMRestClient setRequestProcessor(RequestProcessor processor) {
        requestProcessor = processor;
        return this;
    }
    
    public boolean isXml() {
        return ClientType.XML == getClientType();
    }
    
    public boolean isJson() {
        return ClientType.JSON == getClientType();
    }

    public static enum ClientType {
        XML, JSON
    }
    
    public interface RequestProcessor {
        <T> ResponseEntity<T> process(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType);
    }
    
    public AbstractRestTemplateClient enableCSRFClientToken(boolean enableCSRFClientToken) {
        this.enableCSRFClientToken = enableCSRFClientToken;
        resetCSRFClientToken();
        return this;
    }
    
    public void resetCSRFClientToken() {
        this.clientToken = null;
        this.csrfHeader = null;
        this.csrfToken = null;
    }
    
    public AbstractRestTemplateClient ignoreAuthenticateServer() {
        if(restTemplate.getRequestFactory() instanceof HttpComponentsClientHttpRequestFactory) {
            try {
                SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build());
                HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
                ((HttpComponentsClientHttpRequestFactory)restTemplate.getRequestFactory()).setHttpClient(httpClient);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Debug.error("the request factory " + restTemplate.getRequestFactory().getClass().getName() + " does not support ignoreAuthenticateServer");
        }
        return this;
    }

    private class DefaultRequestProcessor implements RequestProcessor {
        @Override
        public <T> ResponseEntity<T> process(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) {
            return restTemplate.exchange(url, method, requestEntity, responseType);
        }
    }
}
