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
        //Ebean.save((List) Yaml.load("test-data.yml"));
    }

    @Test
    public void createAndRetrieveUser() {
        new User("bob@gmail.com", "Bob", "secret").save();
        User bob = User.find.where().eq("email", "bob@gmail.com").findUnique();
        assertNotNull(bob);
        assertEquals("Bob", bob.name);
    }

    @Test
    public void createAndRetrieveProject() {
    	User bob = new User("bob@gmail.com", "Bob", "secret");
        bob.save();

        Project project = Project.create("Testo", 4, bob.id);

        List<Project> results = Project.findOwnedProjects(bob.id);
        assertEquals(1, results.size());
        assertEquals("Testo", results.get(0).name);
    }

    @Test
    public void createAndRetrieveTrack() {
    	User bob = new User("bob@gmail.com", "Bob", "secret");
        bob.save();

        Project project = Project.create("Testo", 4, bob.id);
        Track t1 = Track.create(project);

        List<Track> results = Track.findAssignedTracks(bob.id);
        assertEquals(1, results.size());
        assertEquals("TRACK 1", results.get(0).title);   
    }

    @Test
    public void createAndRetrieveNote() {
    	User bob = new User("bob@gmail.com", "Bob", "secret");
        bob.save();

        Project project = Project.create("Testo", 4, bob.id);
        Note pNote = Note.create(project);
        pNote.title = "TITLE";
        pNote.text = "BODY";
        Track t1 = Track.create(project);
        Note tNote1 = Note.create(t1);
        tNote1.title = "TITLE";
        tNote1.text = "BODY";
        Note tNote2 = Note.create(t1);
        tNote2.title = "TITLE";
        tNote2.text = "BODY";

        List<Note> pNotes = Note.findProjectNotes(project.id);
        List<Note> tNotes = Note.findTrackNotes(t1.id);
        List<Note> allNotes = Note.find.all();

        assertEquals(1, pNotes.size());
        assertEquals(2, tNotes.size());
        assertEquals(3, allNotes.size());
    }

    @Test
    public void createAndRetrieveRelationship() {
    	User bob = new User("bob@gmail.com", "Bob", "secret");
        bob.save();
        User joe = new User("joe@gmail.com", "Joe", "secret");
        joe.save();
        User jim = new User("jim@gmail.com", "Jim", "secret");
        jim.save();

        UserRelationship.create(bob, joe);
        UserRelationship.create(bob, jim);

        List<UserRelationship> bobs = UserRelationship.findRelationships(bob.id);
        List<UserRelationship> jims = UserRelationship.findRelationships(jim.id);

        assertEquals(1, jims.size());
        assertEquals(2, bobs.size());

        List<User> joes = User.findFriends(joe.id);
        assertEquals(1, joes.size());
    }

    @Test
    public void tryAuthenticateUser() {
        new User("bob@gmail.com", "Bob", "secret").save();
        
        assertNotNull(User.authenticate("bob@gmail.com", "secret"));
        assertNull(User.authenticate("bob@gmail.com", "badpassword"));
        assertNull(User.authenticate("tom@gmail.com", "secret"));
    }

    @Test
    public void ownerNameTest() {
        User bob = new User("bob@gmail.com", "Bob", "secret");
        bob.save();

        Project project = Project.create("Testo", 4, bob.id);
        assertEquals(project.owner.id, bob.id);
        assertEquals(project.owner.name, bob.name);
    }

    @Test
    public void fullTest() {

        // Try to authenticate as users
        assertNotNull(User.authenticate("paul@example.com", "secret"));
        assertNotNull(User.authenticate("john@example.com", "secret"));
        assertNull(User.authenticate("ringo@example.com", "badpassword"));
        assertNull(User.authenticate("dave@example.com", "secret"));

        Long id = User.authenticate("paul@example.com", "secret").id;

        // Find all Bob's collaborations
        List<Project> collaborations = Project.findInvolving(id);
        assertEquals(8, collaborations.size());

        // Find all Bob's projects
        List<Project> projects = Project.findOwnedProjects(id);
        assertEquals(2, projects.size());

        // Find all Bob's todo tasks
        List<Track> tracks = Track.findAssignedTracks(id);
        assertEquals(6, tracks.size());
    }
}