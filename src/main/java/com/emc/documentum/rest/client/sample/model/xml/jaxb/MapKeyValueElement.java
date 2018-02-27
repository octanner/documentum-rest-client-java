/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MapKeyValueElement extends AbstractElement {
    private ItemNodeList nodeList;
    
    public MapKeyValueElement(String mapElement, Map<String, String> map) {
        super(mapElement);
        this.nodeList = new ItemNodeList(map);
    }
    
    @Override
    public NodeList getChildNodes() {
        return nodeList;
    }

    private static class ItemNodeList implements NodeList {
        private List<Element> values;
        
        ItemNodeList(Map<String, String> map) {
            if(map != null) {
                this.values = new ArrayList<Element>(map.size());
                for(Map.Entry<String, String> entry : map.entrySet()) {
                    values.add(new SinglePropertyElement(entry.getKey(), entry.getValue()));
                }
                for(int i=0;i<values.size()-1;++i) {
                    ((SinglePropertyElement)values.get(i)).setNextSibling(values.get(i+1));
                }
            }
        }
        
        @Override
        public Node item(int index) {
            return values.get(index);
        }

        @Override
        public int getLength() {
            return values==null?0:values.size();
        }
    }
}
