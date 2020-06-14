package com.example.iwm.server;

import ca.uhn.fhir.rest.gclient.IQuery;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;

import java.util.List;

public interface IClient<T extends IBaseResource> {
    T getResourceById(String id);

    List<IBaseResource> readObjectsFromBundle(Bundle result);

    T getResourceByIdAndVersion(String id, String version);

    IQuery<IBaseBundle> addTimeRangeToQuery(String date_from, String date_to, IQuery<IBaseBundle> query);
}
