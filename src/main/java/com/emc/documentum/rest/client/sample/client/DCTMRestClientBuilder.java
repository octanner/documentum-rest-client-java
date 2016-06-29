/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client;

import com.emc.documentum.rest.client.sample.client.impl.AbstractRestTemplateClient;
import com.emc.documentum.rest.client.sample.client.impl.jackson.DCTMJacksonClient;
import com.emc.documentum.rest.client.sample.client.impl.jaxb.DCTMJaxbClient;
import com.emc.documentum.rest.client.sample.client.util.Debug;

import static com.emc.documentum.rest.client.sample.client.util.Reader.read;

/**
 * build a DCTMRestClient with parameters
 */
public final class DCTMRestClientBuilder {
    private String contextRoot;
    private String repository;
    private String username;
    private String password;
    private DCTMRestClientBinding binding;
    private boolean useFormatExtension;
    private boolean debug;
    private boolean ignoreAuthenticateServer;
    
    public static DCTMRestClient buildWithPrompt() {
        StringBuilder sb = new StringBuilder();
        sb.append("Please input the client representation type: ");
        for(DCTMRestClientBinding b : DCTMRestClientBinding.values()) {
            sb.append(b.name() + " ");
        }
        String bindingStr = read(sb.toString(), "XML");
        DCTMRestClientBinding binding = DCTMRestClientBinding.valueOf(bindingStr.toUpperCase());
        String contextRoot = read("Please input the REST context path:", "http://localhost:8080/dctm-rest");
        boolean ignoreAuthenticateServer = ignoreAuthenticateServer(contextRoot);
        String repository = read("Please input the repository name:");
        String username = read("Please input the username:");
        String password = read("Please input the password:");
        String useFormatExtension = read("Please input the whether add format extension .xml or .json for URI:", "false");
        String debug = read("Please input whether print debug information:", "false");
        AbstractRestTemplateClient client = (AbstractRestTemplateClient)new DCTMRestClientBuilder().
                   bind(binding).
                   contextRoot(contextRoot).
                   ignoreAuthenticateServer(ignoreAuthenticateServer).
                   credentials(username, password).
                   repository(repository).
                   useFormatExtension("true".equalsIgnoreCase(useFormatExtension)).
                   debug("true".equalsIgnoreCase(debug)).build();
        client.getHomeDocument();
        if(client.getMajorVersion() >= 7.2) {
            String msg = client.getMajorVersion() >= 7.3?"Please input whether enable the client token authentication with the server based CSRF:":"Please input whether enable the client token authentication:";
            boolean enableCSRFClientToken = "true".equalsIgnoreCase(read(msg, "true"));
            client.enableCSRFClientToken(enableCSRFClientToken);
            if("true".equalsIgnoreCase(debug)) {
                Debug.debug("Enable CSRF Client Token=" + enableCSRFClientToken);
            }
        }
        return client;
    }
    
    private static boolean ignoreAuthenticateServer(String contextRoot) {
        if("https".equalsIgnoreCase(contextRoot.substring(0, 5))) {
            if(System.getProperty("javax.net.ssl.trustStore") == null) {
                String useTrustStore = read("Please input whether authenticate the rest server:", "false");
                if("true".equalsIgnoreCase(useTrustStore)) {
                    if(System.getProperty("javax.net.ssl.trustStore") == null) {
                        String trustStore = read("Please input the trust store path:");
                        String password = read("Please input the password of the trust store:");
                        System.setProperty("javax.net.ssl.trustStore", trustStore);
                        System.setProperty("javax.net.ssl.trustStorePassword", password);
                    }
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }
    
    /**
     * the context root the REST service
     * e.g.
     * http://localhost:8080/dctm-rest
     * @param contextRoot
     * @return
     */
    public DCTMRestClientBuilder contextRoot(String contextRoot) {
        this.contextRoot = contextRoot.endsWith("/")?contextRoot.substring(0, contextRoot.length()-1):contextRoot;
        return this;
    }
    
    /**
     * @param repository the repository name
     * @return
     */
    public DCTMRestClientBuilder repository(String repository) {
        this.repository = repository;
        return this;
    }
    
    /**
     * @param binding binding type
     * @return
     */
    public DCTMRestClientBuilder bind(DCTMRestClientBinding binding) {
        this.binding = binding;
        return this;
    }
    
    /**
     * whether append .xml or .json extension to the uri
     * e.g.
     * http://localhost:8080/dctm-rest/services.xml
     * or
     * http://localhost:8080/dctm-rest/services.json
     * @param useFormatExtension
     * @return
     */
    public DCTMRestClientBuilder useFormatExtension(boolean useFormatExtension) {
        this.useFormatExtension = useFormatExtension;
        return this;
    }
    
    /**
     * whether print debug information
     * @param debug
     * @return
     */
    public DCTMRestClientBuilder debug(boolean debug) {
        this.debug = debug;
        return this;
    }
    
    /**
     * @param username
     * @param password
     * @return
     */
    public DCTMRestClientBuilder credentials(String username, String password) {
        this.username = username;
        this.password = password;
        return this;
    }
    
    public DCTMRestClientBuilder ignoreAuthenticateServer(boolean ignoreAuthenticateServer) {
        this.ignoreAuthenticateServer = ignoreAuthenticateServer;
        return this;
    }
    
    public DCTMRestClient build() {
        DCTMRestClient client = null;
        switch(binding) {
            case XML:
                if(debug) {
                    Debug.debug("Build DCTMRestClient with JAXB implementation");
                }
                client = new DCTMJaxbClient(contextRoot, repository, username, password, useFormatExtension).debug(debug).ignoreAuthenticateServer(ignoreAuthenticateServer);
                break;
            case JSON:
                if(debug) {
                    Debug.debug("Build DCTMRestClient with Jackson implementation");
                }
                client = new DCTMJacksonClient(contextRoot, repository, username, password, useFormatExtension).debug(debug).ignoreAuthenticateServer(ignoreAuthenticateServer);
                break;
            default:
                throw new IllegalArgumentException(binding + " binding is not supported yet");    
        }
        if(debug) {
            Debug.debug("Context Root=" + contextRoot);
            Debug.debug("Repository=" + repository);
            Debug.debug("User Name=" + username);
            Debug.debug("Password=" + password);
            Debug.debug("Use Format Extension=" + useFormatExtension);
        }
        return client;
    }
}
