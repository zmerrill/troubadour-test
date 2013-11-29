package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Track extends Model {

    @Id
    public Long id;
    public String title;
    @OneToOne
    public User assignedTo;
    public String path;
    @ManyToOne(cascade = CascadeType.REMOVE)
    public Project project;
    @ManyToMany(cascade = CascadeType.REMOVE)
    public List<Note> notes = new ArrayList<Note>();

    public static Model.Finder<Long,Track> find = new Model.Finder(Long.class, Track.class);

    public Track() { }

    public static List<Track> findAssignedTracks(Long user) {
       return find.where().eq("assignedTo.id", user).findList();
    }

    public static Track create(Track track, Long project) {
        Project p = Project.find.ref(project);
        p.trackCount++;
        track.project = p;
        track.title = "TRACK " + p.trackCount;
        track.save();
        p.save();
        return track;
    }
}