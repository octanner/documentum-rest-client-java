/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.util.StringUtils;

import com.emc.documentum.rest.client.sample.cases.Sample;
import com.emc.documentum.rest.client.sample.client.DCTMRestClient;
import com.emc.documentum.rest.client.sample.client.DCTMRestClientBinding;
import com.emc.documentum.rest.client.sample.client.DCTMRestClientBuilder;
import com.emc.documentum.rest.client.sample.client.util.Reader;

import static com.emc.documentum.rest.client.sample.cases.Sample.loadSamples;

@RunWith(Theories.class)
public class DCTMRestClientSampleTest {
    private static Properties p = new Properties();
    private static boolean enable;
    static {
        try {
            p.load(DCTMRestClientSampleTest.class.getClassLoader().getResourceAsStream("test.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        enable = checkConfiguration();
    }
    @DataPoints
    public static DCTMRestClient[] getData() {
        return new DCTMRestClient[] {
                DCTMRestClientBuilder.buildSilently(DCTMRestClientBinding.XML, p.getProperty("test.context.root"), p.getProperty("test.repository"), p.getProperty("test.username"), p.getProperty("test.password")),
                DCTMRestClientBuilder.buildSilently(DCTMRestClientBinding.JSON, p.getProperty("test.context.root"), p.getProperty("test.repository"), p.getProperty("test.username"), p.getProperty("test.password"))};
    }
    
    @BeforeClass
    public static void before() {
        Reader.setSilently(true);
        Map<String, String> map = new HashMap<>();
        map.put("Please input the aspect type to be attached (return to skip):", p.getProperty("aspect.name"));
        map.put("Please input the shareable type (return to skip):", p.getProperty("shareable.type.name"));
        map.put("Please input the lightweight type (return to skip):", p.getProperty("lightweight.type.name"));
        map.put("Please input the value you want to search:", p.getProperty("search.fulltext"));
        map.put("Please input the type name with the fixed value assistance list(no such type press 'return' directly to skip):", p.getProperty("type.with.fixed.value.assistance.list"));
        map.put("Please input the attribute name of " + p.getProperty("type.with.fixed.value.assistance.list") + " with the fixed assistance list:", p.getProperty("type.with.fixed.value.assistance.list.attribute"));
        map.put("Please input the type name with value assistance dependencies(no such type press 'return' directly to skip):", p.getProperty("type.with.value.assistance.dependencies"));
        map.put("Please input the attribute name of " + p.getProperty("type.with.value.assistance.dependencies") + " with the query dependencies:", p.getProperty("type.with.value.assistance.dependencies.attribute"));
        map.put("Please input the dependency name of " + p.getProperty("type.with.value.assistance.dependencies") + "." + p.getProperty("type.with.value.assistance.dependencies.attribute") + ":", p.getProperty("type.with.value.assistance.dependencies.dependency"));
        map.put("Please input the dependency value of " + p.getProperty("type.with.value.assistance.dependencies.dependency") + ":", p.getProperty("type.with.value.assistance.dependencies.dependency.value"));
        map.put("Please input the object id of the virtual document object (press 'return' to skip sample):", p.getProperty("virtual.document.object.id"));
        map.put("Please input the lifecycle name to be attached to the document (return to skip):", p.getProperty("lifecycle.name"));
        map.put("Please input the aspect type to be attached (return to skip):", p.getProperty("aspect.name"));
        map.put("Please input the lifecycle name to be attached (return to skip):", p.getProperty("lifecycle.name"));
        Reader.setInputMap(map);
    }
    
    @AfterClass
    public static void after() {
        Reader.setSilently(false);
        Reader.setInputMap(null);
    }

    @Theory
    public void testAll(DCTMRestClient client) {
        if(enable) {
            double version = client.getMajorVersion();
            for(Sample s : loadSamples(version, client)) {
                s.throwException().run();
            }
        }
    }
    
    private static boolean checkConfiguration() {
        if(StringUtils.isEmpty(p.getProperty("test.context.root")) || StringUtils.isEmpty(p.getProperty("test.repository")) ||
           StringUtils.isEmpty(p.getProperty("test.username")) || StringUtils.isEmpty(p.getProperty("test.password"))) {
            System.out.println("The unit tests are disabled.");
            System.out.println("Please check the REST server configuration in the test.properties file.");
            return false;
        } else {
            return true;
        }
    }
}
