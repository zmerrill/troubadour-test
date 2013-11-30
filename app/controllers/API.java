package controllers;

import play.*;
import play.mvc.*;
import models.*;

import views.html.*;

public class API extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
}