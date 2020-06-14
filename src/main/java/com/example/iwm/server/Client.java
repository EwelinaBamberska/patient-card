package com.example.iwm.server;


import ca.uhn.fhir.rest.gclient.IQuery;
import ca.uhn.fhir.util.BundleUtil;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Observation;

import java.util.ArrayList;
import java.util.List;

public class Client<T extends IBaseResource> implements IClient{
    private final Class type;

    private final CustomServer server = new CustomServer();

    public Client(Class<T> type) {
        this.type = type;
    }

    public T getResourceById(String id) {
        T results = (T) server.getClient()
                .read()
                .resource(type)
                .withId(id)
                .execute();
        return results;
    }

    public T getResourceByIdAndVersion(String id, String version){
        T results = (T) server.getClient()
                .read()
                .resource(type)
                .withId(new IdType(type.getTypeName(), id, version))
                .execute();
        return results;
    }

    @Override
    public List<IBaseResource> readObjectsFromBundle(Bundle result) {
        List<IBaseResource> resources = new ArrayList<>();
        resources.addAll(BundleUtil.toListOfResources(server.getContext(), result));
        while (result.getLink(IBaseBundle.LINK_NEXT) != null) {
            result = server.getClient()
                    .loadPage()
                    .next(result)
                    .execute();
            resources.addAll(BundleUtil.toListOfResources(server.getContext(), result));
        }
        return resources;
    }

}
