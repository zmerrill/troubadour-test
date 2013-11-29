package models;

import models.*;
import org.junit.*;
import static org.junit.Assert.*;
import play.test.WithApplication;
import play.libs.Yaml;
import java.util.List;
import com.avaje.ebean.Ebean;
import static play.test.Helpers.*;

public class ModelsTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
        Ebean.save((List) Yaml.load("test-data.yml"));
    }

    @Test
    public void createAndRetrieveUser() {
        new User("bob@gmail.com", "Bob", "secret").save();
        User bob = User.find.where().eq("email", "bob@gmail.com").findUnique();
        assertNotNull(bob);
        assertEquals("Bob", bob.name);
    }

    public void createAndRetrieveProject() {

    }

    public void createAndRetrieveTrack() {

    }

    public void createAndRetrieveNote() {
    	
    }

    @Test
    public void tryAuthenticateUser() {
        new User("bob@gmail.com", "Bob", "secret").save();
        
        assertNotNull(User.authenticate("bob@gmail.com", "secret"));
        assertNull(User.authenticate("bob@gmail.com", "badpassword"));
        assertNull(User.authenticate("tom@gmail.com", "secret"));
    }

    @Test
    public void fullTest() {

        // Count things
        assertEquals(4, User.find.findRowCount());
        assertEquals(11, Project.find.findRowCount());
        assertEquals(18, Track.find.findRowCount());

        // Try to authenticate as users
        assertNotNull(User.authenticate("paul@example.com", "secret"));
        assertNotNull(User.authenticate("john@example.com", "secret"));
        assertNull(User.authenticate("ringo@example.com", "badpassword"));
        assertNull(User.authenticate("dave@example.com", "secret"));

        Long id = User.authenticate("paul@example.com", "secret").id;

        // Find all Bob's collaborations
        List<Project> collaborations = Project.findInvolving(id);
        assertEquals(10, collaborations.size());

        // Find all Bob's projects
        List<Project> projects = Project.findOwnedProjects(id);
        assertEquals(2, projects.size());

        // Find all Bob's todo tasks
        List<Track> tracks = Track.findAssignedTracks(id);
        assertEquals(6, tracks.size());
    }
}