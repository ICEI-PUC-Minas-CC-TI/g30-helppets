package com.helppets.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.helppets.app.services.AuthService;
import com.helppets.routerannotations.annotations.Controller;
import com.helppets.routerannotations.annotations.Route;
import com.helppets.routerannotations.functionalities.GenericController;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import static spark.Spark.post;

@Controller(prefix="/api/")
public class ApiController extends GenericController {
    private final AuthService authService = new AuthService();

    public ApiController(String prefix) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        super(prefix);
    }

    @Route
    public void login() throws JsonProcessingException {
        post(routePrefix.concat("auth/login"), (request, response) -> {
            try {
                if (!request.contentType().equalsIgnoreCase("application/json")) {
                    return controllerMapper.writeValueAsString(returnError("Invalid contentType"));
                }

                return controllerMapper.writeValueAsString(authService.login(controllerMapper.readValue(request.body(), Map.class)));
            } catch (Exception e) {
                return controllerMapper.writeValueAsString(returnError(e.getMessage()));
            }
        });
    }

    @Route
    public void register() {
        post(routePrefix.concat("auth/register"), ((request, response) -> {
            try {
                if (!request.contentType().equalsIgnoreCase("application/json")) {
                    return controllerMapper.writeValueAsString(returnError("Invalid contentType"));
                }

                return controllerMapper.writeValueAsString(authService.register(controllerMapper.readValue(request.body(), Map.class)));
            }
            catch (Exception e) {
                return controllerMapper.writeValueAsString(returnError(e.getMessage()));
            }
        }));
    }
}
