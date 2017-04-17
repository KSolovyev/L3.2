package com.example.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Solovyev on 13/04/2017.
 */
public abstract class Resource {

    private String type;

    public Resource() {
    }

    public Resource(@JsonProperty("type") String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
