package by.daw.api.db;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.Objects;

public class Post {
    private String id;
    private String owner;
    private String title;
    private String content;

    private String category;
    private int upvotesCounter;
    private Comments comments;

    /**
     * Constructor reservado para el proceso de marshalling (escritura) y unmarshalling (lectura)
     * de JABX. Este no debería usarse en otro contexto.
     */
    public Post(){
        this.id = "";
        this.owner = "";
        this.content = "";
        this.category = "";
        this.upvotesCounter = 0;
        this.comments = new Comments();
    }

    public Post(String owner, String title, String content) {
        this.id = "";
        this.owner = owner;
        this.title = title;
        this.content = content;
        this.category = "";
        this.upvotesCounter = 0;
        this.comments = new Comments();
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

    @XmlElement(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @XmlElement(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @XmlElement(name = "upvotes-counter")
    public int getUpvotesCounter() {
        return upvotesCounter;
    }

    public void setUpvotesCounter(int upvotesCounter) {
        this.upvotesCounter = upvotesCounter;
    }

    @XmlElement(name = "comments")
    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }


    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", content='" + content + '\'' +
                ", upvotesCounter=" + upvotesCounter +
                ", comments=" + comments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
