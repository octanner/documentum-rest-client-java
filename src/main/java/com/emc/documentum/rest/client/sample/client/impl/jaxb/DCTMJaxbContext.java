/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.impl.jaxb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.w3c.dom.Node;

/**
 * the class to unmarshal the xml
 */
public final class DCTMJaxbContext {
    private final static JAXBContext context;
    
    static {
        JAXBContext c = null;
        try {
            c = JAXBContext.newInstance("com.emc.documentum.rest.client.sample.model.xml.jaxb");
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        context = c;
    }
    
    public static Object unmarshal(Node node) {
        Object obj = null;
        try {
            obj = context.createUnmarshaller().unmarshal(node);
        } catch (JAXBException e) {
            throw new IllegalArgumentException(e);
        }
        return obj;
    }
    
    public static Object unmarshal(String xml) {
        Object obj = null;
        try {
            obj = context.createUnmarshaller().unmarshal(new ByteArrayInputStream(xml.getBytes()));
        } catch (JAXBException e) {
            throw new IllegalArgumentException(e);
        }
        return obj;
    }
    
    public static void marshal(OutputStream os, Object object) {
         try {
            context.createMarshaller().marshal(object, os);
        } catch (JAXBException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    public static String marshal(Object object) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            context.createMarshaller().marshal(object, os);
        } catch (JAXBException e) {
            throw new IllegalArgumentException(e);
        }
        return os.toString();
    }
}
