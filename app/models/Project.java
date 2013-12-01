package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Project extends Model {

    @Id
    public Long id;
    public String name;
    public int trackCount;
    public int bpm;
    @ManyToOne(cascade = CascadeType.REMOVE)
    public User owner;
    @ManyToMany(cascade = CascadeType.REMOVE)
    public List<User> collaborators = new ArrayList<User>();

    public Project(String name, int trackCount, User owner) {
        this.name = name;
        this.trackCount = trackCount;
        this.owner = owner;
    }

    public static Model.Finder<Long,Project> find = new Model.Finder(Long.class, Project.class);

    public static Project create(String name, int trackCount, Long owner) {
        Project project = new Project(name, trackCount, User.find.ref(owner));
        project.save();
        project.saveManyToManyAssociations("collaborators");
        return project;
    }

    public static List<Project> findInvolving(Long user) {
        return find.where()
            .eq("collaborators.id", user)
            .findList();
    }

    public static List<Project> findOwnedProjects(Long user){
        return find.where()
            .eq("owner.id", user)
            .findList();
    }
}