/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Search;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbFacetProperty;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbFullTextExpression;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbIdLocation;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbPathLocation;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbPropertyExpression;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbPropertyListExpression;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbPropertyRangeExpression;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbRelativeDateExpression;

@XmlRootElement(name = "search")
@XmlSeeAlso({JaxbFullTextExpression.class, JaxbPropertyExpression.class, JaxbPropertyListExpression.class, JaxbPropertyRangeExpression.class,
            JaxbRelativeDateExpression.class, JaxbIdLocation.class, JaxbPathLocation.class, JaxbFacetProperty.class})
public class JaxbSearch extends JaxbDmLinkableBase implements Search {
    private List<String> types;
    private List<Column> columns;
    private List<Sort> sorts;
    private List<Location> locations;
    private ExpressionSet expressionSet;
    private List<String> collections;
    private Boolean allVersions;
    private Boolean includeHiddenObjects;
    private Integer maxResultsForFacets;
    private List<FacetDefinition> facetDefinitions;

    @XmlElementWrapper
    @XmlElement(name = "type")
    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    @XmlElementWrapper
    @XmlElement(name = "column", type=JaxbColumn.class)
    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
    
    @XmlElementWrapper
    @XmlElement(name = "sort", type=JaxbSort.class)
    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }
    
    @XmlElementWrapper
    @XmlAnyElement()
    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    @XmlElement(name="expression-set", type=JaxbExpressionSet.class)
    public ExpressionSet getExpressionSet() {
        return expressionSet;
    }

    public void setExpressionSet(ExpressionSet expressionSet) {
        this.expressionSet = expressionSet;
    }

    @XmlElementWrapper
    @XmlElement(name = "collection")
    public List<String> getCollections() {
        return collections;
    }

    public void setCollections(List<String> collections) {
        this.collections = collections;
    }

    @XmlAttribute(name = "all-versions")
    public Boolean getAllVersions() {
        return allVersions;
    }

    public void setAllVersions(Boolean allVersions) {
        this.allVersions = allVersions;
    }

    @XmlAttribute(name = "include-hidden-objects")
    public Boolean getIncludeHiddenObjects() {
        return includeHiddenObjects;
    }

    public void setIncludeHiddenObjects(Boolean includeHiddenObjects) {
        this.includeHiddenObjects = includeHiddenObjects;
    }

    @XmlAttribute(name = "max-results-for-facets")
    public Integer getMaxResultsForFacets() {
        return maxResultsForFacets;
    }

    public void setMaxResultsForFacets(Integer maxResultsForFacets) {
        this.maxResultsForFacets = maxResultsForFacets;
    }

    @XmlElementWrapper(name = "facet-definitions")
    @XmlElement(name = "facet-definition", type = JaxbFacetDefinition.class)
    public List<FacetDefinition> getFacetDefinitions() {
        return facetDefinitions;
    }

    public void setFacetDefinitions(List<FacetDefinition> facetDefinitions) {
        this.facetDefinitions = facetDefinitions;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbSearch that = (JaxbSearch) obj;
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

    @XmlRootElement(name = "column")
    public static class JaxbColumn implements Column {
        private String name;
        public JaxbColumn() {
        }
        public JaxbColumn(String name) {
            this.name = name;
        }
        @XmlValue
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        @Override
        public boolean equals(Object obj) {
            JaxbColumn that = (JaxbColumn) obj;
            return Equals.equal(name, that.name);
        }
        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    @XmlRootElement(name = "sort")
    public static class JaxbSort implements Sort {
        private String property;
        private Boolean ascending;
        private String lang;
        private Boolean ascii;
        public JaxbSort() {
        }
        public JaxbSort(String property) {
            this.property = property;
        }
        public JaxbSort(String property, Boolean ascending, String lang, Boolean ascii) {
            this.property = property;
            this.ascending = ascending;
            this.lang = lang;
            this.ascii = ascii;
        }
        @XmlValue
        public String getProperty() {
            return property;
        }
        public void setProperty(String property) {
            this.property = property;
        }
        @XmlAttribute
        public Boolean isAscending() {
            return ascending;
        }
        public void setAscending(Boolean ascending) {
            this.ascending = ascending;
        }
        @XmlAttribute
        public String getLang() {
            return lang;
        }
        public void setLang(String lang) {
            this.lang = lang;
        }
        @XmlAttribute
        public Boolean isAscii() {
            return ascii;
        }
        public void setAscii(Boolean ascii) {
            this.ascii = ascii;
        }
        @Override
        public boolean equals(Object obj) {
            JaxbSort that = (JaxbSort) obj;
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
    
    @XmlRootElement(name = "id-location")
    public static class JaxbIdLocation implements IdLocation {
        private boolean descendent;
        private String id;
        public JaxbIdLocation() {
        }
        public JaxbIdLocation(String id, boolean descendent) {
            this.id = id;
            this.descendent = descendent;
        }
        @XmlAttribute
        public boolean isDescendent() {
            return descendent;
        }
        public void setDescendent(boolean descendent) {
            this.descendent = descendent;
        }
        @XmlElement
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        @Override
        public boolean equals(Object obj) {
            JaxbIdLocation that = (JaxbIdLocation) obj;
            return Equals.equal(descendent, that.descendent)
                    && Equals.equal(id, that.id);
        }
        @Override
        public int hashCode() {
            return Objects.hash(descendent, id);
        }
    }
    
    @XmlRootElement(name = "path-location")
    public static class JaxbPathLocation implements PathLocation {
        private boolean descendent;
        private String path;
        public JaxbPathLocation() {
        }
        public JaxbPathLocation(String path, boolean descendent) {
            this.path = path;
            this.descendent = descendent;
        }
        @XmlAttribute
        public boolean isDescendent() {
            return descendent;
        }
        public void setDescendent(boolean descendent) {
            this.descendent = descendent;
        }
        @XmlElement
        public String getPath() {
            return path;
        }
        public void setPath(String path) {
            this.path = path;
        }
        @Override
        public boolean equals(Object obj) {
            JaxbPathLocation that = (JaxbPathLocation) obj;
            return Equals.equal(descendent, that.descendent)
                    && Equals.equal(path, that.path);
        }
        @Override
        public int hashCode() {
            return Objects.hash(descendent, path);
        }
    }
    
    @XmlRootElement(name = "facet-definition")
    public static class JaxbFacetDefinition implements FacetDefinition {
        private String id;
        private List<String> attributes;
        private String groupby;
        private String sort;
        private Map<String, String> properties;
        private Integer maxValues;
        private FacetDefinition nestedFacetDefinition;
        public JaxbFacetDefinition() {
        }
        public JaxbFacetDefinition(String id, List<String> attributes, String groupby, String sort, int maxValues) {
            this.id = id;
            this.attributes = attributes;
            this.groupby = groupby;
            this.sort = sort;
            this.maxValues = maxValues;
        }
        @XmlAttribute
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        @XmlElementWrapper
        @XmlElement(name = "attribute")
        public List<String> getAttributes() {
            return attributes;
        }
        public void setAttributes(List<String> attributes) {
            this.attributes = attributes;
        }
        @XmlAttribute(name = "group-by")
        public String getGroupby() {
            return groupby;
        }
        public void setGroupby(String groupby) {
            this.groupby = groupby;
        }
        @XmlElement
        public String getSort() {
            return sort;
        }
        public void setSort(String sort) {
            this.sort = sort;
        }
        @XmlElementWrapper(name = "properties")
        @XmlElement(name = "property")
        public List<JaxbFacetProperty> getPropertiesList() {
            List<JaxbFacetProperty> list = null;
            if(properties != null) {
                list = new ArrayList<>(properties.size());
                for(Map.Entry<String, String> entry : properties.entrySet()) {
                    list.add(new JaxbFacetProperty(entry.getKey(), entry.getValue()));
                }
            }
            return list;
        }
        @XmlTransient
        public Map<String, String> getProperties() {
            return properties;
        }
        public void setProperties(Map<String, String> properties) {
            this.properties = properties;
        }
        @XmlAttribute(name = "max-values")
        public Integer getMaxValues() {
            return maxValues;
        }
        public void setMaxValues(Integer maxValues) {
            this.maxValues = maxValues;
        }
        @XmlElement(name = "facet-definition", type = JaxbFacetDefinition.class )
        public FacetDefinition getNestedFacetDefinition() {
            return nestedFacetDefinition;
        }
        public void setNestedFacetDefinition(FacetDefinition nestedFacetDefinition) {
            this.nestedFacetDefinition = nestedFacetDefinition;
        }
        @Override
        public boolean equals(Object obj) {
            JaxbFacetDefinition that = (JaxbFacetDefinition) obj;
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
    
    @XmlRootElement(name = "property")
    public static class JaxbFacetProperty {
        private String name;
        private String value;
        public JaxbFacetProperty() {
        }
        public JaxbFacetProperty(String name, String value) {
            this.name = name;
            this.value = value;
        }
        @XmlAttribute
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        @XmlValue
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }
    
    @XmlRootElement(name = "expression-set")
    public static class JaxbExpressionSet implements ExpressionSet{
        private String operator;
        private List<Expression> expressions;
        public JaxbExpressionSet() {
        }
        public JaxbExpressionSet(List<Expression> expressions) {
            this.expressions = expressions;
        }
        public JaxbExpressionSet(String operator, List<Expression> expressions) {
            this.operator = operator;
            this.expressions = expressions;
        }
        @XmlAttribute
        public String getOperator() {
            return operator;
        }
        public void setOperator(String operator) {
            this.operator = operator;
        }
        @XmlElementWrapper
        @XmlAnyElement
        public List<Expression> getExpressions() {
            return expressions;
        }
        public void setExpressions(List<Expression> expressions) {
            this.expressions = expressions;
        }
        @Override
        public boolean equals(Object obj) {
            JaxbExpressionSet that = (JaxbExpressionSet) obj;
            return Equals.equal(operator, that.operator)
                    && Equals.equal(expressions, that.expressions);
        }
        @Override
        public int hashCode() {
            return Objects.hash(operator, expressions);
        }
    }
    
    @XmlRootElement(name = "property")
    public static class JaxbPropertyExpression implements PropertyExpression {
        private String name;
        private String value;
        private String operator;
        private boolean exactMatch;
        private boolean repeated;
        private boolean caseSensitive;
        private boolean fuzzy;
        public JaxbPropertyExpression() {
        }
        public JaxbPropertyExpression(String name, String value, String operator) {
            this.name = name;
            this.value = value;
            this.operator = operator;
        }
        public JaxbPropertyExpression(String name, String value, String operator,
                boolean exactMatch, boolean repeated, boolean caseSensitive, boolean fuzzy) {
            this.name = name;
            this.value = value;
            this.operator = operator;
            this.exactMatch = exactMatch;
            this.repeated = repeated;
            this.caseSensitive = caseSensitive;
            this.fuzzy = fuzzy;
        }
        @XmlAttribute
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        @XmlValue
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
        @XmlAttribute
        public String getOperator() {
            return operator;
        }
        public void setOperator(String operator) {
            this.operator = operator;
        }
        @XmlAttribute(name="exact-match")
        public boolean isExactMatch() {
            return exactMatch;
        }
        public void setExactMatch(boolean exactMatch) {
            this.exactMatch = exactMatch;
        }
        @XmlAttribute
        public boolean isRepeated() {
            return repeated;
        }
        public void setRepeated(boolean repeated) {
            this.repeated = repeated;
        }
        @XmlAttribute(name="case-sensitive")
        public boolean isCaseSensitive() {
            return caseSensitive;
        }
        public void setCaseSensitive(boolean caseSensitive) {
            this.caseSensitive = caseSensitive;
        }
        @XmlAttribute
        public boolean isFuzzy() {
            return fuzzy;
        }
        public void setFuzzy(boolean fuzzy) {
            this.fuzzy = fuzzy;
        }
        @Override
        public boolean equals(Object obj) {
            JaxbPropertyExpression that = (JaxbPropertyExpression) obj;
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
    
    @XmlRootElement(name = "fulltext")
    public static class JaxbFullTextExpression implements FullTextExpression {
        private boolean fuzzy;
        private String value;
        public JaxbFullTextExpression() {
        }
        public JaxbFullTextExpression(String value, boolean fuzzy) {
            this.value = value;
            this.fuzzy = fuzzy;
        }
        @XmlAttribute
        public boolean isFuzzy() {
            return fuzzy;
        }
        public void setFuzzy(boolean fuzzy) {
            this.fuzzy = fuzzy;
        }
        @XmlValue
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
        @Override
        public boolean equals(Object obj) {
            JaxbFullTextExpression that = (JaxbFullTextExpression) obj;
            return Equals.equal(fuzzy, that.fuzzy)
                    && Equals.equal(value, that.value);
        }
        @Override
        public int hashCode() {
            return Objects.hash(fuzzy, value);
        }
    }
    
    @XmlRootElement(name = "relative-date")
    public static class JaxbRelativeDateExpression implements RelativeDateExpression {
        private String name;
        private int value;
        private String timeUnit;
        private String operator;
        public JaxbRelativeDateExpression() {
        }
        public JaxbRelativeDateExpression(String name, int value, String timeUnit, String operator) {
            this.name = name;
            this.value = value;
            this.timeUnit = timeUnit;
            this.operator = operator;
        }
        @XmlAttribute
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        @XmlValue
        public int getValue() {
            return value;
        }
        public void setValue(int value) {
            this.value = value;
        }
        @XmlAttribute(name="time-unit")
        public String getTimeUnit() {
            return timeUnit;
        }
        public void setTimeUnit(String timeUnit) {
            this.timeUnit = timeUnit;
        }
        @XmlAttribute
        public String getOperator() {
            return operator;
        }
        public void setOperator(String operator) {
            this.operator = operator;
        }
        @Override
        public boolean equals(Object obj) {
            JaxbRelativeDateExpression that = (JaxbRelativeDateExpression) obj;
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
    
    @XmlRootElement(name = "property-list")
    public static class JaxbPropertyListExpression implements PropertyListExpression {
        private String name;
        private String operator;
        private List<String> values;
        private boolean repeated;
        public JaxbPropertyListExpression() {
        }
        public JaxbPropertyListExpression(String name, List<String> values) {
            this.name = name;
            this.values = values;
        }
        public JaxbPropertyListExpression(String name, List<String> values, String operator, boolean repeated) {
            this.name = name;
            this.values = values;
            this.operator = operator;
            this.repeated = repeated;
        }
        @XmlAttribute
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        @XmlAttribute
        public String getOperator() {
            return operator;
        }
        public void setOperator(String operator) {
            this.operator = operator;
        }
        @XmlElementWrapper
        @XmlElement(name = "value")
        public List<String> getValues() {
            return values;
        }
        public void setValues(List<String> values) {
            this.values = values;
        }
        @XmlAttribute
        public boolean isRepeated() {
            return repeated;
        }
        public void setRepeated(boolean repeated) {
            this.repeated = repeated;
        }
        @Override
        public boolean equals(Object obj) {
            JaxbPropertyListExpression that = (JaxbPropertyListExpression) obj;
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
    
    @XmlRootElement(name = "property-range")
    public static class JaxbPropertyRangeExpression implements PropertyRangeExpression {
        private String name;
        private final String operator = OPERATOR_BETWEEN;
        private String from;
        private String to;
        private boolean repeated;
        public JaxbPropertyRangeExpression() {
        }
        public JaxbPropertyRangeExpression(String name, String from, String to) {
            this.name = name;
            this.from = from;
            this.to = to;
        }
        @XmlAttribute
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        @XmlAttribute
        public String getOperator() {
            return operator;
        }
        @XmlElement
        public String getFrom() {
            return from;
        }
        public void setFrom(String from) {
            this.from = from;
        }
        @XmlElement
        public String getTo() {
            return to;
        }
        public void setTo(String to) {
            this.to = to;
        }
        @XmlAttribute
        public boolean isRepeated() {
            return repeated;
        }
        public void setRepeated(boolean repeated) {
            this.repeated = repeated;
        }
        @Override
        public boolean equals(Object obj) {
            JaxbPropertyRangeExpression that = (JaxbPropertyRangeExpression) obj;
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