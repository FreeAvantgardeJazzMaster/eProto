package Main;

import DataModel.Course;
import DataModel.DataBase;
import DataModel.Grade;
import DataModel.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/")
public class Service {

    @GET
    @Path("/students")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Student> getStudents(){
            return DataBase.getStudents();
    }

    @GET
    @Path("/students/{index}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Student getStudentByIndex(@PathParam("index") int index){
        if(DataBase.getStudentByIndex(index) != null)
            return DataBase.getStudentByIndex(index);
        else
            throw new NotFoundException(new JsonError("Error", "Student " + String.valueOf(index) + " not found"));
    }

    @GET
    @Path("/students/{index}/grades")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Grade> getStudentGrades(@PathParam("index") int index){
        return DataBase.getStudentByIndexGrades(index);
    }

    @GET
    @Path("/students/{index}/grades/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Grade getStudentGrade(@PathParam("index") int index, @PathParam("id") int id){
        if(DataBase.getStudentByIndexGradeById(index, id) != null)
            return DataBase.getStudentByIndexGradeById(index, id);
        else
            throw new NotFoundException(new JsonError("Error", "Grade " + String.valueOf(id) + " of Student " + String.valueOf(index) + " not found."));
    }

    @GET
    @Path("/courses")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Course> getCourses(){
        return DataBase.getCourses();
    }

    @GET
    @Path("/courses/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Course getCourses(@PathParam("id") int id){
        if (DataBase.getCourseById(id) != null)
            return DataBase.getCourseById(id);
        else
            throw new NotFoundException(new JsonError("Error", "Course " + String.valueOf(id) + " not found"));
    }


    @POST
    @Path("/students")
    public Response postStudent (Student student) {
        DataBase.postStudent(student);
        return Response.status(Response.Status.CREATED).header("Location", "http://localhost:8080/students").build();
    }

    @POST
    @Path("/courses")
    public Response postCourse(Course course){
        DataBase.postCourse(course);
        return Response.status(Response.Status.CREATED).header("Location", "http://localhost:8080/courses").build();
    }

    @POST
    @Path("/students/{index}/grades")
    public Response postGrade(@PathParam("index") int index, Grade grade){
        DataBase.postGrade(index, grade);
        return Response.status(Response.Status.CREATED).header("Location", "http://localhost:8080/students/" + index + "/grades").build();
    }


    @PUT
    @Path("students/{index}")
    public Response putStudent(@PathParam("index") int index, Student student){
        return DataBase.putStudent(index, student);
    }

    @PUT
    @Path("courses/{id}")
    public Response putStudent(@PathParam("id") int id, Course course){
        return DataBase.putCourse(id, course);
    }

    @PUT
    @Path("/students/{index}/grades/{id}")
    public Response putGrade(@PathParam("index") int index, @PathParam("id") int id, Grade grade){
        return DataBase.putGrade(index, id, grade);
    }


    @DELETE
    @Path("/students/{index}")
    public Response deleteStudent(@PathParam("index") int index){
        return DataBase.deleteStudent(index);
    }

    @DELETE
    @Path("/courses/{id}")
    public Response deleteCourse(@PathParam("id") int id){
        return DataBase.deleteCourse(id);
    }

    @DELETE
    @Path("/students/{index}/grades/{id}")
    public Response deleteGrade(@PathParam("index") int index, @PathParam("id") int id){
        return DataBase.deleteGrade(index, id);
    }
}
