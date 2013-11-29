package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class User extends Model {

    @Id
    public Long id;
    public String email;
    public String name;
    public String password;
    public String state;
    public String city;
    public String zip;
    public String genres;
    public String soundsLike;
    public String bio;
    
    public User(String email, String name, String password) {
      this.email = email;
      this.name = name;
      this.password = password;
    }

    public static Finder<Long,User> find = new Finder<Long,User>(
        Long.class, User.class
    ); 

    public static User authenticate(String email, String password) {
        return find.where().eq("email", email)
            .eq("password", password).findUnique();
    }
}