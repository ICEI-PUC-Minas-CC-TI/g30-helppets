package com.helppets.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.helppets.app.services.AuthService;
import com.helppets.app.services.CalendarioService;
import com.helppets.app.services.PetsService;
import com.helppets.app.services.VacinaService;
import com.helppets.routerannotations.annotations.Controller;
import com.helppets.routerannotations.annotations.Route;
import com.helppets.routerannotations.functionalities.GenericController;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import static spark.Spark.*;

@Controller(prefix="/api/")
public class ApiController extends GenericController {
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final AuthService authService = new AuthService();
    private final PetsService petsService = new PetsService();
    private final VacinaService vacinaService = new VacinaService();
    private final CalendarioService calendarioService = new CalendarioService();

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

    @Route
    public void insertPet() {
        post(routePrefix.concat("pets/insert"), (request, response) -> {
            try {
                if (!request.contentType().equalsIgnoreCase("application/json")) {
                    return controllerMapper.writeValueAsString(returnError("Invalid contentType"));
                }

                if (request.headers(AUTHORIZATION_HEADER).isEmpty()) {
                    return controllerMapper.writeValueAsString(returnError("Invalid token"));
                }

                return controllerMapper.writeValueAsString(petsService.insertPet(request.headers(AUTHORIZATION_HEADER),
                                                           controllerMapper.readValue(request.body(), Map.class)));
            }
            catch (Exception e) {
                return controllerMapper.writeValueAsString(returnError(e.getMessage()));
            }
        });
    }

    @Route
    public void listRegisteredPets() {
        get(routePrefix.concat("pets/list"), ((request, response) -> {
            try {
                if (request.headers(AUTHORIZATION_HEADER).isEmpty()) {
                    return controllerMapper.writeValueAsString(returnError("Invalid token"));
                }

                return controllerMapper.writeValueAsString(petsService.listRegisteredPetsWithLimit(request.headers(AUTHORIZATION_HEADER),
                        Integer.parseInt(request.queryParams("limit"))));
            } catch (Exception e) {
                    return controllerMapper.writeValueAsString(returnError(e.getMessage()));
            }
        }));
    }

    @Route
    public void deletePetById() {
        delete(routePrefix.concat("pets/delete"), (request, response) -> {
            try {
                if (request.headers(AUTHORIZATION_HEADER).isEmpty()) {
                    return controllerMapper.writeValueAsString(returnError("Invalid token"));
                }

                return controllerMapper.writeValueAsString(petsService.deleteById(request.headers(AUTHORIZATION_HEADER),
                        Integer.parseInt(request.queryParams("id"))));
            }
            catch (Exception e) {
                return controllerMapper.writeValueAsString(returnError(e.getMessage()));
            }
        });
    }

    @Route
    public void insertVacina() {
        post(routePrefix.concat("vacinas/insert"), ((request, response) -> {
            try {
                if (!request.contentType().equalsIgnoreCase("application/json")) {
                    return controllerMapper.writeValueAsString(returnError("Invalid contentType"));
                }

                if (request.headers(AUTHORIZATION_HEADER).isEmpty()) {
                    return controllerMapper.writeValueAsString(returnError("Invalid token"));
                }

                return controllerMapper.writeValueAsString(vacinaService.insertVacina(request.headers(AUTHORIZATION_HEADER),
                                                                                      controllerMapper.readValue(request.body(), Map.class)));
            }
            catch (Exception e) {
                return controllerMapper.writeValueAsString(returnError(e.getMessage()));
            }
        }));
    }

    @Route
    public void returnVacinasByPetsId() {
        get(routePrefix.concat("vacinas/listByPetsId"), ((request, response) -> {
            try {
                if (request.headers(AUTHORIZATION_HEADER).isEmpty()) {
                    return controllerMapper.writeValueAsString(returnError("Invalid token"));
                }

                return controllerMapper.writeValueAsString(vacinaService.returnVacinasByPetsId(request.headers(AUTHORIZATION_HEADER),
                        Integer.valueOf(request.queryParams("petsId")),
                        Integer.valueOf(request.queryParams("limit"))));
            }
            catch (Exception e) {
                return controllerMapper.writeValueAsString(returnError(e.getMessage()));
            }
        }));
    }

    @Route
    public void insertEvent() {
        post(routePrefix.concat("calendario/insert"), (request, response) -> {
            try {
                if (request.headers(AUTHORIZATION_HEADER).isEmpty()) {
                    return controllerMapper.writeValueAsString(returnError("Invalid token"));
                }

                return controllerMapper.writeValueAsString(calendarioService.insertCalendario(request.headers(AUTHORIZATION_HEADER),
                                                                                              controllerMapper.readValue(request.body(), Map.class)));
            }
            catch (Exception e) {
                return controllerMapper.writeValueAsString(returnError(e.getMessage()));
            }
        });
    }

    @Route
    public void listEvents() {
        get(routePrefix.concat("calendario/list"), (request, response) -> {
            try {
                if (request.headers(AUTHORIZATION_HEADER).isEmpty()) {
                    return controllerMapper.writeValueAsString(returnError("Invalid token"));
                }

                return controllerMapper.writeValueAsString(calendarioService.getCalendarioByUserIdAndDay(request.headers(AUTHORIZATION_HEADER),
                                                                                                    request.queryParams("day"),
                                                                                                    Integer.parseInt(request.queryParams("limit")))
                );
            }
            catch (Exception e) {
                return controllerMapper.writeValueAsString(returnError(e.getMessage()));
            }
        });
    }
}
