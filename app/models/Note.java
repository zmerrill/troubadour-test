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

    public Note(Project p) { 
    	this.project = p;
    }

    public Note(Track t) {
    	this.track = t;
    }

    public static Model.Finder<Long,Note> find = new Model.Finder(Long.class, Note.class);

    public static List<Note> findProjectNotes(Long project){
        return Note.find.where().eq("project.id", project).findList();
    }

    public static List<Note> findTrackNotes(Long track){
        return Note.find.where().eq("track.id", track).findList();
    }

    public static Note create(Project p) {
        Note note = new Note(p);
        note.save();
        return note;
    }

    public static Note create(Track t) {
        Note note = new Note(t);
        note.save();
        return note;
    }
}