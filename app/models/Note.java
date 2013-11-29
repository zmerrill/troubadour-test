package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Note extends Model {
	//TODO - ADD CREATE METHOD
	@Id
	public Long id;
	public String title;
    public String text;
    @ManyToOne(cascade = CascadeType.REMOVE)
    public Project project;
    @ManyToOne(cascade = CascadeType.REMOVE)
    public Track track;

    public Note() { }

    public static Model.Finder<Long,Note> find = new Model.Finder(Long.class, Note.class);

    public static List<Note> findProjectNotes(Long project){
        return Note.find.where().eq("project.id", project).findList();
    }

    public static List<Note> findTrackNotes(Long track){
        return Note.find.where().eq("track.id", track).findList();
    }
}