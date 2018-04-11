package DataModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@XmlRootElement
public class Grade {
    private static final AtomicInteger count = new AtomicInteger(0);
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
        this.id = count.incrementAndGet();
    }

    public Grade(float value, Course course){
        this.course = course;
        this.date = new Date();
        this.value = value;
        this.id = count.incrementAndGet();
    }

}
