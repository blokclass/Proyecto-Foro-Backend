package by.daw.api.db;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "posts")
public class Posts {
    private List<Post> posts;


    /**
     * Constructor reservado para el proceso de marshalling (escritura) y unmarshalling (lectura)
     * de JABX. Este no debería usarse en otro contexto.
     */
    public Posts(){
    }

    public Posts(List<Post> posts) {
        this.posts = posts;
    }

    @XmlElement(name = "post")
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "posts=" + posts +
                '}';
    }
}
