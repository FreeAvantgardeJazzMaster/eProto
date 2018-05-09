package DataModel;

import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlRootElement
@Entity("courses")
public class Course {
    @Id
    @XmlTransient
    //@XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class)
    private ObjectId ID;
    private int id;
    private String name;
    private String lecturer;

    @InjectLinks({
            @InjectLink(value = "/courses/{id}", rel = "self"),
            @InjectLink(value = "/courses", rel = "parent")
    })
    @XmlElement(name = "link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    List<Link> links;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course(){ }

    public Course(String name, String lecturer, int id){
        this.name = name;
        this.lecturer = lecturer;
        this.id = id;
    }
    @XmlTransient
    public ObjectId getID() {
        return ID;
    }

    public void setID(ObjectId ID) {
        this.ID = ID;
    }
}
