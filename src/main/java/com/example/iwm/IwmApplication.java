package com.example.iwm;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import com.example.iwm.server.Client;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IwmApplication {

    public static void main(String[] args) {
        SpringApplication.run(IwmApplication.class, args);
    }

}
