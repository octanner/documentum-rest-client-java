/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.ValueAssistant;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name = "assist-values", namespace = XMLNamespace.DM_NAMESPACE)
public class JaxbValueAssistance extends LinkableBase implements ValueAssistant {
    
    private List<Attribute> properties;
    private ValueAssistantProperties valueAssistantProperties;
    private List<Link> links;

    @Override
	@XmlTransient
	public List<Attribute> getProperties() {
		if(properties == null) {
			properties = new ArrayList<Attribute>();
			if(valueAssistantProperties != null && valueAssistantProperties.getElements() != null) {
				for(Element e : valueAssistantProperties.getElements()) {
				    JaxbAssistantAttribute a = new JaxbAssistantAttribute(e.getLocalName());
				    if(e.hasAttribute("allow-user-values")) {
				        a.allowUserValues(Boolean.parseBoolean(e.getAttribute("allow-user-values")));
				    }
				    NodeList values = e.getElementsByTagName("value");
				    for(int i=0;i<values.getLength();++i) {
				        Node node = values.item(i);
				        a.addValue(new JaxbValue(node.getTextContent(), node.getAttributes().getNamedItem("label").getTextContent()));
				    }
					properties.add(a);
				}
			}
		}
		return properties;
	}

	@XmlElement(name="properties")
	protected ValueAssistantProperties getValueAssistantProperties() {
		return valueAssistantProperties;
	}

	protected void setValueAssistantProperties(ValueAssistantProperties valueAssistantProperties) {
		this.valueAssistantProperties = valueAssistantProperties;
	}
	
	@Override
	@XmlElementWrapper(name = "links")
	@XmlElement(name = "link", type = JaxbLink.class, namespace = XMLNamespace.DM_NAMESPACE)
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	@XmlRootElement(name = "properties")
	static class ValueAssistantProperties {
		private List<Element> elements;
		
		@XmlAnyElement
		public List<Element> getElements() {
			return elements;
		}

		public void setElements(List<Element> elements) {
			this.elements = elements;
		}
	}
	
	private static class JaxbAssistantAttribute implements Attribute {
        private final String name;
        private boolean allowUserValues;
        private List<Value> values = new ArrayList<>();

        JaxbAssistantAttribute(String name) {
            this.name = name;
        }
        
        @Override
        public String name() {
            return name;
        }
        
        void allowUserValues(boolean allowUserValues) {
            this.allowUserValues = allowUserValues;
        }
        
        @Override
        public boolean allowUserValues() {
            return allowUserValues;
        }
        
        @Override
        public List<Value> values() {
            return values;
        }
        
        private void addValue(Value value) {
            values.add(value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, allowUserValues, values);
        }

        @Override
        public boolean equals(Object obj) {
            JaxbAssistantAttribute that = (JaxbAssistantAttribute)obj;
            return Equals.equal(name, that.name)
                && Equals.equal(allowUserValues, that.allowUserValues)
                && Equals.equal(values, that.values);
        }
    }
    
    private static class JaxbValue implements Value {
        private final String value;
        private final String label;
        
        public JaxbValue(String value, String label) {
            this.value = value;
            this.label = label;
        }
        
        @Override
        public String value() {
            return value;
        }
        
        @Override
        public String label() {
            return label;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, label);
        }

        @Override
        public boolean equals(Object obj) {
            JaxbValue that = (JaxbValue)obj;
            return Equals.equal(value, that.value)
                && Equals.equal(label, that.label);
        }
    }
    
    @Override
	public boolean equals(Object obj) {
		JaxbValueAssistance that = (JaxbValueAssistance) obj;
		return Equals.equal(properties, that.properties);
	}

    @Override
    public int hashCode() {
        return Objects.hash(properties);
    }
}
