/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.impl.jaxb;

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
            e.printStackTrace();
        }
        return obj;
    }
    
    public static void marshal(OutputStream os, Object object) throws Exception {
         context.createMarshaller().marshal(object, os);
    }
}
