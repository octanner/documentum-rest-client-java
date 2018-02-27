/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.util;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.xml.transform.Source;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import com.emc.documentum.rest.client.sample.client.DCTMRestClient;
import com.emc.documentum.rest.client.sample.client.impl.AbstractRestTemplateClient;
import com.emc.documentum.rest.client.sample.model.AuditPolicy;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Facet;
import com.emc.documentum.rest.client.sample.model.FacetValue;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.LinkRelation;
import com.emc.documentum.rest.client.sample.model.Linkable;
import com.emc.documentum.rest.client.sample.model.ObjectLifecycle;
import com.emc.documentum.rest.client.sample.model.Permission;
import com.emc.documentum.rest.client.sample.model.PermissionSet;
import com.emc.documentum.rest.client.sample.model.Preference;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.SavedSearch;
import com.emc.documentum.rest.client.sample.model.Search;
import com.emc.documentum.rest.client.sample.model.Search.Searchable;
import com.emc.documentum.rest.client.sample.model.SearchEntry;
import com.emc.documentum.rest.client.sample.model.SearchFeed;
import com.emc.documentum.rest.client.sample.model.SearchTemplate;
import com.emc.documentum.rest.client.sample.model.batch.Batch;
import com.emc.documentum.rest.client.sample.model.batch.Operation;

public class Debug {
    private static final String NEWLINE = System.getProperty("line.separator");
    
    public static void debug(String msg) {
        System.out.println("  [debug] " + msg);
    }
    
    public static void error(String msg) {
        System.err.println("  [error] " + msg);
    }
    
    @SuppressWarnings("unchecked")
    public static void debugObject(Object object) {
        if(object instanceof RestObject) {
            debugRestObject((RestObject)object);
        } else if(object instanceof MultiValueMap) {
            for(List<Object> list : ((MultiValueMap<String, Object>)object).values()) {
                for(Object o : list) {
                    if(o instanceof RestObject) {
                        debugRestObject((RestObject)o);
                    } else if(o instanceof HttpEntity){
                        Object oo = ((HttpEntity<?>)o).getBody();
                        if(oo instanceof RestObject) {
                            debugRestObject((RestObject)oo);
                        } else {
                            debugContent(oo);
                        }
                    } else {
                        debugContent(o);
                    }
                }
            }
        } else {
            debugContent(object);
        }
    }
    
    public static void debugSerialize(DCTMRestClient client, Object object) {
        if(client instanceof AbstractRestTemplateClient) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ((AbstractRestTemplateClient)client).serialize(object, os);
            System.out.println(os);
        }
    }
    
    public static void debugRestObject(RestObject object) {
        if(object != null) {
            debug("RestObject: type=" + object.getType() + ", properties=" + object.getProperties());
        }
    }

    public static void debugContent(Object content) {
        if(content != null) {
            if(content instanceof String) {
                debug("Binary Content: " + content);
            } else if(content instanceof byte[]) {
                byte[] bytes = (byte[])content;
                StringBuilder sb = new StringBuilder();
                for(byte b : bytes) {
                    sb.append(Integer.toHexString(b));
                }
                debug("Binary Content: size=" + bytes.length + ", content=" + sb);
            } else if(content instanceof Source) {
                debug("Binary Content: javax.xml.transform.Source");
            } else if(content instanceof Resource) {
                debug("Binary Content: org.springframework.core.io.Resource");
            } else{
                debug("Binary Content: " + content.getClass().getName());
            }
        }
    }
    
    public static void printEntryContentSrc(Feed<?> feed) {
        if(feed.getEntries() != null) {
            for(Entry<?> e : feed.getEntries()) {
                System.out.println(e.getTitle() + " -> " + e.getContentSrc());
            }
        } else {
            System.out.println("no entries");
        }
    }
    
    public static void printEntry(Feed<?> feed, String... properties) {
        for(Entry<?> e : feed.getEntries()) {
            printFields(e, properties);
        }
    }
    
    public static void printEntryContent(Feed<?> feed, String... properties) {
        for(Entry<?> e : feed.getEntries()) {
            if(e.getContentObject() instanceof RestObject) {
                print((RestObject)e.getContentObject(), properties);
            } else {
                printFields(e.getContentObject(), properties);
            }
        }
    }
    
    public static void printSearchFeed(SearchFeed<RestObject> result) {
        if(result.getEntries() != null) {
            for(SearchEntry<RestObject> e : result.getEntries()) {
                if(e.getContentSrc() != null) {
                    System.out.println(e.getTitle() + " -> " + e.getContentSrc());
                } else {
                    print(e.getContentObject());
                }
                System.out.println("score:" + e.getScore() + ", terms:" + e.getTerms());
            }
        } else {
            System.out.println("no search result");
        }
        
        if(result.getFacets() != null) {
            for(Facet f : result.getFacets()) {
                System.out.println("facet id:" + f.getId() + ", facet label:" + f.getLabel());
                for(FacetValue fv : f.getValues()) {
                    System.out.println("facet-id:" + fv.getFacetId() + ", facet-value-id:" + fv.getId()
                        + ", facet-value-count:" + fv.getCount() + "facet-value-constraint:" + fv.getConstraint());
                    print(fv);
                }
            }
        }
    }

    public static void print(RestObject object) {
        print(object, "r_object_id", "object_name", "r_object_type");
    }
    
    public static void print(RestObject object, String... properties) {
        StringBuilder sb = new StringBuilder();
        for(String p : properties) {
            if(sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(p.replaceFirst("^r_", "").replaceAll("_", " ")).append(':').append(object.getProperties().get(p));
        }
        System.out.println(sb);
    }
    
    public static void printFields(Collection<?> objects, String... fields) {
        printFields(objects, false, fields);
    }
    
    public static void printFields(Collection<?> objects, boolean ignoreNull, String... fields) {
        if(objects != null) {
            for(Object o : objects) {
                printFields(o, ignoreNull, fields);
            }
        }
    }
    
    public static void printFields(Object object, String... fields) {
        printFields(object, false, fields);
    }
    
    public static void printFields(Object object, boolean ignoreNull, String... fields) {
        if(object == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        if(fields == null || fields.length == 0) {
            fields = new String[object.getClass().getDeclaredFields().length];
            for(int i=0;i<fields.length;++i) {
                fields[i] = object.getClass().getDeclaredFields()[i].getName();
            }
            Arrays.sort(fields);
        }
        for(String p : fields) {
            try {
                Field f = getField(object.getClass(), p);
                if(f != null) {
                    Object result = f.get(object);
                    if(!ignoreNull || result != null) {
                        if(sb.length() > 0) {
                            sb.append(", ");
                        }
                        sb.append(p.replaceAll("([A-Z])", " $1").toLowerCase()).append(':').append(result);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalArgumentException(p);
            }
        }
        System.out.println(sb);
    }
    
    private static Field getField(Class<?> clazz, String field) {
        if(clazz == null || field == null) {
            return null;
        }
        try {
            Field f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            return f;
        } catch(NoSuchFieldException no) {
            return getField(clazz.getSuperclass(), field);
        }
    }
    
    public static void print(Batch batch) {
        System.out.println((batch.getDescription()==null?"":(batch.getDescription()+" ")) + "started: " + batch.getStarted() + ", finished: " + batch.getFinished() + ", state: " + batch.getState() + (batch.getSubstate() == null ? "" : (", substate: " + batch.getSubstate())));
    }
    
    public static void print(Batch batch, boolean printRequest, boolean printResponse) {
        print(batch);
        if(batch.getOperations() != null) {
            for(Operation o : batch.getOperations()) {
                System.out.println("id: " + o.getId() + ", started: " + o.getStarted() + ", finished: " + o.getFinished() + ", state: " + o.getState());
                if(printRequest && o.getRequest() != null) {
                    System.out.println("\trequest: " + o.getRequest().getMethod() + " " + o.getRequest().getUri().substring(o.getRequest().getUri().lastIndexOf('/')+1) + ", headers: " + o.getRequest().getHeaders());
                }
                if(printResponse && o.getResponse() != null) {
                    System.out.println("\tresponse: " + o.getResponse().getStatus() + ", headers: " + o.getResponse().getHeaders());
                    if(!StringUtils.isEmpty(o.getResponse().getEntity())) {
                        System.out.println("\t\t" + o.getResponse().getEntity());
                    }
                }
            }
        }
    }
    
    public static void print(Preference preference) {
        System.out.println("client: " + preference.getClient() + ", title: " + preference.getTitle() + ", subject: " + preference.getSubject() + ", keywords: " + preference.getKeywords()); 
        System.out.println("preference: " + preference.getPreference());
    }
    
    public static void print(Permission permission) {
        System.out.println("accessor: " + permission.getAccessor() + ", basic: " + permission.getBasicPermission() + ", extended: " + permission.getExtendPermissions() + ", application: " + permission.getApplicationPermission());
    }
    
    public static void print(PermissionSet permissionSet) {
        if(permissionSet.getPermitted() != null) {
            System.out.println("permitted:"); 
            for(Permission p : permissionSet.getPermitted()) {
                print(p);
            }
        }
        if(permissionSet.getRestricted() != null) {
            System.out.println("restricted:");
            for(Permission p : permissionSet.getRestricted()) {
                print(p);
            }
        }
        if(permissionSet.getRequiredGroup() != null) {
            System.out.println("required group: " + permissionSet.getRequiredGroup());
        }
        if(permissionSet.getRequiredGroupSet() != null) {
            System.out.println("required group set: " + permissionSet.getRequiredGroupSet());
        }
    }
    
    public static void print(DCTMRestClient client, SavedSearch savedSearch) {
        print(savedSearch, "r_object_id", "object_name", "r_object_type", "r_is_public");
        printSearchable(client, savedSearch);
    }
    
    public static void print(DCTMRestClient client, SearchTemplate searchTemplate) {
        print(searchTemplate, "r_object_id", "object_name", "r_object_type", "r_is_public");
        printFields(searchTemplate.getExternalVariables(), "variableType", "id", "expressionType", "dataType", "propertyName", "operator", "variableValue");
        printSearchable(client, searchTemplate);
    }
    
    public static void printSearchable(DCTMRestClient client, Searchable searchable) {
        print(client, searchable.getSearch());
    }
    
    public static void print(DCTMRestClient client, Search search) {
        if(search != null) {
            System.out.println("The AQL is: ");
            debugSerialize(client, search);
        }
    }

    public static void print(DCTMRestClient client) {
        System.out.println("http status: " + client.getStatus());
    }
    
    public static void printNewLine() {
        System.out.println(NEWLINE);
    }
    
    public static void print(Linkable linkable, LinkRelation... rels) {
        for(LinkRelation rel : rels) {
            System.out.println(rel.rel() + " -> " + linkable.getHref(rel));
        }
    }
    
    public static void printLinks(Linkable linkable) {
        printLinks(linkable.getLinks());
    }
    
    public static void printLinks(List<Link> links) {
        if(links != null) {
            for(Link l : links) {
                System.out.println(l.getRel() + " -> " + l.getHref());
            }
        }
    }
    
    public static void printStep(String step) {
        System.out.println("-------------" + step);
    }
    
    public static void print(ObjectLifecycle objectLifecycle) {
        System.out.println("current state: " + objectLifecycle.getCurrentState());
        if(!StringUtils.isEmpty(objectLifecycle.getNextState())) {
            System.out.println("next state: " + objectLifecycle.getNextState());
        }
        if(!StringUtils.isEmpty(objectLifecycle.getPreviousState())) {
            System.out.println("previous state: " + objectLifecycle.getPreviousState());
        }
        if(!StringUtils.isEmpty(objectLifecycle.getResumeState())) {
            System.out.println("resume state: " + objectLifecycle.getResumeState());
        }
    }
    
    public static void print(AuditPolicy auditPolicy) {
        System.out.println(auditPolicy.getObjectId() + ", name: " + auditPolicy.getName() + ", accessor-name: "
                + auditPolicy.getAccessorName() + ", is-group: " + auditPolicy.isGroup() + ", attribute-rule: "
                + auditPolicy.getAttributeRules());
    }
}
