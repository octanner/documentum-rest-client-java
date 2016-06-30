Documentum REST Java Client Samples
=========

[![Build Status](https://travis-ci.org/Enterprise-Content-Management/documentum-rest-client-java.svg?branch=master)](https://travis-ci.org/Enterprise-Content-Management/documentum-rest-client-java) 
[![License: Apache 2](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0)

This Java project contains a reference implementation of Documentum REST Services client written in Java code. The
purpose of this project is to demonstrate one way to develop a hypermedia driven REST client to consume Documentum
REST Services. It does NOT indicate that users could not develop a REST client using other technologies.

EMC shares the source code of this project for the technology sharing. If users plan to migrate the sample code to their
 products, they are responsible to maintain this part of the code in their products and should agree with license polices
  of the referenced libraries used by this sample project.


## Overview
This Documentum REST Services client is written with Java code. Underlying it leverages Spring *RestTemplate* to send
the REST messages. It supports both `XML` and `JSON` representations. `JAXB` is used to bind XML object, and `Jackson`
for JSON. Both http and https are supported.

Supported authentication scheme:
* basic
* basic-ct

*DCTMRestClientSample* is the main class to be executed with some sample operations.

## System Requirements
1. Documentum REST Services 7.x is deployed in the development environment. The samples will be automatically loaded according the rest server version.
2. Java 7 or later is installed.
3. Maven 3 is required to build the project.

### How To Use
1. Build the project with maven
>       mvn clean install

2. There will be several files built in target directory:
 * *rest-api-client-sample-7.3.jar* --> the sample client jar file
 * *lib* -> the lib directory contains all other required third party library
 * *rest-api-client-sample-7.3-all-in-one.jar* -> the sample client jar file, with all other third party classes embedded
 * *rest-api-client-sample-7.3-javadoc.jar* -> javadoc of the sample client
 * *rest-api-client-sample-7.3-sources.jar* -> source code of the sample client
3. Run the project
 execute rest-api-client-sample-7.3.jar, with lib directory at the same place
>     java -jar rest-api-client-sample-7.3.jar
   
 or execute rest-api-client-sample-7.3-all-in-one.jar directly
>     java -jar rest-api-client-sample-7.3-all-in-one.jar
   
4. After the program is started, please input the required information prompted by the sample client. After that, you
will see a list of commands:
>       Please input the number of the sample operation which need be executed:
>         0 Exit
>         1 Navigation
>         2 Folder Create/Update/Delete
>         ...

5. Then input the number of the sample you want to execute.

6. Basic sample client
<img src="imgs/basic.gif" width="600">

7. HTTPS connection without authenticating the rest server
<img src="imgs/https1.gif" width="600">


8. HTTPS connection with authenticating the rest server (the trust store must contain the rest server)
<img src="imgs/https2.gif" width="600">

Or provide the trust store path with -D parameter:
> java -Djavax.net.ssl.trustStore=X:\git\documentum-rest-client-java\truststore.jks -Djavax.net.ssl.trustStorePassword=password -jar rest-api-client-sample-7.3.jar
