package controllers;

import play.*;
import play.mvc.*;
import models.*;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;     
import com.fasterxml.jackson.databind.node.ObjectNode;          
import play.mvc.BodyParser;
import views.html.*;
import java.util.*;

public class API extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
	public static Result sayHello() {
  		JsonNode json = request().body().asJson();
  		ObjectNode result = Json.newObject();
  		String name = json.findPath("name").asText();
  		if(name == null) {
    		result.put("status", "KO");
    		result.put("message", "Missing parameter [name]");
    		return badRequest(result);
  		} else {
    		result.put("status", "OK");
    		result.put("message", "Hello " + name);
    		return ok(result);
  		}
	}

	@BodyParser.Of(BodyParser.Json.class)
	public static Result getOwnedProjects() {
  		JsonNode json = request().body().asJson();
  		ObjectNode result = Json.newObject();
  		Long id = json.findPath("userId").asLong();
  		System.err.println(id + " THATA THA ID");
  		
  		if(id == null) {
    		result.put("status", "KO");
    		result.put("message", "Missing parameter [name]");
    		return badRequest(result);
  		} else {
  			List<Project> projects = Project.findOwnedProjects(id);
    		result.put("status", "OK");
    		result.put("message", "Hello " + id);
    		result.put("projects", Json.toJson(projects));
    		return ok(result);
  		}
	}

	@BodyParser.Of(BodyParser.Json.class)
	public static Result getInvolvedProjects() {
  		JsonNode json = request().body().asJson();
  		ObjectNode result = Json.newObject();
  		Long id = json.findPath("userId").asLong();
  		System.err.println(id + " THATA THA ID");
  		
  		if(id == null) {
    		result.put("status", "KO");
    		result.put("message", "Missing parameter [name]");
    		return badRequest(result);
  		} else {
  			List<Project> projects = Project.findInvolving(id);
    		result.put("status", "OK");
    		result.put("message", "Hello " + id);
    		result.put("projects", Json.toJson(projects));
    		return ok(result);
  		}
	}
}