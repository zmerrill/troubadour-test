package controllers.apis;

import play.*;
import play.mvc.*;
import models.*;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;     
import com.fasterxml.jackson.databind.node.ObjectNode;          
import play.mvc.BodyParser;
import views.html.*;
import java.util.*;

public class ProjectAPI extends Controller {

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

    @BodyParser.Of(BodyParser.Json.class)
    public static Result createProject() {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        Long id = json.findPath("userId").asLong();
        Integer tracksToCreate = json.findPath("trackCount").asInt();
        String name = json.findPath("name").textValue();
        
        if(id == null || tracksToCreate == null || name == null) {
          result.put("status", "KO");
          result.put("message", "Missing parameter");
          return badRequest(result);
        } else {
          Project project = Project.create(name, 4, id);
          result.put("status", "OK");
          result.put("message", "Hello " + id);
          result.put("projects", Json.toJson(project));
          return ok(result);
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result findProjectById() {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        Long id = json.findPath("projectId").asLong();
        
        if(id == null) {
          result.put("status", "KO");
          result.put("message", "Missing parameter");
          return badRequest(result);
        } else {
          Project project = Project.find.byId(id);
          result.put("status", "OK");
          result.put("message", "Found project: " + project.name);
          result.put("projects", Json.toJson(project));
          return ok(result);
        }
    }
}