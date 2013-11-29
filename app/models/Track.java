package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Track extends Model {

    @Id
    public Long id;
    public String title;
    @ManyToOne(cascade = CascadeType.REMOVE)
    public User assignedTo;
    public String path;
    @ManyToOne(cascade = CascadeType.REMOVE)
    public Project project;

    public static Model.Finder<Long,Track> find = new Model.Finder(Long.class, Track.class);

    public Track(Project project, User owner, String title) {
        this.title = title;
        this.project = project;
        this.assignedTo = owner;
    }

    public static List<Track> findAssignedTracks(Long user) {
       return find.where().eq("assignedTo.id", user).findList();
    }

    public static List<Track> findProjectTracks(Long project){
        return Track.find.where().eq("project.id", project).findList();
    }

    public static Track create(Project p) {
        int newCount = findProjectTracks(p.id).size() + 1;
        Track track = new Track(p, p.owner, "TRACK " + newCount);
        track.save();
        return track;
    }
}