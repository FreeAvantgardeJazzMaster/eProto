package Endpoints;

import DataModel.Course;
import DataService.DataService;
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


@Path("/students")
public class StudentsEndpoint {

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getStudents(@QueryParam("firstName") String firstName,
                                     @QueryParam("lastName") String lastName,
                                     @QueryParam("date") Date date,
                                     @QueryParam("rel") String rel){
        List<Student> students = DataService.getStudentsByFilters(firstName, lastName, date, rel);

        GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(Lists.newArrayList(students)) {};

        if (students == null || students.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).entity("No matching results").build();

        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @GET
    @Path("/{index}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Student getStudentByIndex(@PathParam("index") int index){
        Student student = DataService.getStudentByIndex(index);
        if(student != null)
            return student;
        else
            throw new Utils.NotFoundException(new JsonError("Error", "Student " + String.valueOf(index) + " not found"));
    }

    @POST
    public Response postStudent (Student student) {
        return DataService.postStudent(student);
    }

    @PUT
    @Path("/{index}")
    public Response putStudent(@PathParam("index") int index, Student student){
        return DataService.putStudent(index, student);
    }

    @DELETE
    @Path("/{index}")
    public Response deleteStudent(@PathParam("index") int index){
        return DataService.deleteStudent(index);
    }
}
