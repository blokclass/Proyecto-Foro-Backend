package by.daw.api.db;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.Objects;

public class Comment {

    private String id;
    private String owner;
    private String content;

    /**
     * Constructor reservado para el proceso de marshalling (escritura) y unmarshalling (lectura)
     * de JABX. Este no debería usarse en otro contexto.
     */
    public Comment() {

    }

    public Comment(String owner, String content) {
        this.owner = owner;
        this.content = content;
    }

    @XmlAttribute(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @XmlElement(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "owner='" + owner + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(owner, comment.owner) && Objects.equals(content, comment.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, content);
    }
}
