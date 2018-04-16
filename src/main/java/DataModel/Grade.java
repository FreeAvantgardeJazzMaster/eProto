package DataModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Grade {
    private int id;
    private float value;
    private Date date;
    private Course course;

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

    public Grade(){
        this.date = new Date();
    }

    public Grade(float value, Course course, int id){
        this.course = course;
        this.date = new Date();
        this.value = value;
        this.id = id;
    }

}
