package DataModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

@XmlRootElement
@Embedded
public class Grade {
    private int id;
    private float value;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern= "yyyy-MM-dd" , timezone= "CET")
    private Date date;
    @Reference
    private Course course;
    @XmlTransient
    private int studentIndex;

    @InjectLinks({
            @InjectLink(value = "/students/{studentIndex}/grades/{id}", rel = "self"),
            @InjectLink(value = "/students/{studentIndex}/grades", rel = "parent")
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setStudentIndex(int studentIndex) {
        this.studentIndex = studentIndex;
    }

    public int getStudentIndex() {
        return studentIndex;
    }

    public Grade(){
        this.date = new Date();
    }

    public Grade(float value, Course course, int id, int studentIndex){
        this.course = course;
        this.date = new Date();
        this.value = value;
        this.id = id;
        this.studentIndex = studentIndex;
    }

}
