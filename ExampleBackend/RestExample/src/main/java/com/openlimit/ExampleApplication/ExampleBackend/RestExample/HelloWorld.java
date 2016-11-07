/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.openlimit.ExampleApplication.ExampleBackend.RestExample;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.openlimit.ExampleApplication.ExampleBackend.JPAExample.ManagementServiceImpl;

/**
 * A simple REST service which is able to say hello to someone using HelloService Please take a look at the web.xml where JAX-RS
 * is enabled
 *
 * @author gbrey@redhat.com
 *
 */

@Produces(MediaType.TEXT_HTML)
@Path("/")
public class HelloWorld {
    @Inject
    HelloService helloService;

    @GET
    @Path("/json")
    @Produces({ "application/json" })
    public String getHelloWorldJSON() {
        return "{\"result\":\"" + helloService.createHelloMessage("World") + "\"}";
    }

    @GET
    @Path("/xml")
    @Produces({ "application/xml" })
    public String getHelloWorldXML() {
        return "<xml><result>" + helloService.createHelloMessage("World") + "</result></xml>";
    }
    
    @GET
    @Path("/wurst")
    @Produces({ MediaType.TEXT_PLAIN })
    public String getHelloWorldWurst() {
        return helloService.createHelloMessage("Wurst");
    }

    @GET
    @Path("/worst")
    public String getHelloWorldWorst() {
        return "<h1>" + helloService.createHelloMessage("Wurst") + "</h1>";
    }
    
    @GET
    @Path("/users")
    public String getUsernames() {
    	
    	ManagementServiceImpl msi = new ManagementServiceImpl();
    	
        return "<h1>" + msi.getUsernames() + "</h1>";
    }
}
