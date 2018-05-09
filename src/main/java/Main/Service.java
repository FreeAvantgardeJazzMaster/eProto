package Main;

import DataModel.Course;
import DataAccess.DataAccess;
import DataModel.Grade;
import DataModel.Student;
import Utils.JsonError;
import Utils.NotFoundException;

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
            return DataAccess.getStudents();
    }

    @GET
    @Path("/students/{index}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Student getStudentByIndex(@PathParam("index") int index){
        Student student = DataAccess.getStudentByIndex(index);
        if(student != null)
            return student;
        else
            throw new Utils.NotFoundException(new JsonError("Error", "Student " + String.valueOf(index) + " not found"));
    }

    @GET
    @Path("/students/{index}/grades")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Grade> getStudentGrades(@PathParam("index") int index){
        return DataAccess.getStudentByIndexGrades(index);
    }

    @GET
    @Path("/students/{index}/grades/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Grade getStudentGrade(@PathParam("index") int index, @PathParam("id") int id){
        Grade grade = DataAccess.getStudentByIndexGradeById(index, id);
        if(grade != null)
            return grade;
        else
            throw new Utils.NotFoundException(new JsonError("Error", "Grade " + String.valueOf(id) + " of Student " + String.valueOf(index) + " not found."));
    }

    @GET
    @Path("/courses")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Course> getCourses(){
        return DataAccess.getCourses();
    }

    @GET
    @Path("/courses/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Course getCourses(@PathParam("id") int id){
        if (DataAccess.getCourseById(id) != null)
            return DataAccess.getCourseById(id);
        else
            throw new NotFoundException(new JsonError("Error", "Course " + String.valueOf(id) + " not found"));
    }


    @POST
    @Path("/students")
    public Response postStudent (Student student) {
        Student newStudent = DataAccess.postStudent(student);
        return Response.status(Response.Status.CREATED).header("Location", "http://localhost:8080/students/" + newStudent.getIndex()).build();
    }

    @POST
    @Path("/courses")
    public Response postCourse(Course course){
        Course newCourse = DataAccess.postCourse(course);
        return Response.status(Response.Status.CREATED).header("Location", "http://localhost:8080/courses" + newCourse.getId()).build();
    }

    @POST
    @Path("/students/{index}/grades")
    public Response postGrade(@PathParam("index") int index, Grade grade){
        DataAccess.postGrade(index, grade);
        return Response.status(Response.Status.CREATED).header("Location", "http://localhost:8080/students/" + index + "/grades").build();
    }


    @PUT
    @Path("students/{index}")
    public Response putStudent(@PathParam("index") int index, Student student){
        return DataAccess.putStudent(index, student);
    }

    @PUT
    @Path("courses/{id}")
    public Response putStudent(@PathParam("id") int id, Course course){
        return DataAccess.putCourse(id, course);
    }

    @PUT
    @Path("/students/{index}/grades/{id}")
    public Response putGrade(@PathParam("index") int index, @PathParam("id") int id, Grade grade){
        return DataAccess.putGrade(index, id, grade);
    }


    @DELETE
    @Path("/students/{index}")
    public Response deleteStudent(@PathParam("index") int index){
        return DataAccess.deleteStudent(index);
    }

    @DELETE
    @Path("/courses/{id}")
    public Response deleteCourse(@PathParam("id") int id){
        return DataAccess.deleteCourse(id);
    }

    @DELETE
    @Path("/students/{index}/grades/{id}")
    public Response deleteGrade(@PathParam("index") int index, @PathParam("id") int id){
        return DataAccess.deleteGrade(index, id);
    }
}
