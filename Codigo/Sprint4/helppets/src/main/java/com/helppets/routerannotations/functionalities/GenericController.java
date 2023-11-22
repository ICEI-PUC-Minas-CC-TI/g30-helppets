package com.helppets.routerannotations.functionalities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helppets.routerannotations.annotations.Controller;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.internalServerError;

public class GenericController {
    protected final ObjectMapper controllerMapper = new ObjectMapper();
    protected String routePrefix;

    public GenericController(String routePrefix) {
        this.routePrefix = routePrefix;
    }

    protected Map<String, String> returnError(String error){
        Map<String, String> map = new HashMap<>();

        map.put("error", error);

        return map;
    }
}
