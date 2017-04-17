package com.example.data;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Solovyev on 13/04/2017.
 */
@SuppressWarnings({"LocalCanBeFinal", "OverlyBroadCatchBlock", "SameParameterValue"})
@Service
public class ResourceFactory {

    private final ObjectMapper objectMapper;

    public ResourceFactory() {
        this.objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * @param path - path to resource
     * @return Resource
     * @throws ResourceException - if cant find or parse the resource
     */
    public Resource get(String path) {

        URL resourceDescriptor;
        try {
            resourceDescriptor = Resources.getResource(path);
        } catch (IllegalArgumentException ex) {
            throw new ResourceException("Unable to find resource " + path, ex);
        }

        RawResource rawResource;
        String resourceContent;
        try {
            resourceContent = Files.toString(new File(resourceDescriptor.getFile()), Charset.forName("UTF-8"));

            rawResource = objectMapper.readValue(resourceContent, RawResource.class);
        } catch (IOException e) {
            throw new ResourceException("Unable to parse resource " + path, e);
        }

        if (rawResource.getType() == null) {
            throw new ResourceException("Resource does not have 'type' field. Resource: " + path);
        }

        Class<?> aClass;
        try {
            aClass = Class.forName(rawResource.getType());
        } catch (ClassNotFoundException e) {
            throw new ResourceException("No corresponding resource class " + rawResource.getType() + " for resource " + path + " found", e);
        }

        if (!checkIfResource(aClass)) {
            throw new ResourceException("Trying to deserialize a nonresource class " + rawResource.getType() + " . For resource " + path);
        }

        try {
            return (Resource) objectMapper.readValue(resourceContent, aClass);
        } catch (IOException e) {
            throw new ResourceException("Failed constructing resource object " + path + " of type " + rawResource.getType(), e);
        }
    }

    public <T extends Resource> T get(String path, Class<T> clazz) {
        URL resourceDescriptor;
        try {
            resourceDescriptor = Resources.getResource(path);
        } catch (IllegalArgumentException ex) {
            throw new ResourceException("Unable to find resource " + path, ex);
        }

        T resource;
        try {
            resource = objectMapper.readValue(resourceDescriptor, clazz);
        } catch (IOException e) {
            throw new ResourceException("Failed constructing resource object " + path + " of type " + clazz.getName(), e);
        }

        if(!resource.getType().equals(clazz.getName())) {
           throw new ResourceException("type mismatch for resource " + path + ". Expected " + clazz.getName() +
                   " , but got " + resource.getType());
        }

        return resource;
    }

    private boolean checkIfResource(Class<?> clazz) {
        Class<?> superclass;
        while ((superclass = clazz.getSuperclass()) != null) {
            if (superclass == Resource.class) {
                return true;
            }
        }
        return false;
    }


}
