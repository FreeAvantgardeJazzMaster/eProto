package DataModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.concurrent.atomic.AtomicInteger;

@XmlRootElement
public class Course {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;
    private String name;
    private String lecturer;

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

    public Course(){
        this.id = count.incrementAndGet();
    }
    public Course(String name, String lecturer){
        this.name = name;
        this.lecturer = lecturer;
        this.id = count.incrementAndGet();
    }
}
