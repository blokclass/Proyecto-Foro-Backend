package by.daw.api.db.behaviour;

import by.daw.api.db.Post;
import by.daw.api.db.exceptions.NoSuchPostException;

import java.util.List;

/**
 * La interfaz que define el comportamiento
 * que debe tener una clase manejadora de la
 * base de datos de posts.
 */
public interface PostsDatabaseManager {

    /**
     * Todos los objetos Post que existen en la BBDD.
     * En caso de no existir posts, devolverá una lista vacía.
     *
     * @return una lista de tipo Post
     */
    List<Post> getPosts();

    /**
     * Devuelve el post con tal id
     *
     * @param id el id del post
     * @return el objeto Post que coincide con tal ID
     * @throws NoSuchPostException si no se encuentra un post con tal ID
     */
    Post getPostByID(String id) throws NoSuchPostException;


    /**
     * Añade un post a la BBDD
     * @param post el objeto que será seraliado a XML
     */
    void createPost(Post post);

    /**
     * Elimina el post con tal ID de la BBDD
     * @param postID el id del post
     * @throws NoSuchPostException si no existe un post con tal ID
     */
    void deletePost(String postID) throws NoSuchPostException;


    // TODO --> IMPLEMENT FUNCTIONS FOR ADDING AND DELETING COMMENTS


}
