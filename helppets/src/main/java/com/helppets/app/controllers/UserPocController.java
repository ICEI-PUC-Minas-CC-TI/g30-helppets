package com.helppets.app.controllers;

import static spark.Spark.*;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helppets.app.daos.UsuarioDAO;
import com.helppets.app.models.UsuarioModel;
import com.helppets.app.utils.AuthUtils;
import com.helppets.routerannotations.annotations.Controller;
import com.helppets.routerannotations.annotations.Route;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserPocController {
    private final ObjectMapper mapper = new ObjectMapper();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final AuthUtils authUtils = new AuthUtils();

    public UserPocController() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
    }

    @Route
    public void pocInsertUser() {
        post("/user", "application/json", (req, res) -> {
            res.type("application/json");

            try {
                if (!req.contentType().equalsIgnoreCase("application/json")) {
                    return mapper.writeValueAsString(returnError("Invalid content type"));
                }

                Map<String, Object> body = mapper.readValue(req.body(), Map.class);

                body.put("senha", authUtils.returnCryptedPassword((String) body.get("senha")));

                UsuarioModel user = usuarioModelFromMap(body);

                return mapper.writeValueAsString(usuarioDAO.insert(user));
            }
           catch (Exception e) {
                return mapper.writeValueAsString(returnError(e.getMessage()));
           }
        });
    }

    @Route
    public void pocReadUserById() {
        get("/user/:id", (req, res) -> {
            res.type("application/json");

            try {
                return mapper.writeValueAsString(usuarioDAO.getById(Integer.parseInt(req.params("id"))));
            }
            catch (Exception e) {
                return mapper.writeValueAsString(returnError(e.getMessage()));
            }
        });
    }

    @Route
    public void pocReadUserByLimit() {
        get("/user", (req, res) -> {
            res.type("application/json");

            try {
                Map<String, Object> map = new HashMap<>();

                map.put("result", usuarioDAO.selectAllWithLimiter(Integer.parseInt(req.queryParams("limiter"))));

                return mapper.writeValueAsString(map);
            }
            catch (Exception e) {
                return mapper.writeValueAsString(returnError(e.getMessage()));
            }
        });
    }

    @Route
    public void pocUpdateUser() {
        put("/user/:id", "application/json", (req, res) -> {
            res.type("application/json");

            try {
                if (!req.contentType().equalsIgnoreCase("application/json")) {
                    return mapper.writeValueAsString(returnError("Invalid content type"));
                }

                Map<String, Object> body = mapper.readValue(req.body(), Map.class);

                body.put("senha", authUtils.returnCryptedPassword((String) body.get("senha")));

                UsuarioModel user = usuarioModelFromMap(body);

                return mapper.writeValueAsString(usuarioDAO.updateById(Integer.parseInt(req.params("id")), user));
            }
            catch (Exception e) {
                return mapper.writeValueAsString(returnError(e.getMessage()));
            }
        });
    }

    @Route
    public void pocDeleteUser() {
        delete("/user/:id", (req, res) -> {
            res.type("application/json");

            try {
                return mapper.writeValueAsString(usuarioDAO.deleteById(Integer.parseInt(req.params("id"))));
            }
            catch (Exception e) {
                return mapper.writeValueAsString(returnError(e.getMessage()));
            }
        });
    }

    private Map<String, String> returnError(String error){
        Map<String, String> map = new HashMap<>();

        map.put("error", error);

        return map;
    }

    private UsuarioModel usuarioModelFromMap(Map<String, Object> map) {
        UsuarioModel usuario = new UsuarioModel();

        usuario.setNome((String) map.get("nome"));
        usuario.setEmail((String) map.get("email"));
        usuario.setSenha((byte[]) map.get("senha"));

        return usuario;
    }
}
