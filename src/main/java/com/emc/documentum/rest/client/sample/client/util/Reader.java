/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.util;

import java.io.IOException;

import org.springframework.util.StringUtils;

public class Reader {
    public static String read(String prompt) {
        byte[] bytes = new byte[100];
        String value = null;
        while(value == null || value.length() == 0) {
            try {
                System.out.println(prompt);
                int readed;
                    readed = System.in.read(bytes);
                while(readed <= 0) {
                    System.out.println(prompt);
                    readed = System.in.read(bytes);
                }
                value = new String(bytes, 0, readed).trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }
    
    public static String read(String prompt, String defaultValue) {
        System.out.println(StringUtils.isEmpty(defaultValue)?prompt:(prompt + " [default " + defaultValue + "]")); 
        String value = defaultValue;
        byte[] bytes = new byte[100];
        int readed;
        try {
            readed = System.in.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return defaultValue;
        }
        if(readed > 0) {
            String tmp = new String(bytes, 0, readed).trim();
            if(tmp.length() > 0) {
                value = tmp;
            }
        }
        return value;
    }
    
    public static void readEnterToContinue() {
        read("press \"Enter\" to continue", "");
    }
    
    public static int read(String prompt, int defaultValue) {
        while(true) {
            String value = read(prompt, String.valueOf(defaultValue));
            try {
                return Integer.parseInt(value);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean read(String prompt, boolean defaultValue) {
        while(true) {
            String value = read(prompt, String.valueOf(defaultValue));
            try {
                return Boolean.parseBoolean(value);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
