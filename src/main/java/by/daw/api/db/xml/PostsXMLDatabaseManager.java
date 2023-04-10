package by.daw.api.db.xml;

import by.daw.api.db.Post;
import by.daw.api.db.Posts;
import by.daw.api.db.behaviour.PostsDatabaseManager;
import by.daw.api.db.exceptions.NoSuchPostException;
import by.daw.api.db.exceptions.RuntimeJAXBException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase manejadora de una una BBDD de posts en formato XML. Para
 * ver un ejemplo de la estructura de esta vayase a: resources/samples/PostsDBSample.xml
 *
 * Esta clase para leer y escribir posts debe cargar toda la BBDD en memoria, cosa la
 * cual no es muy eficiente. Pero, ¿sabes qué?, me la pela, ya que no se tendrán muchos
 * posts.
 *
 * Usando SAX (Simple API for XML) permite leer posts sin cargarlos todos en memoria. En un futuro
 * puede rentar implementarlo.
 *
 */
public class PostsXMLDatabaseManager implements PostsDatabaseManager {
    // TODO --> IMPLEMENT FUNCTIONS FOR ADDING AND DELETING COMMENTS
    private static PostsXMLDatabaseManager instance;
    private final JAXBContext jaxbContext;
    private File postsDB;

    private PostsXMLDatabaseManager() {
        try {
            this.jaxbContext = JAXBContext.newInstance(Posts.class);
        } catch (JAXBException e) {
            throw new RuntimeJAXBException(e);
        }
        this.loadDatabase();
    }

    public static PostsXMLDatabaseManager getInstance() {
        if(instance == null){
            instance = new PostsXMLDatabaseManager();
        }
        return instance;
    }


    /**
     * Intenta construir un archivo File que representa la base de datos,
     * valida que esta exista y las asigna a this.postsDB
     *
     * @throws NullPointerException     si la propiedad postsDatabase.path no está definida.
     * @throws IllegalArgumentException si el fichero de las base de datos no existe.
     */
    private void loadDatabase() throws NullPointerException, IllegalArgumentException {
        String postsDBPath = System.getProperty("postsDatabase.path");
        if (postsDBPath == null) {
            throw new NullPointerException("Property 'postsDatabase.path' is not defined");
        }
        this.postsDB = new File(postsDBPath);
        if (!this.postsDB.exists()) {
            throw new IllegalArgumentException("ERROR: " + postsDBPath + " does not exist");
        }

    }

    /**
     * Lee los posts de de la BBDD XML y los deserializa en
     * una lista de objetos Post, contenidos en un objeto Posts, que actúa como wrapper.
     * Este método adquiere el candado intrínsico y bloquea el objeto para
     * otros hilos, lo que evita problemas de concurrencia.
     *
     * @return Los posts contenidas en una instancia de Posts
     */
    private Posts unmarshallPosts() throws RuntimeJAXBException {
        Posts posts;
        try {
            Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
            synchronized(this) {
                posts = (Posts) unmarshaller.unmarshal(this.postsDB);
            }
        } catch (JAXBException e) {
            throw new RuntimeJAXBException(e);
        }
        return posts;
    }

    /**
     * Serializa una instancia de Posts en formato XML en la BBDD
     * Este método adquiere el candado intrínsico y bloquea el objeto para
     * otros hilos, lo que evita problemas de concurrencia.
     *
     * @param posts los posts a serializar
     */

    private void marshallPosts(Posts posts) throws RuntimeJAXBException {
        try {
            Marshaller marshaller = this.jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            synchronized(this) {
                marshaller.marshal(posts, this.postsDB);
            }
        } catch (JAXBException e) {
            throw new RuntimeJAXBException(e);
        }
    }



    /**
     * Todos los objetos Post que existen en la BBDD.
     * En caso de no existir posts, devolverá una lista vacía.
     *
     * @return una lista de tipo Post
     * @throws RuntimeJAXBException  si hay algún error en el marshalling o unmarshalling
     */
    @Override
    public List<Post> getPosts() throws RuntimeJAXBException {
        Posts posts = this.unmarshallPosts();
        List<Post> postsList = posts.getPosts();

        // Mejor devolver una lista vacía antes que null
        if(postsList.size() < 1){
            return new ArrayList<>();
        }
        return postsList;
    }

    /**
     * Devuelve el post con tal id
     *
     * @param id el id del post
     * @return el objeto Post que coincide con tal ID
     * @throws NoSuchPostException si no se encuentra un post con tal ID
     * @throws RuntimeJAXBException  si hay algún error en el marshalling o unmarshalling
     */
    @Override
    public Post getPostByID(String id) throws NoSuchPostException, RuntimeJAXBException {
        Posts posts = this.unmarshallPosts();
        Post target = null;

        for(Post post : posts.getPosts()){
            if(post.getId().equals(id)){
                target = post;
            }
        }
        if(target == null){
            throw new NoSuchPostException("Post with ID: " + id + " does not exist!");
        }

        return target;
    }

    /**
     * Añade un post a la BBDD
     *
     * @param post el objeto que será seraliado a XML
     * @throws RuntimeJAXBException  si hay algún error en el marshalling o unmarshalling
     */
    @Override
    public void createPost(Post post) throws RuntimeJAXBException {
        post.setId(this.provideNewID());

        List<Post> posts = this.getPosts();
        posts.add(post);

        this.marshallPosts(new Posts(posts));

    }


    /**
     * Devuelve el siguiente ID disponible
     *
     * @return el id disponible
     *
     */
    private String provideNewID() {
        // Ejemplo: u0001
        String lastID = this.getLastID();
        DecimalFormat df = new DecimalFormat("0000");

        if(lastID == null){
            lastID = "u" + df.format(1);
        }

        String numberPartFromString = lastID.substring(1);
        int numberPart = Integer.parseInt(numberPartFromString);

        return "u" + df.format(numberPart+1) ;
    }

    /**
     * Devuelve el id del último usuario registrado
     *
     * @throws RuntimeJAXBException si hay algún error en el marshalling o unmarshalling
     * @return el id del último post o null si no hay posts
     */
    private String getLastID() throws RuntimeJAXBException {
        List<Post> posts = this.getPosts();
        if(posts.size() < 1){
            return null;
        }
        Post lastPost = posts.get(posts.size()-1);
        return lastPost.getId();
    }



    /**
     * Elimina el post con tal ID de la BBDD
     *
     * @param postID el id del post
     * @throws NoSuchPostException si no existe un post con tal ID
     * @throws RuntimeJAXBException  si hay algún error en el marshalling o unmarshalling
     */
    @Override
    public void deletePost(String postID) throws NoSuchPostException, RuntimeJAXBException {
        List<Post> posts = this.getPosts();
        Post toDelete = null;

        for(Post post : posts){
            if(post.getId().equals(postID)){
                toDelete = post;
            }
        }
        if(toDelete == null){
            throw new NoSuchPostException("Post with ID: " + postID + " does not exist!");
        }

        posts.remove(toDelete);
        this.marshallPosts(new Posts(posts));
    }

}
