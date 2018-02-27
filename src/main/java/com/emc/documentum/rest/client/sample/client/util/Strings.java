/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.util;

public class Strings {
    public static boolean contains(String[] params, String ... param) {
        for(int i=0;i<=params.length-param.length;++i) {
            if(params[i].equals(param[0])) {
                boolean contains = true;
                for(int j=1;j+i<params.length && j<param.length && contains;j++) {
                    if(!params[j+i].equals(param[j])) {
                        contains = false;
                    }
                }
                if(contains) {
                    return true;
                }
            }
        }
        return false;
    }
}
