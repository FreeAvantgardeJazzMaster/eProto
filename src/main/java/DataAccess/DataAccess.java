package DataAccess;

import DataModel.*;
import Main.DatabaseInit;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DataAccess {
    private static List<Student> students = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    private static List<Grade> grades = new ArrayList<>();

    private static AtomicInteger gradeCounter = new AtomicInteger(0);

    private static Datastore datastore = DatabaseInit.getDatastore();
    static{

        if (!(datastore.getCount(StudentIndex.class) > 0)){
            StudentIndex studentIndex = new StudentIndex(0);
            datastore.save(studentIndex);
        }

        if (!(datastore.getCount(CourseID.class) > 0)){
            CourseID courseID = new CourseID(0);
            datastore.save(courseID);
        }

        if (!(datastore.getCount(GradeID.class) > 0)){
            GradeID gradeID = new GradeID(0);
            datastore.save(gradeID);
        }

        if (!(datastore.getCount(Student.class) > 0)) {

            courses.add(new Course("WF", "Janek Stanek", getCourseId()));
            courses.add(new Course("IT", "Robert Brylewski", getCourseId()));
            courses.add(new Course("Integracja", "Patryk Kuśmierkiewicz", getCourseId()));
            datastore.save(courses);


            int studentIndex = getStudentIndex();
            grades.add(new Grade((float) 3.5, getCourseByName("WF"), getGradeId(), studentIndex));
            grades.add(new Grade((float) 4, getCourseByName("IT"), getGradeId(), studentIndex));
            grades.add(new Grade((float) 5, getCourseByName("Integracja"), getGradeId(), studentIndex));
            students.add(new Student("Adam", "Kokosza", new Date(95, 8, 10), grades, studentIndex));

            grades = new ArrayList<>();

            studentIndex = getStudentIndex();
            grades.add(new Grade((float) 5, getCourseByName("WF"), getGradeId(), studentIndex));
            grades.add(new Grade((float) 5, getCourseByName("IT"), getGradeId(), studentIndex));
            grades.add(new Grade((float) 3, getCourseByName("Integracja"), getGradeId(), studentIndex));
            students.add(new Student("Murzynek", "Bambo", new Date(95, 12, 24), grades, studentIndex));

            grades = new ArrayList<>();

            studentIndex = getStudentIndex();
            grades.add(new Grade((float) 4, getCourseByName("WF"), getGradeId(), studentIndex));
            grades.add(new Grade((float) 4.5, getCourseByName("IT"), getGradeId(), studentIndex));
            grades.add(new Grade((float) 5, getCourseByName("Integracja"), getGradeId(), studentIndex));
            students.add(new Student("Przemysław", "Wojtczak", new Date(94, 4, 1), grades, studentIndex));


            datastore.save(students);
        }
    }

    private static int getStudentIndex(){
        Query<StudentIndex> query = datastore.find(StudentIndex.class);
        int id = query.get().getIndexCount() + 1;
        UpdateOperations<StudentIndex> updateOperations = datastore.createUpdateOperations(StudentIndex.class).set("indexCount", id);
        datastore.findAndModify(query, updateOperations);

        return id;
    }

    private static int getCourseId(){
        Query<CourseID> query = datastore.find(CourseID.class);
        int id = query.get().getId() + 1;
        UpdateOperations<CourseID> updateOperations = datastore.createUpdateOperations(CourseID.class).set("id", id);
        datastore.findAndModify(query, updateOperations);

        return id;
    }

    private static int getGradeId(){
        Query<GradeID> query = datastore.find(GradeID.class);
        int id = query.get().getId() + 1;
        UpdateOperations<GradeID> updateOperations = datastore.createUpdateOperations(GradeID.class).set("id", id);
        datastore.findAndModify(query, updateOperations);

        return id;
    }


    private static int getFirstAvailableStudentIndex(){
        boolean isFound = true;
        int index = 0;
        while(isFound) {
            isFound = false;
            index = getStudentIndex();
            if (StudentAdapter.getStudentByIndex(index) != null)
                isFound = true;
        }
        return index;
    }

    private static int getPreferredStudentIndex(int index){
        boolean isFound = true;
        while(isFound) {
            isFound = false;
            if (StudentAdapter.getStudentByIndex(index) != null) {
                isFound = true;
                index++;
            }
        }
        return index;
    }

    private static int getFirstAvailableCourseId(){
        boolean isFound = true;
        int id = 0;
        while(isFound) {
            isFound = false;
            id = getCourseId();
            if (CourseAdapter.getCourseById(id) != null)
                isFound = true;
        }
        return id;
    }

    private static int getPreferredCourseId(int id){
        boolean isFound = true;
        while(isFound) {
            isFound = false;
            for (Course course : courses) {
                if (id == course.getId()) {
                    isFound = true;
                    id++;
                }
            }
        }
        return id;
    }

    private static int getFirstAvailableGradeId(){
        boolean isFound = true;
        int id = 0;
        while(isFound) {
            isFound = false;
            id = gradeCounter.incrementAndGet();
            for (Grade grade : grades) {
                if (id == grade.getId())
                    isFound = true;
            }
        }
        return id;
    }

    private static int getPreferredGradeId(int id){
        boolean isFound = true;
        while(isFound) {
            isFound = false;
            for (Grade grade : grades) {
                if (id == grade.getId()) {
                    isFound = true;
                    id++;
                }
            }
        }
        return id;
    }


    public static List<Student> getStudents(){
        return StudentAdapter.getStudents();
    }

    public static Student getStudentByIndex(int index){
        return StudentAdapter.getStudentByIndex(index);
    }

    public static List<Grade> getStudentByIndexGrades(int index){
        return StudentAdapter.getStudentByIndexGrades(index);
    }

    public static Grade getStudentByIndexGradeById(int index, int id){
        return StudentAdapter.getStudentByIndexGradesById(index, id);
    }

    public static Student postStudent(Student student){
        student.setIndex(getFirstAvailableStudentIndex());
        StudentAdapter.addStudent(student);

        return student;
    }

    public static Response putStudent(int index, Student newStudent){
        Student student = StudentAdapter.getStudentByIndex(index);
            if (student != null) {
                student.setGrades(newStudent.getGrades());
                student.setBirthDate(newStudent.getBirthDate());
                student.setFirstName(newStudent.getFirstName());
                student.setLastName(newStudent.getLastName());
                StudentAdapter.updateStudent(student);

                return Response.status(Response.Status.OK).build();
            }
        newStudent.setIndex(index);
        StudentAdapter.addStudent(newStudent);
        return Response.status(Response.Status.CREATED).header("Location", "http://localhost:8080/students/" + newStudent.getIndex()).build();
    }

    public static Response deleteStudent(int index){
        if(StudentAdapter.deleteStudent(index))
            return Response.status(Response.Status.OK).build();
        else
            return Response.status(Response.Status.NO_CONTENT).build();
    }


    public static List<Course> getCourses(){
        return CourseAdapter.getCourses();
    }

    public static Course getCourseById(int id){
        return CourseAdapter.getCourseById(id);
    }

    private static Course getCourseByName(String name){
        return CourseAdapter.getCourseByName(name);
    }

    public static Course postCourse(Course course){
        course.setId(DataAccess.getFirstAvailableCourseId());
        CourseAdapter.addCourse(course);
        return course;
    }

    public static Response putCourse(int id, Course newCourse){
        Course course = CourseAdapter.getCourseById(id);
        if (course != null) {
            course.setName(newCourse.getName());
            course.setLecturer(newCourse.getLecturer());
            CourseAdapter.updateCourse(course);
            return Response.status(Response.Status.OK).build();
        }

        newCourse.setId(id);
        CourseAdapter.addCourse(newCourse);
        return Response.status(Response.Status.CREATED).header("Location", "http://localhost:8080/courses/" + newCourse.getId()).build();
    }

    public static Response deleteCourse(int id){
        if(CourseAdapter.deleteCourse(id))
            return Response.status(Response.Status.OK).build();
        else
            return Response.status(Response.Status.NO_CONTENT).build();

    }


    public static void postGrade(int index, Grade grade){
        List<Grade> grades = StudentAdapter.getStudentByIndexGrades(index);
        grade.setId(getGradeId());
        grades.add(grade);
        Student student = getStudentByIndex(index);
        student.setGrades(grades);

        StudentAdapter.updateStudent(student);
    }

    public static Response putGrade(int index, int id, Grade newGrade){
        Student student = StudentAdapter.getStudentByIndex(index);
        if (student != null){
            for(Grade grade : student.getGrades()) {
                if (grade.getId() == id) {
                    grade.setStudentIndex(index);
                    grade.setValue(newGrade.getValue());
                    grade.setCourse(newGrade.getCourse());
                    grade.setDate(newGrade.getDate());
                    StudentAdapter.updateStudent(student);
                    return Response.status(Response.Status.OK).build();
                }
            }
            List<Grade> grades = StudentAdapter.getStudentByIndexGrades(index);
            newGrade.setId(getGradeId());
            grades.add(newGrade);
            student.setGrades(grades);
            StudentAdapter.updateStudent(student);
            return Response.status(Response.Status.CREATED).header("Location", "http://localhost:8080/students/" + index + "/grades/" + newGrade.getId()).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    public static Response deleteGrade(int index, int id){
        Student student = getStudentByIndex(index);
        if (student != null){
            if(student.getGrades().remove(getStudentByIndexGradeById(index, id))) {
                StudentAdapter.updateStudent(student);
                return Response.status(Response.Status.OK).build();
            }
            else
                return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
