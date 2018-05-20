package DataAccess;

import DataModel.Grade;
import DataModel.Student;
import DataModel.StudentIndex;
import Main.DatabaseInit;
import Utils.JsonError;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.Date;
import java.util.List;

public class StudentAdapter {
    private static Datastore datastore = DatabaseInit.getDatastore();

    public static List<Student> getStudents(){
        Query<Student> query = datastore.find(Student.class);
        return query.asList();
    }

    public static Student getStudentByIndex(int index){
        Query<Student> query = datastore.find(Student.class).field("index").equal(index);
        return query.get();
    }

    public static List<Grade> getStudentByIndexGrades(int index){
        Query<Student> query = datastore.find(Student.class).field("index").equal(index);
        if (query.get() == null)
            throw new Utils.NotFoundException(new JsonError("Error", "Student " + String.valueOf(index) + " not found"));
        return query.get().getGrades();
    }

    public static Grade getStudentByIndexGradesById(int index, int id){
        Query<Student> query = datastore.find(Student.class).field("index").equal(index);
        if (query.get() == null)
            throw new Utils.NotFoundException(new JsonError("Error", "Student " + String.valueOf(index) + " not found"));
        List<Grade> grades = query.get().getGrades();
        for(Grade grade : grades){
            if (grade.getId() == id)
                return grade;
        }
        return null;
    }

    public static void addStudent(Student student){
        datastore.save(student);
    }

    public static void updateStudent(Student student){
        Query<Student> query = datastore.find(Student.class).field("index").equal(student.getIndex());
        UpdateOperations<Student> updateOperations = datastore.createUpdateOperations(Student.class)
                .set("firstName", student.getFirstName())
                .set("lastName", student.getLastName())
                .set("birthDate", student.getBirthDate())
                .set("grades", student.getGrades());
        datastore.findAndModify(query, updateOperations);
    }

    public static boolean deleteStudent(int index){
        Student student = getStudentByIndex(index);
        if (student != null) {
            datastore.delete(student);
            return true;
        }
        else{
            return false;
        }
    }

    public static List<Student> getStudentsByFilters(String firstName, String lastName, Date birthDate, String dateRelation) {
        final Query<Student> query = datastore.createQuery(Student.class);
        if(firstName != null)
            query.field("firstName").containsIgnoreCase(firstName);
        if(lastName != null)
            query.field("lastName").containsIgnoreCase(lastName);
        if(birthDate != null && dateRelation != null) {
            if(dateRelation.equals("equal"))
                query.field("birthDate").equal(birthDate);
            else if(dateRelation.equals("grater"))
                query.field("birthDate").greaterThan(birthDate);
            else if(dateRelation.equals("lower"))
                query.field("birthDate").lessThan(birthDate);
        }
        List<Student> filteredStudents = query.asList();
        return filteredStudents;
    }
}
