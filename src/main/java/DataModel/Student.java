package DataModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@XmlRootElement
public class Student {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int index;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private List<Grade> grades = new ArrayList<>();

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public Student(){
        this.index = count.incrementAndGet();
    }

    public Student(String firstName, String lastName, Date birthDate, List<Grade> grades){
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.index = count.incrementAndGet();
        this.grades = grades;
    }

   // private void addGrade(Grade grade){
   //     grades.add(grade);
   // }
}
