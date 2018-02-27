/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import com.emc.documentum.rest.client.sample.model.Search;
import com.emc.documentum.rest.client.sample.model.SearchTemplate;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearchTemplate.JaxbFullTextVariable;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearchTemplate.JaxbPropertyListVariable;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearchTemplate.JaxbPropertyValueVariable;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearchTemplate.JaxbRelativeDateVariable;

import static com.emc.documentum.rest.client.sample.client.impl.jaxb.DCTMJaxbContext.marshal;
import static com.emc.documentum.rest.client.sample.client.impl.jaxb.DCTMJaxbContext.unmarshal;

@XmlRootElement(name = "search-template")
@XmlSeeAlso({JaxbPropertyListVariable.class, JaxbRelativeDateVariable.class, JaxbPropertyValueVariable.class, JaxbFullTextVariable.class})
public class JaxbSearchTemplate extends JaxbObject implements SearchTemplate {
    private String queryDocumentTemplate;
    private List<ExternalVariable<?>> externalVariables = new ArrayList<>();
    private String searchReference;

    public JaxbSearchTemplate() {
    }
    
    public JaxbSearchTemplate(SearchTemplate template) {
        super(template);
        setSearch(template.getSearch());
        setSearchReference(template.getSearchReference());
    }

    public JaxbSearchTemplate(String searchReference) {
        this.searchReference = searchReference;
    }

    @Override
    public String getName() {
        return "search-template";
    }

    @Override
    @XmlElement(name = "query-document-template")
    public String getQueryDocumentTemplate() {
        return queryDocumentTemplate;
    }
    
    public void setQueryDocumentTemplate(String queryDocumentTemplate) {
        this.queryDocumentTemplate = queryDocumentTemplate;
    }
    
    @Override
    @XmlTransient
    public Search getSearch() {
        return queryDocumentTemplate==null?null:(Search)unmarshal(queryDocumentTemplate);
    }

    public void setSearch(Search search) {
        queryDocumentTemplate = search == null?null:marshal(search);
    }

    @XmlElementWrapper(name = "external-variables")
    @XmlElements({@XmlElement(name="property-list-variable", type=JaxbPropertyListVariable.class),
        @XmlElement(name="relative-date-variable", type=JaxbRelativeDateVariable.class),
        @XmlElement(name="property-variable", type=JaxbPropertyValueVariable.class),
        @XmlElement(name="fulltext-variable", type=JaxbFullTextVariable.class)})
    @Override
    public List<ExternalVariable<?>> getExternalVariables() {
        return externalVariables;
    }

    public void setExternalVariables(List<ExternalVariable<?>> externalVariables) {
        this.externalVariables = externalVariables;
    }
    
    @Override
    @XmlAttribute(name = "search-reference")
    public String getSearchReference() {
        return searchReference;
    }

    public void setSearchReference(String searchReference) {
        this.searchReference = searchReference;
    }
    
    public static abstract class JaxbExternalVariable<T> implements ExternalVariable<T> {
        private String id;
        private String expressionType;
        private String dataType;
        @Override
        @XmlElement
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        @Override
        @XmlElement(name = "expression-type")
        public String getExpressionType() {
            return expressionType;
        }
        public void setExpressionType(String expressionType) {
            this.expressionType = expressionType;
        }
        @Override
        @XmlElement(name = "data-type")
        public String getDataType() {
            return dataType;
        }
        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
    }
    
    public abstract static class JaxbPropertyVariable<T> extends JaxbExternalVariable<T> implements PropertyVariable<T> {
        private String propertyName;
        private String operator;
        @Override
        @XmlElement(name = "property-name")
        public String getPropertyName() {
            return propertyName;
        }
        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }
        @Override
        @XmlElement
        public String getOperator() {
            return operator;
        }
        public void setOperator(String operator) {
            this.operator = operator;
        }
    }
    
    @XmlRootElement(name = "fulltext-variable")
    public static class JaxbFullTextVariable extends JaxbExternalVariable<String> implements FullTextVariable {
        private String variableValue;
        @Override
        @XmlElement(name = "variable-value")
        public String getVariableValue() {
            return variableValue;
        }
        public void setVariableValue(String variableValue) {
            this.variableValue = variableValue;
        }
    }
    
    @XmlRootElement(name = "property-variable")
    public static class JaxbPropertyValueVariable extends JaxbPropertyVariable<String> implements PropertyValueVariable {
        private String variableValue;
        @Override
        @XmlElement(name = "variable-value")
        public String getVariableValue() {
            return variableValue;
        }
        public void setVariableValue(String variableValue) {
            this.variableValue = variableValue;
        }
    }
    
    @XmlRootElement(name = "relative-date-variable")
    public static class JaxbRelativeDateVariable extends JaxbPropertyVariable<String> implements RelativeDateVariable {
        private String variableValue;
        @Override
        @XmlElement(name = "variable-value")
        public String getVariableValue() {
            return variableValue;
        }
        public void setVariableValue(String variableValue) {
            this.variableValue = variableValue;
        }
    }
    
    @XmlRootElement(name = "property-list-variable")
    public static class JaxbPropertyListVariable extends JaxbPropertyVariable<List<String>> implements PropertyListVariable {
        private List<String> variableValue = new ArrayList<>();
        @Override
        @XmlElementWrapper(name = "variable-value")
        @XmlElement(name = "item")
        public List<String> getVariableValue() {
            return variableValue;
        }
        public void setVariableValue(List<String> variableValue) {
            this.variableValue = variableValue;
        }
    }
}
