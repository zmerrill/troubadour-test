package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;
import java.util.*;

@Entity
public class User extends Model {

    @Id
    public Long id;
    public String email;
    private String name;
    public String password;
    public String state;
    public String city;
    public String zip;
    public String genres;
    public String soundsLike;
    public String bio;

    public String getName(){
      return this.name;
    }

    public void setName(String name){
      this.name = name;
    }
    
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

    public static List<User> findFriends(Long userId){
        List<UserRelationship> rel = UserRelationship.findRelationships(userId);
        List<User> friends = new ArrayList<User>();
        for(UserRelationship r : rel){
          Long friendId = null;
          if(r.u1.id.equals(userId))
            friendId = r.u2.id;
          else
            friendId = r.u1.id;

          User buddy = User.find.byId(friendId);
          friends.add(buddy);
        }
        return friends;
    }
}