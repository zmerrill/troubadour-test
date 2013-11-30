package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;
import java.util.*;

@Entity
public class UserRelationship extends Model {

    @Id
    public Long id;
    @ManyToOne(cascade = CascadeType.REMOVE)
    public User u1;
    @ManyToOne(cascade = CascadeType.REMOVE)
    public User u2;
    
    public UserRelationship(User u1, User u2) {
      this.u1 = u1;
      this.u2 = u2;
    }

    public static Finder<Long,UserRelationship> find = new Finder<Long,UserRelationship>(
        Long.class, UserRelationship.class
    ); 

    public static UserRelationship create(User u1, User u2) {
        UserRelationship r1 = new UserRelationship(u1, u2);
        r1.save();
        return r1;
    }

    public static List<UserRelationship> findRelationships(Long user) {
        List<UserRelationship> l1 = find.where().eq("u1.id", user).findList();
        List<UserRelationship> l2 = find.where().eq("u2.id", user).findList();
        List<UserRelationship> newList = new ArrayList<UserRelationship>();
		newList.addAll(l1);
		newList.addAll(l2);
		return newList;
    }
}