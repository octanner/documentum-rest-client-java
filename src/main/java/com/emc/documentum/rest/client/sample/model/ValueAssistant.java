package com.emc.documentum.rest.client.sample.model;

import java.util.List;

public interface ValueAssistant extends Linkable{
    public List<Attribute> getProperties();
    
    public static interface Attribute {
        public String name();
        public boolean allowUserValues();
        public List<Value> values();
    }
    
    public static interface Value {
        public String value();
        public String label();
    }
}
