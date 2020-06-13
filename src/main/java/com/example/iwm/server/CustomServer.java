package com.example.iwm.server;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.springframework.stereotype.Component;

@Component
public class CustomServer {
    private FhirContext ctx = FhirContext.forR4();
    private String serverBase = "http://localhost:8080/baseR4/";
    private IGenericClient client;

    public CustomServer() {
        client = ctx.newRestfulGenericClient(serverBase);
    }

    public IGenericClient getClient(){
        return this.client;
    }

    public FhirContext getContext() {
        return this.ctx;
    }
}
