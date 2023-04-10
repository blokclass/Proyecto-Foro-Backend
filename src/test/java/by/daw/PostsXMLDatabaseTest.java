package by.daw;

import by.daw.api.db.Post;
import by.daw.api.db.exceptions.NoSuchPostException;
import by.daw.api.db.xml.PostsXMLDatabaseManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PostsXMLDatabaseTest {

    public PostsXMLDatabaseTest() {
        System.setProperty("postsDatabase.path", "NOT SETTED UP");
    }

    @Test
    public void addPostTest() {
        PostsXMLDatabaseManager manager = PostsXMLDatabaseManager.getInstance();

        Post post = new Post("Juanjo", "titulo", "buenas");
        assertDoesNotThrow(() -> manager.createPost(post));
    }

    @Test
    public void deletePostTest() {
        PostsXMLDatabaseManager manager = PostsXMLDatabaseManager.getInstance();

        Post post = new Post("Antonio", "titulo", "xd");
        manager.createPost(post);
        assertDoesNotThrow(() -> manager.deletePost(post.getId()));
    }
    @Test
    public void deletePostTestInverseLogic() {
        PostsXMLDatabaseManager manager = PostsXMLDatabaseManager.getInstance();

        assertThrows(NoSuchPostException.class, () -> manager.deletePost("AAA"));
    }

    @Test
    public void getPostTest() {
        PostsXMLDatabaseManager manager = PostsXMLDatabaseManager.getInstance();

        Post post = new Post("PRUEBA", "PRUEBA", "PRUEBA");
        manager.createPost(post);
        assertDoesNotThrow(() -> manager.getPostByID(post.getId()));
    }

    @Test
    public void getPostTestInverseLogic() {
        PostsXMLDatabaseManager manager = PostsXMLDatabaseManager.getInstance();
        assertThrows(NoSuchPostException.class, () -> manager.getPostByID("AAA"));
    }



}

