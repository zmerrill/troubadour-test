package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Note extends Model {

	@Id
	public Long id;
    public String text;

    public Note() { }

    public static Model.Finder<Long,Note> find = new Model.Finder(Long.class, Note.class);
}