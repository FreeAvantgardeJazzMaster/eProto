package DataModel;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBase {
    private List<Student> students = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<Grade> grades = new ArrayList<>();

    public DataBase(){
        courses.add(new Course("WF", "Janek Stanek"));
        courses.add(new Course("IT", "Robert Brylewski"));
        courses.add(new Course("Integracja", "Patryk Kuśmierkiewicz"));


        grades.add(new Grade((float) 3.5, getCourseByName("WF")));
        grades.add(new Grade((float) 4, getCourseByName("IT")));
        grades.add(new Grade((float) 5, getCourseByName("Integracja")));
        students.add(new Student("Adam", "Kokosza", new Date(1995, 8, 10), grades));

        grades = new ArrayList<>();

        grades.add(new Grade((float) 5, getCourseByName("WF")));
        grades.add(new Grade((float) 5, getCourseByName("IT")));
        grades.add(new Grade((float) 3, getCourseByName("Integracja")));
        students.add(new Student("Murzynek", "Bambo", new Date(1995, 12, 24), grades));

        grades = new ArrayList<>();

        grades.add(new Grade((float) 4, getCourseByName("WF")));
        grades.add(new Grade((float) 4.5, getCourseByName("IT")));
        grades.add(new Grade((float) 5, getCourseByName("Integracja")));
        students.add(new Student("Przemysław", "Wojtczak", new Date(1994, 4, 1), grades));
    }

    public List<Student> getStudents(){
        return students;
    }

    public Student getStudentByIndex(int index){
        for(Student student : students){
            if(student.getIndex() == index)
                return student;
        }
        return null;
    }

    public List<Grade> getStudentByIndexGrades(int index){
        for(Student student : students){
            if(student.getIndex() == index)
                return student.getGrades();
        }
        return null;
    }

    public Grade getStudentByIndexGradeById(int index, int id){
        for(Student student : students){
            if(student.getIndex() == index)
                for(Grade grade : student.getGrades()){
                    if(grade.getId() == id)
                        return grade;
                }
        }
        return null;
    }

    public void postStudent(Student student){
        students.add(student);
    }

    public Response putStudent(int index, Student newStudent){
        for(Student student : students) {
            if (student.getIndex() == index) {
                student.setIndex(newStudent.getIndex());
                student.setGrades(newStudent.getGrades());
                student.setBirthDate(newStudent.getBirthDate());
                student.setFirstName(newStudent.getFirstName());
                student.setLastName(newStudent.getLastName());
                return Response.status(Response.Status.OK).build();
            }
        }
        students.add(newStudent);
        return Response.status(Response.Status.CREATED).build();
    }

    public Response deleteStudent(int index){
        if(students.remove(getStudentByIndex(index)))
            return Response.status(Response.Status.OK).build();
        else
            return Response.status(Response.Status.NO_CONTENT).build();

    }

    public List<Course> getCourses(){
        return courses;
    }

    public Course getCourseById(int id){
        for(Course course : courses){
            if (course.getId() == id)
                return course;
        }
        return null;
    }

    private Course getCourseByName(String name){
        for(Course course : courses){
            if (course.getName() == name)
                return course;
        }
        return null;
    }

    public void postCourse(Course course){
        courses.add(course);
    }

    public Response putCourse(int id, Course newCourse){
        for(Course course : courses) {
            if (course.getId() == id) {
                course.setId(newCourse.getId());
                course.setName(newCourse.getName());
                course.setLecturer(newCourse.getLecturer());
                return Response.status(Response.Status.OK).build();
            }
        }
        courses.add(newCourse);
        return Response.status(Response.Status.CREATED).build();
    }

    public Response deleteCourse(int id){
        if(courses.remove(getCourseById(id)))
            return Response.status(Response.Status.OK).build();
        else
            return Response.status(Response.Status.NO_CONTENT).build();

    }

    public void postGrade(int index, Grade grade){
        for(Student student : students){
            if (student.getIndex() == index){
                List<Grade> _grades = student.getGrades();
                _grades.add(grade);
                student.setGrades(_grades);
            }
        }
    }

    public Response putGrade(int index, int id, Grade newGrade){
        for (Student student : students){
            if (student.getIndex() == index){
                for(Grade grade : student.getGrades()) {
                    if (grade.getId() == id) {
                        grade.setId(newGrade.getId());
                        grade.setValue(newGrade.getValue());
                        grade.setCourse(newGrade.getCourse());
                        grade.setDate(newGrade.getDate());
                        return Response.status(Response.Status.OK).build();
                    }
                }
                List<Grade> _grades = student.getGrades();
                _grades.add(newGrade);
                student.setGrades(_grades);
                return Response.status(Response.Status.CREATED).build();
            }
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    public Response deleteGrade(int index, int id){
        for(Student student : students){
            if (student.getIndex() == index){
                if(student.getGrades().remove(getStudentByIndexGradeById(index, id)))
                    return Response.status(Response.Status.OK).build();
                else
                    return Response.status(Response.Status.NO_CONTENT).build();
            }
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
