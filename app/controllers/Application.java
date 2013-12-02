package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import models.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render( 
            Project.findOwnedProjects(1L),
            Project.findInvolving(1L),
            User.findFriends(1L)
        )); 
    }

}
