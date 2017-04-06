Documentum REST Java Client Samples
=========

[![Build Status](https://travis-ci.org/Enterprise-Content-Management/documentum-rest-client-java.svg?branch=master)](https://travis-ci.org/Enterprise-Content-Management/documentum-rest-client-java) 
[![License: Apache 2](https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0)


This Java project contains a reference implementation of Documentum REST Services client written in Java code. The
purpose of this project is to demonstrate one way to develop a hypermedia driven REST client to consume Documentum
REST Services. It does NOT indicate that users could not develop a REST client using other technologies.

EMC shares the source code of this project for the technology sharing. If users plan to migrate the sample code to their
 products, they are responsible to maintain this part of the code in their products and should agree with license polices
  of the referenced libraries used by this sample project.


## Overview
This Documentum REST Services client is written with Java code. Underlying it leverages Spring *RestTemplate* to send
the REST messages. It supports both `XML` and `JSON` representations. `JAXB` is used to bind XML object, and `Jackson`
for JSON. Both http and https are supported. Supported features are listed in the below table.


Supported Features | Description 
--- | ---
API Version | ![REST API Version](https://img.shields.io/badge/rest--api-7.2-brightgreen.png) ![REST API Version](https://img.shields.io/badge/rest--api-7.3-brightgreen.png)
Formats | JSON and XML
Authentication | <ul><li>Basic</li><li>Basic plus client token cookie</li></ul>
Fundamental Services | <ul><li>Home document</li><li>Product information</li><li>Batch execution</li></ul>
Folder Services | <ul><li>Cabinet CRUD</li><li>Folder CRUD</li><li>Folder navigation</li><li>Copy, move, link, unlink</li></ul>
Document / Sysobject Services | <ul><li>Document CRUD</li><li>Checkout, cancel checkout, checkin</li><li>Copy, move, link, unlink</li></ul>
Content Services | <ul><li>Import contents</li><li>Update contents</li><li>Export contents</li></ul>
Aspect Services | <ul><li>Attach aspects</li><li>Detach aspects</li></ul>
Lightweight object Services | <ul><li>Shared parent/children navigation</li><li>Materialization</li><li>Dematerialization</li></ul>
Virtual document Services | <ul><li>List virtual document nodes</li></ul>
Query and search Services | <ul><li>DQL read-only query</li><li>Full-text search</li><li>Full-text search with facets</li><li>Search template CRUD, execution</li><li>Saved search CRUD, execution</li></ul>
User and group Services | <ul><li>User CRUD</li><li>Group CRUD</li><li>Add group members</li><li>Remove group members</li><li>User preference CRUD</li></ul>
Permission Services | <ul><li>ACL CRUD</li><li>Set object permissions</li><li>Enquire object permissions</li></ul>
Collaboration Services | <ul><li>List comments</li><li>Add/reply comments</li><li>Delete comments</li></ul>
Relation Services | <ul><li>Create relations</li><li>List relations</li></ul>
Format Services | <ul><li>List formats</li></ul>
Network location Services | <ul><li>List network locations</li></ul>
Type services | <ul><li>List types</li><li>List relation types</li><li>List aspect types</li><li>Get value assistance</li></ul>


> _**CRUD**_ - *Create, Retrieve, Update, Delete*

## System requirements
1. Documentum REST Services 7.x is deployed in the development environment. The samples will be automatically loaded according the rest server version.
2. Java 7 or later is installed.
3. Maven 3 is required to build the project.

## Use the API
The class **DCTMRestClient** is the client stub class for the client application to call REST APIs. We can use **DCTMRestClientBuilder** to build a REST client instance.
```java
DCTMRestClient client = new DCTMRestClientBuilder().bind(DCTMRestClientBinding.JSON)
                .contextRoot("http://localhost:8080/dctm-rest")
                .repository("ACME")
                .credentials("username", "password")
                .build();
RestObject productInfo = client.getProductInfo();
```

## Build the project
1. Build the project with maven
>       mvn clean install

2. There will be several files built in target directory:
 * *rest-api-client-sample-7.3.jar* --> the sample client jar file
 * *lib* -> the lib directory contains all other required third party library
 * *rest-api-client-sample-7.3-all-in-one.jar* -> the sample client jar file, with all other third party classes embedded
 * *rest-api-client-sample-7.3-javadoc.jar* -> javadoc of the sample client
 * *rest-api-client-sample-7.3-sources.jar* -> source code of the sample client
 
## Run samples
1. Run the project
 execute rest-api-client-sample-7.3.jar, with lib directory at the same place
>     java -jar rest-api-client-sample-7.3.jar
   
 or execute rest-api-client-sample-7.3-all-in-one.jar directly
>     java -jar rest-api-client-sample-7.3-all-in-one.jar
   
2. After the program is started, please input the required information prompted by the sample client. After that, you
will see a list of commands:
>       Please input the number of the sample operation which need be executed:
>         0 Exit
>         1 Navigation
>         2 Folder Create/Update/Delete
>         ...

3. Then input the number of the sample you want to execute
4. Basic sample client <br/>
<img src="imgs/basic.gif" width="600">

5. HTTPS connection without authenticating the rest server
<img src="imgs/https1.gif" width="600">

6. HTTPS connection with authenticating the rest server (the trust store must contain the rest server)
<img src="imgs/https2.gif" width="600">

Or provide the trust store path with -D parameter:
> java -Djavax.net.ssl.trustStore=X:\git\documentum-rest-client-java\truststore.jks -Djavax.net.ssl.trustStorePassword=password -jar rest-api-client-sample-7.3.jar
