package DataAccess;

import DataModel.Course;
import Main.DatabaseInit;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

public class CourseAdapter {
    private static Datastore datastore = DatabaseInit.getDatastore();

    public static List<Course> getCourses(){
        Query<Course> query = datastore.find(Course.class);
        return query.asList();
    }

    public static Course getCourseById(int id){
        Query<Course> query = datastore.find(Course.class).field("id").equal(id);
        return query.get();
    }

    public static Course getCourseByName(String name){
        Query<Course> query = datastore.find(Course.class).field("name").equal(name);
        return query.get();
    }

    public static void addCourse(Course course){
        datastore.save(course);
    }

    public static void updateCourse(Course course){
        Query<Course> query = datastore.find(Course.class).field("id").equal(course.getId());
        UpdateOperations<Course> updateOperations = datastore.createUpdateOperations(Course.class)
                .set("name", course.getName())
                .set("lecturer", course.getLecturer());
        datastore.findAndModify(query, updateOperations);
    }

    public static boolean deleteCourse(int id){
        Course course = getCourseById(id);
        if (course != null) {
            datastore.delete(course);
            return true;
        }
        else{
            return false;
        }
    }

    public static List<Course> getCoursesByLecturer(String lecturer) {
        final Query<Course> query = datastore.createQuery(Course.class);
        if (lecturer != null)
            query.field("lecturer").containsIgnoreCase(lecturer);
        List<Course> courses = query.asList();
        return courses;
    }

}
