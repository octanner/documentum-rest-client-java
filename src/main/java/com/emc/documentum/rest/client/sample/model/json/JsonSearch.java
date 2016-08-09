/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.json;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Search;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class JsonSearch extends JsonInlineLinkableBase implements Search {
    private List<String> types;
    private List<Column> columns;
    private List<Sort> sorts;
    private List<Location> locations;
    @JsonProperty("expression-set")
    private ExpressionSet expressionSet;
    private List<String> collections;
    @JsonProperty("all-versions")
    private Boolean allVersions;
    @JsonProperty("include-hidden-objects")
    private Boolean includeHiddenObjects;
    @JsonProperty("max-results-for-facets")
    private Integer maxResultsForFacets;
    @JsonProperty("facet-definitions")
    private List<FacetDefinition> facetDefinitions;
    
    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public ExpressionSet getExpressionSet() {
        return expressionSet;
    }

    public void setExpressionSet(ExpressionSet expressionSet) {
        this.expressionSet = expressionSet;
    }

    public List<String> getCollections() {
        return collections;
    }

    public void setCollections(List<String> collections) {
        this.collections = collections;
    }

    public Boolean getAllVersions() {
        return allVersions;
    }

    public void setAllVersions(Boolean allVersions) {
        this.allVersions = allVersions;
    }

    public Boolean getIncludeHiddenObjects() {
        return includeHiddenObjects;
    }

    public void setIncludeHiddenObjects(Boolean includeHiddenObjects) {
        this.includeHiddenObjects = includeHiddenObjects;
    }

    public Integer getMaxResultsForFacets() {
        return maxResultsForFacets;
    }

    public void setMaxResultsForFacets(Integer maxResultsForFacets) {
        this.maxResultsForFacets = maxResultsForFacets;
    }

    public List<FacetDefinition> getFacetDefinitions() {
        return facetDefinitions;
    }

    public void setFacetDefinitions(List<FacetDefinition> facetDefinitions) {
        this.facetDefinitions = facetDefinitions;
    }

    @Override
    public boolean equals(Object obj) {
        JsonSearch that = (JsonSearch) obj;
        return Equals.equal(types, that.types)
                && Equals.equal(columns, that.columns)
                && Equals.equal(sorts, that.sorts)
                && Equals.equal(locations, that.locations)
                && Equals.equal(expressionSet, that.expressionSet)
                && Equals.equal(collections, that.collections)
                && Equals.equal(allVersions, that.allVersions)
                && Equals.equal(includeHiddenObjects, that.includeHiddenObjects)
                && Equals.equal(maxResultsForFacets, that.maxResultsForFacets)
                && Equals.equal(facetDefinitions, that.facetDefinitions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(types, expressionSet, locations, sorts, columns, facetDefinitions);
    }

    public static class JsonColumn implements Column {
        private String name;
        public JsonColumn() {
        }
        public JsonColumn(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        @Override
        public boolean equals(Object obj) {
            JsonColumn that = (JsonColumn) obj;
            return Equals.equal(name, that.name);
        }
        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    public static class JsonSort implements Sort {
        private String property;
        private Boolean ascending;
        private String lang;
        private Boolean ascii;
        public JsonSort() {
        }
        public JsonSort(String property) {
            this.property = property;
        }
        public JsonSort(String property, Boolean ascending, String lang, Boolean ascii) {
            this.property = property;
            this.ascending = ascending;
            this.lang = lang;
            this.ascii = ascii;
        }
        public String getProperty() {
            return property;
        }
        public void setProperty(String property) {
            this.property = property;
        }
        public Boolean isAscending() {
            return ascending;
        }
        public void setAscending(Boolean ascending) {
            this.ascending = ascending;
        }
        public String getLang() {
            return lang;
        }
        public void setLang(String lang) {
            this.lang = lang;
        }
        public Boolean isAscii() {
            return ascii;
        }
        public void setAscii(Boolean ascii) {
            this.ascii = ascii;
        }
        @Override
        public boolean equals(Object obj) {
            JsonSort that = (JsonSort) obj;
            return Equals.equal(property, that.property)
                    && Equals.equal(ascending, that.ascending)
                    && Equals.equal(lang, that.lang)
                    && Equals.equal(ascii, that.ascii);
        }
        @Override
        public int hashCode() {
            return Objects.hash(property, ascending, lang, ascii);
        }
    }
    
    @JsonPropertyOrder({ "locationType" })
    public static class JsonIdLocation implements IdLocation {
        private boolean descendent;
        private String id;
        @JsonProperty("location-type")
        private final String locationType = "id-location";
        public JsonIdLocation() {
        }
        public JsonIdLocation(String id, boolean descendent) {
            this.id = id;
            this.descendent = descendent;
        }
        public boolean isDescendent() {
            return descendent;
        }
        public void setDescendent(boolean descendent) {
            this.descendent = descendent;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getLocationType() {
            return locationType;
        }
        @Override
        public boolean equals(Object obj) {
            JsonIdLocation that = (JsonIdLocation) obj;
            return Equals.equal(descendent, that.descendent)
                    && Equals.equal(id, that.id);
        }
        @Override
        public int hashCode() {
            return Objects.hash(descendent, id);
        }
    }
    
    @JsonPropertyOrder({ "locationType" })
    public static class JsonPathLocation implements PathLocation {
        private boolean descendent;
        private String path;
        @JsonProperty("location-type")
        private final String locationType = "path-location";
        public JsonPathLocation() {
        }
        public JsonPathLocation(String path, boolean descendent) {
            this.path = path;
            this.descendent = descendent;
        }
        public boolean isDescendent() {
            return descendent;
        }
        public void setDescendent(boolean descendent) {
            this.descendent = descendent;
        }
        public String getPath() {
            return path;
        }
        public void setPath(String path) {
            this.path = path;
        }
        public String getLocationType() {
            return locationType;
        }
        @Override
        public boolean equals(Object obj) {
            JsonPathLocation that = (JsonPathLocation) obj;
            return Equals.equal(descendent, that.descendent)
                    && Equals.equal(path, that.path);
        }
        @Override
        public int hashCode() {
            return Objects.hash(descendent, path);
        }
    }
    
    public static class JsonFacetDefinition implements FacetDefinition {
        private String id;
        private List<String> attributes;
        @JsonProperty("group-by")
        private String groupby;
        private String sort;
        private Map<String, String> properties;
        @JsonProperty("max-values")
        private Integer maxValues;
        @JsonProperty("facet-definition")
        private FacetDefinition nestedFacetDefinition;
        public JsonFacetDefinition() {
        }
        public JsonFacetDefinition(String id, List<String> attributes, String groupby, String sort, int maxValues) {
            this.id = id;
            this.attributes = attributes;
            this.groupby = groupby;
            this.sort = sort;
            this.maxValues = maxValues;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public List<String> getAttributes() {
            return attributes;
        }
        public void setAttributes(List<String> attributes) {
            this.attributes = attributes;
        }
        public String getGroupby() {
            return groupby;
        }
        public void setGroupby(String groupby) {
            this.groupby = groupby;
        }
        public String getSort() {
            return sort;
        }
        public void setSort(String sort) {
            this.sort = sort;
        }
        public Map<String, String> getProperties() {
            return properties;
        }
        public void setProperties(Map<String, String> properties) {
            this.properties = properties;
        }
        public Integer getMaxValues() {
            return maxValues;
        }
        public void setMaxValues(Integer maxValues) {
            this.maxValues = maxValues;
        }
        public FacetDefinition getNestedFacetDefinition() {
            return nestedFacetDefinition;
        }
        public void setNestedFacetDefinition(FacetDefinition nestedFacetDefinition) {
            this.nestedFacetDefinition = nestedFacetDefinition;
        }
        @Override
        public boolean equals(Object obj) {
            JsonFacetDefinition that = (JsonFacetDefinition) obj;
            return Equals.equal(id, that.id)
                    && Equals.equal(attributes, that.attributes)
                    && Equals.equal(groupby, that.groupby)
                    && Equals.equal(sort, that.sort)
                    && Equals.equal(properties, that.properties)
                    && Equals.equal(maxValues, that.maxValues)
                    && Equals.equal(nestedFacetDefinition, that.nestedFacetDefinition);
        }
        @Override
        public int hashCode() {
            return Objects.hash(id, attributes, sort, properties);
        }
    }
    
    @JsonPropertyOrder({ "name" })
    public static class JsonExpressionSet implements ExpressionSet{
        private final String name = "expression-set";
        private String operator;
        private List<Expression> expressions;
        public JsonExpressionSet() {
        }
        public JsonExpressionSet(List<Expression> expressions) {
            this.expressions = expressions;
        }
        public JsonExpressionSet(String operator, List<Expression> expressions) {
            this.operator = operator;
            this.expressions = expressions;
        }
        public String getOperator() {
            return operator;
        }
        public void setOperator(String operator) {
            this.operator = operator;
        }
        public List<Expression> getExpressions() {
            return expressions;
        }
        public void setExpressions(List<Expression> expressions) {
            this.expressions = expressions;
        }
        public String getName() {
            return name;
        }
        @Override
        public boolean equals(Object obj) {
            JsonExpressionSet that = (JsonExpressionSet) obj;
            return Equals.equal(operator, that.operator)
                    && Equals.equal(expressions, that.expressions);
        }
        @Override
        public int hashCode() {
            return Objects.hash(operator, expressions);
        }
    }
    
    @JsonPropertyOrder({ "expressionType" })
    public static class JsonPropertyExpression implements PropertyExpression {
        @JsonProperty("expression-type")
        private final String expressionType = "property";
        private String name;
        private String value;
        private String operator;
        @JsonProperty("exact-match")
        private boolean exactMatch;
        private boolean repeated;
        @JsonProperty("case-sensitive")
        private boolean caseSensitive;
        private boolean fuzzy;
        public JsonPropertyExpression() {
        }
        public JsonPropertyExpression(String name, String value, String operator) {
            this.name = name;
            this.value = value;
            this.operator = operator;
        }
        public JsonPropertyExpression(String name, String value, String operator,
                boolean exactMatch, boolean repeated, boolean caseSensitive, boolean fuzzy) {
            this.name = name;
            this.value = value;
            this.operator = operator;
            this.exactMatch = exactMatch;
            this.repeated = repeated;
            this.caseSensitive = caseSensitive;
            this.fuzzy = fuzzy;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
        public String getOperator() {
            return operator;
        }
        public void setOperator(String operator) {
            this.operator = operator;
        }
        public boolean isExactMatch() {
            return exactMatch;
        }
        public void setExactMatch(boolean exactMatch) {
            this.exactMatch = exactMatch;
        }
        public boolean isRepeated() {
            return repeated;
        }
        public void setRepeated(boolean repeated) {
            this.repeated = repeated;
        }
        public boolean isCaseSensitive() {
            return caseSensitive;
        }
        public void setCaseSensitive(boolean caseSensitive) {
            this.caseSensitive = caseSensitive;
        }
        public boolean isFuzzy() {
            return fuzzy;
        }
        public void setFuzzy(boolean fuzzy) {
            this.fuzzy = fuzzy;
        }
        public String getExpressionType() {
            return expressionType;
        }
        @Override
        public boolean equals(Object obj) {
            JsonPropertyExpression that = (JsonPropertyExpression) obj;
            return Equals.equal(name, that.name)
                    && Equals.equal(value, that.value)
                    && Equals.equal(operator, that.operator)
                    && Equals.equal(exactMatch, that.exactMatch)
                    && Equals.equal(repeated, that.repeated)
                    && Equals.equal(caseSensitive, that.caseSensitive)
                    && Equals.equal(fuzzy, that.fuzzy);
        }
        @Override
        public int hashCode() {
            return Objects.hash(name, value, operator);
        }
    }
    
    @JsonPropertyOrder({ "expressionType" })
    public static class JsonFullTextExpression implements FullTextExpression {
        @JsonProperty("expression-type")
        private final String expressionType = "fulltext";
        private boolean fuzzy;
        private String value;
        public JsonFullTextExpression() {
        }
        public JsonFullTextExpression(String value, boolean fuzzy) {
            this.value = value;
            this.fuzzy = fuzzy;
        }
        public boolean isFuzzy() {
            return fuzzy;
        }
        public void setFuzzy(boolean fuzzy) {
            this.fuzzy = fuzzy;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
        public String getExpressionType() {
            return expressionType;
        }
        @Override
        public boolean equals(Object obj) {
            JsonFullTextExpression that = (JsonFullTextExpression) obj;
            return Equals.equal(fuzzy, that.fuzzy)
                    && Equals.equal(value, that.value);
        }
        @Override
        public int hashCode() {
            return Objects.hash(fuzzy, value);
        }
    }
    
    @JsonPropertyOrder({ "expressionType" })
    public static class JsonRelativeDateExpression implements RelativeDateExpression {
        @JsonProperty("expression-type")
        private final String expressionType = "relative-date";
        private String name;
        private int value;
        @JsonProperty("time-unit")
        private String timeUnit;
        private String operator;
        public JsonRelativeDateExpression() {
        }
        public JsonRelativeDateExpression(String name, int value, String timeUnit, String operator) {
            this.name = name;
            this.value = value;
            this.timeUnit = timeUnit;
            this.operator = operator;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getValue() {
            return value;
        }
        public void setValue(int value) {
            this.value = value;
        }
        public String getTimeUnit() {
            return timeUnit;
        }
        public void setTimeUnit(String timeUnit) {
            this.timeUnit = timeUnit;
        }
        public String getOperator() {
            return operator;
        }
        public void setOperator(String operator) {
            this.operator = operator;
        }
        public String getExpressionType() {
            return expressionType;
        }
        @Override
        public boolean equals(Object obj) {
            JsonRelativeDateExpression that = (JsonRelativeDateExpression) obj;
            return Equals.equal(name, that.name)
                    && Equals.equal(value, that.value)
                    && Equals.equal(timeUnit, that.timeUnit)
                    && Equals.equal(operator, that.operator);
        }
        @Override
        public int hashCode() {
            return Objects.hash(name, value, timeUnit, operator);
        }
    }
    
    @JsonPropertyOrder({ "expressionType" })
    public static class JsonPropertyListExpression implements PropertyListExpression {
        @JsonProperty("expression-type")
        private final String expressionType = "property-list";
        private String name;
        private String operator;
        private List<String> values;
        private boolean repeated;
        public JsonPropertyListExpression() {
        }
        public JsonPropertyListExpression(String name, List<String> values) {
            this.name = name;
            this.values = values;
        }
        public JsonPropertyListExpression(String name, List<String> values, String operator, boolean repeated) {
            this.name = name;
            this.values = values;
            this.operator = operator;
            this.repeated = repeated;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getOperator() {
            return operator;
        }
        public void setOperator(String operator) {
            this.operator = operator;
        }
        public List<String> getValues() {
            return values;
        }
        public void setValues(List<String> values) {
            this.values = values;
        }
        public boolean isRepeated() {
            return repeated;
        }
        public void setRepeated(boolean repeated) {
            this.repeated = repeated;
        }
        public String getExpressionType() {
            return expressionType;
        }
        @Override
        public boolean equals(Object obj) {
            JsonPropertyListExpression that = (JsonPropertyListExpression) obj;
            return Equals.equal(name, that.name)
                    && Equals.equal(operator, that.operator)
                    && Equals.equal(values, that.values)
                    && Equals.equal(repeated, that.repeated);
        }
        @Override
        public int hashCode() {
            return Objects.hash(name, operator, values);
        }
    }
    
    @JsonPropertyOrder({ "expressionType" })
    public static class JsonPropertyRangeExpression implements PropertyRangeExpression {
        @JsonProperty("expression-type")
        private final String expressionType = "property-range";
        private String name;
        private final String operator = OPERATOR_BETWEEN;
        private String from;
        private String to;
        private boolean repeated;

        public JsonPropertyRangeExpression() {
        }
        public JsonPropertyRangeExpression(String name, String from, String to) {
            this.name = name;
            this.from = from;
            this.to = to;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getOperator() {
            return operator;
        }
        public String getFrom() {
            return from;
        }
        public void setFrom(String from) {
            this.from = from;
        }
        public String getTo() {
            return to;
        }
        public void setTo(String to) {
            this.to = to;
        }
        public boolean isRepeated() {
            return repeated;
        }
        public void setRepeated(boolean repeated) {
            this.repeated = repeated;
        }
        public String getExpressionType() {
            return expressionType;
        }
        @Override
        public boolean equals(Object obj) {
            JsonPropertyRangeExpression that = (JsonPropertyRangeExpression) obj;
            return Equals.equal(name, that.name)
                    && Equals.equal(operator, that.operator)
                    && Equals.equal(from, that.from)
                    && Equals.equal(to, that.to)
                    && Equals.equal(repeated, that.repeated);
        }
        @Override
        public int hashCode() {
            return Objects.hash(name, operator, from, to);
        }
    }
}