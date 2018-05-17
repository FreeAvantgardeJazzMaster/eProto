package Main;

import DataModel.Course;
import DataAccess.DataAccess;
import DataModel.Grade;
import DataModel.Student;
import Utils.JsonError;
import Utils.NotFoundException;
import jersey.repackaged.com.google.common.collect.Lists;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Path("/")
public class Service {

    @GET
    @Path("/students")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getStudents(@QueryParam("firstName") String firstName,
                                     @QueryParam("lastName") String lastName,
                                     @QueryParam("date") Date date,
                                     @QueryParam("rel") String rel){
        List<Student> students = DataAccess.getStudents();

        if (students == null || students.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).entity("No students").build();

        if (firstName != null) {
            students = students.stream().filter(st -> st.getFirstName().equals(firstName)).collect(Collectors.toList());
        }

        if (lastName != null) {
            students = students.stream().filter(st -> st.getLastName().equals(lastName)).collect(Collectors.toList());
        }

        if (date != null && rel != null) {
            switch (rel.toLowerCase()) {
                case "equal":
                    students = students.stream().filter(st -> st.getBirthDate().equals(date)).collect(Collectors.toList());
                    break;
                case "after":
                    students = students.stream().filter(st -> st.getBirthDate().after(date)).collect(Collectors.toList());
                    break;
                case "before":
                    students = students.stream().filter(st -> st.getBirthDate().before(date)).collect(Collectors.toList());
                    break;
            }
        }

        GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(Lists.newArrayList(students)) {};

        if (students == null || students.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).entity("No matching results").build();

        return Response.status(Response.Status.OK).entity(entity).build();
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
    public Response getStudentGrades(@PathParam("index") int index,
                                     @QueryParam("course") String course,
                                     @QueryParam("value") String value,
                                     @QueryParam("rel") String rel){
        List<Grade> grades = DataAccess.getStudentByIndexGrades(index);

        if (grades == null || grades.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).entity("No grades").build();

        if (course != null){
            grades = grades.stream().filter(grade -> grade.getCourse().getName().equals(course)).collect(Collectors.toList());
        }

        if (value != null && rel != null) {
            switch (rel.toLowerCase()) {
                case "equal":
                    grades = grades.stream().filter(grade -> grade.getValue() == Float.valueOf(value).floatValue()).collect(Collectors.toList());
                    break;
                case "grater":
                    grades = grades.stream().filter(grade -> grade.getValue() > Float.valueOf(value).floatValue()).collect(Collectors.toList());
                    break;
                case "lower":
                    grades = grades.stream().filter(grade -> grade.getValue() < Float.valueOf(value).floatValue()).collect(Collectors.toList());
                    break;
            }
        }

        if (grades == null || grades.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).entity("No matching grades").build();

        GenericEntity<List<Grade>> entity = new GenericEntity<List<Grade>>(Lists.newArrayList(grades)) {};

        return Response.status(Response.Status.OK).entity(entity).build();
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
    public Response getCourses(@QueryParam("lecturer") String lecturer){
        List<Course> courses = DataAccess.getCourses();

        if (courses == null)
            return Response.status(Response.Status.NOT_FOUND).entity("No courses").build();
        if (lecturer != null){
            courses = courses.stream().filter(course -> course.getLecturer().equals(lecturer)).collect(Collectors.toList());
        }
        if (courses == null || courses.size() == 0){
            return Response.status(Response.Status.NOT_FOUND).entity("No matching courses").build();
        }

        GenericEntity<List<Course>> entity = new GenericEntity<List<Course>>(Lists.newArrayList(courses)) {};

        return Response.status(Response.Status.OK).entity(entity).build();
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
        return DataAccess.postStudent(student);
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
        return DataAccess.postGrade(index, grade);
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
