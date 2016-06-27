/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import com.emc.documentum.rest.client.sample.client.DCTMRestClient;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSampleExclude;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.client.util.Debug;

import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;

public abstract class Sample {
    protected DCTMRestClient client;
    protected double version;
    protected String name;
    
    public Sample() {
        name = ((RestServiceSample)this.getClass().getAnnotation(RestServiceSample.class)).value();
    }
    
    public static List<Sample> loadSamples(final double version, DCTMRestClient client) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                if(metadataReader.getAnnotationMetadata().getAnnotationTypes().contains(RestServiceSample.class.getName()) &&
                   version >= (double)metadataReader.getAnnotationMetadata().getAnnotationAttributes(RestServiceVersion.class.getName()).get("value")) {
                    try {
                        Class<?> clazz = Class.forName(metadataReader.getClassMetadata().getClassName());
                        Class<?> superClass = clazz.getSuperclass();
                        while(superClass != null && !superClass.equals(Sample.class)) {
                            superClass = superClass.getSuperclass();
                        }
                        return Sample.class.equals(superClass);
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e);
                    }
                }
                return false;
            }
        });
        Set<BeanDefinition> samples = provider.findCandidateComponents("com.emc.documentum.rest.client.sample.cases");
        List<Sample> list = new ArrayList<>();
        try {
            for(BeanDefinition bd : samples) {
                Class<?> clazz = Class.forName(bd.getBeanClassName());
                Constructor<?> constructor = clazz.getConstructor();
                list.add(((Sample)constructor.newInstance()).setClient(client).setVersion(version));
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return list;
    }
    
    public Sample setClient(DCTMRestClient client) {
        this.client = client;
        return this;
    }
    
    public Sample setVersion(double version) {
        this.version = version;
        return this;
    }
    
    public String name() {
        return name;
    }
    
    @RestServiceSampleExclude
    public void run() {
        System.out.println("start " + name + " samples");
        for(Method m : this.getClass().getMethods()) {
            if(!Object.class.equals(m.getDeclaringClass()) && m.getParameterTypes().length == 0 && m.getAnnotation(RestServiceSampleExclude.class)==null && void.class.equals(m.getReturnType())) {
                RestServiceVersion serviceVersion = m.getAnnotation(RestServiceVersion.class);
                if(serviceVersion == null || serviceVersion.value() <= version) {
                    try {
                        m.invoke(this);
                    } catch (Exception e) {
                        Debug.error(m.toString());
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("finish " + name + " samples");
        printNewLine();
    }
    
    protected void printHttpStatus() {
        Debug.print(client);
    }
}
