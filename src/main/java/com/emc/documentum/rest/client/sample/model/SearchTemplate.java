/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;

import com.emc.documentum.rest.client.sample.model.Search.Searchable;

/**
 * the class represents the search template of the REST services
 */
public interface SearchTemplate extends RestObject, Searchable {
    public String getQueryDocumentTemplate();
    public List<ExternalVariable<?>> getExternalVariables();
    public String getSearchReference();
    
    public interface ExternalVariable<T> {
        public String getId();
        public String getExpressionType();
        public String getDataType();
        public T getVariableValue();
        public void setVariableValue(T t);
    }
    
    public interface FullTextVariable extends ExternalVariable<String> {
    }
    
    public interface PropertyVariable<T> extends ExternalVariable<T> {
        public String getPropertyName();
        public String getOperator();
    }
    
    public interface PropertyValueVariable extends PropertyVariable<String> {
        public static final String OPERATOR_UNDEFINED = "UNDEFINED";
        public static final String OPERATOR_EQUAL = "EQUAL";
        public static final String OPERATOR_NOT_EQUAL = "NOT_EQUAL";
        public static final String OPERATOR_GREATER_THAN = "GREATER_THAN";
        public static final String OPERATOR_LESS_THAN = "LESS_THAN";
        public static final String OPERATOR_GREATER_EQUAL = "GREATER_EQUAL";
        public static final String OPERATOR_LESS_EQUAL = "LESS_EQUAL";
        public static final String OPERATOR_BEGINS_WITH = "BEGINS_WITH";
        public static final String OPERATOR_CONTAINS = "CONTAINS";
        public static final String OPERATOR_DOES_NOT_CONTAIN = "DOES_NOT_CONTAIN";
        public static final String OPERATOR_ENDS_WITH = "ENDS_WITH";
        public static final String OPERATOR_IS_NULL = "IS_NULL";
        public static final String OPERATOR_IS_NOT_NULL = "IS_NOT_NULL";
    }
    
    public interface RelativeDateVariable extends PropertyVariable<String> {
        public static final String OPERATOR_EQUAL = "EQUAL";
        public static final String OPERATOR_GREATER_THAN = "GREATER_THAN";
        public static final String OPERATOR_LESS_THAN = "LESS_THAN";
        public static final String OPERATOR_GREATER_EQUAL = "GREATER_EQUAL";
        public static final String OPERATOR_LESS_EQUAL = "LESS_EQUAL";
    }
    
    public interface PropertyListVariable extends PropertyVariable<List<String>> {
        public static final String OPERATOR_IN = "IN";
        public static final String OPERATOR_NOT_IN = "NOT_IN";
    }
}
