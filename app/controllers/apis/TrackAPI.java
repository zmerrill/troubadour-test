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
  	public static Result getAssignedTracks() {
    		JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        Long id = json.findPath("userId").asLong();
        System.err.println(id + " THATA THA ID");
        
        if(id == null) {
          result.put("status", "KO");
          result.put("message", "Missing parameter [name]");
          return badRequest(result);
        } else {
          List<Track> tracks = Track.findAssignedTracks.(id);
          result.put("status", "OK");
          result.put("message", "Hello " + id);
          result.put("projects", Json.toJson(tracks));
          return ok(result);
        }
  	}

  	@BodyParser.Of(BodyParser.Json.class)
  	public static Result getProjectTracks() {
    		JsonNode json = request().body().asJson();
    		ObjectNode result = Json.newObject();
    		Long id = json.findPath("projectId").asLong();
    		System.err.println(id + " THATA THA ID");
    		
    		if(id == null) {
      		result.put("status", "KO");
      		result.put("message", "Missing parameter [name]");
      		return badRequest(result);
    		} else {
    			List<Track> tracks = Track.findProjectTracks.(id);
      		result.put("status", "OK");
      		result.put("message", "Hello " + id);
      		result.put("projects", Json.toJson(tracks));
      		return ok(result);
    		}
  	}

    @BodyParser.Of(BodyParser.Json.class)
    public static Result createTrack() {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        Long id = json.findPath("projectId").asLong();
        
        if(id == null) {
          result.put("status", "KO");
          result.put("message", "Missing parameter");
          return badRequest(result);
        } else {
          Project project = Project.find.byId(id);
          Track track = Track.create(project);
          result.put("status", "OK");
          result.put("message", "Hello " + id);
          result.put("projects", Json.toJson(track));
          return ok(result);
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result findTrackById() {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        Long id = json.findPath("trackId").asLong();
        
        if(id == null) {
          result.put("status", "KO");
          result.put("message", "Missing parameter");
          return badRequest(result);
        } else {
          Track track = Track.find.byId(id);
          result.put("status", "OK");
          result.put("message", "Found track: " + track.title);
          result.put("tracks", Json.toJson(track));
          return ok(result);
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result deleteTrackById() {
        
    }
}