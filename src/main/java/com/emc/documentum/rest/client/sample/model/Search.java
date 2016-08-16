/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;
import java.util.Map;

public interface Search extends Linkable {
    public List<String> getRepositories();
    public List<String> getTypes();
    public List<Column> getColumns();
    public List<Sort> getSorts();
    public List<Location> getLocations();
    public ExpressionSet getExpressionSet();
    public List<String> getCollections();
    public Boolean getAllVersions();
    public Boolean getIncludeHiddenObjects();
    public Integer getMaxResultsForFacets();
    public List<FacetDefinition> getFacetDefinitions();

    public interface Column {
        public String getName();
    }
    
    public interface Sort {
        public String getProperty();
        public Boolean getAscending();
        public String getLang();
        public Boolean getAscii();
    }
    
    public interface Location {
        public String getRepository();
        public Boolean getDescendent();
    }
    
    public interface IdLocation extends Location {
        public String getId();
    }
    
    public interface PathLocation extends Location {
        public String getPath();
    }
    
    public interface FacetDefinition {
        public static final String SORT_FREQUENCY = "FREQUENCY";
        public static final String SORT_VALUE_ASCENDING = "VALUE_ASCENDING";
        public static final String SORT_VALUE_DESCENDING = "VALUE_DESCENDING";
        public static final String SORT_NONE = "NONE";
        public static final String GROUP_BY_STRING = "string";
        public static final String GROUP_BY_RANGE = "range";
        public static final String GROUP_BY_ALPHARANGE = "alpharange";
        public static final String GROUP_BY_DATE_DAY = "day";
        public static final String GROUP_BY_DATE_WEEK = "week";
        public static final String GROUP_BY_DATE_MONTH = "month";
        public static final String GROUP_BY_DATE_QUARTER = "quarter";
        public static final String GROUP_BY_DATE_YEAR = "year";
        public static final String GROUP_BY_DATE_RELATIVE_DATE = "relativeDate";
        public String getId();
        public List<String> getAttributes();
        public String getGroupby();
        public String getSort();
        public Map<String, String> getProperties();
        public Integer getMaxValues();
        public FacetDefinition getNestedFacetDefinition();
    }
    
    public interface Expression {
        public Boolean getTemplate();
    }
    
    public interface ExpressionSet extends Expression {
        public static final String OPERATOR_AND = "AND";
        public static final String OPERATOR_OR = "OR";
        public String getOperator();
        public List<Expression> getExpressions();
    }
    
    public interface PropertyExpression extends Expression {
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
        public String getName();
        public String getValue();
        public String getOperator();
        public Boolean getExactMatch();
        public Boolean getRepeating();
        public Boolean getCaseSensitive();
        public Boolean getFuzzy();
    }
    
    public interface FullTextExpression extends Expression {
        public Boolean getFuzzy();
        public String getValue();
    }
    
    public interface RelativeDateExpression extends Expression {
        public static final String TIME_UNIT_MINUTE = "MINUTE";
        public static final String TIME_UNIT_HOUR = "HOUR";
        public static final String TIME_UNIT_DAY = "DAY";
        public static final String TIME_UNIT_WEEK = "WEEK";
        public static final String TIME_UNIT_MONTH = "MONTH";
        public static final String TIME_UNIT_YEAR = "YEAR";
        public static final String OPERATOR_EQUAL = "EQUAL";
        public static final String OPERATOR_GREATER_THAN = "GREATER_THAN";
        public static final String OPERATOR_LESS_THAN = "LESS_THAN";
        public static final String OPERATOR_GREATER_EQUAL = "GREATER_EQUAL";
        public static final String OPERATOR_LESS_EQUAL = "LESS_EQUAL";
        public String getName();
        public int getValue();
        public String getTimeUnit();
        public String getOperator();
        public Boolean getRepeating();
    }
    
    public interface PropertyListExpression extends Expression {
        public static final String OPERATOR_IN = "IN";
        public static final String OPERATOR_NOT_IN = "NOT_IN";
        public String getName();
        public String getOperator();
        public List<String> getValues();
        public Boolean getRepeating();
    }
    
    public interface PropertyRangeExpression extends Expression {
        public static final String OPERATOR_BETWEEN = "BETWEEN";
        public String getName();
        public String getOperator();
        public String getFrom();
        public String getTo();
        public Boolean getRepeating();
    }
    
    public interface Searchable {
        public Search getSearch();
    }
}
