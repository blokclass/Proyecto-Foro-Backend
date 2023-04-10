package by.daw.api.db;

import jakarta.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

public class Comments {
    private List<Comment> comments;

    /**
     * Constructor reservado para el proceso de marshalling (escritura) y unmarshalling (lectura)
     * de JABX. Este no debería usarse en otro contexto.
     */
    public Comments() {
        this.comments = new ArrayList<Comment>();
    }

    public Comments(List<Comment> comments) {
        this.comments = comments;
    }

    @XmlElement(name = "comment")
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "comments=" + comments +
                '}';
    }
}
