package com.helppets.app.controllers;

import com.helppets.routerannotations.annotations.Controller;
import com.helppets.routerannotations.annotations.Route;
import com.helppets.routerannotations.functionalities.GenericController;
import static spark.Spark.*;

@Controller
public class SiteController extends GenericController {
    public SiteController(String routePrefix) {
        super(routePrefix);
    }

    @Route
    public void index() {
        get("/", (req, res) -> {res.redirect("/html/index.html"); return null;});
    }

    @Route
    public void home() {
        get("/home", (req, res) -> {res.redirect("/html/home.html"); return null;});
    }

    @Route
    public void pets() {
        get("/pets", (req, res) -> {res.redirect("/html/pets.html"); return null;});
    }

    @Route
    public void calendario() {
        get("/calendario", (req, res) -> {res.redirect("/html/calendario.html");return null;});
    }
}
