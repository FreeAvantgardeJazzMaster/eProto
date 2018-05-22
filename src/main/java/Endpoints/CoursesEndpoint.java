package Endpoints;

import DataModel.Course;
import DataService.DataService;
import Utils.JsonError;
import Utils.NotFoundException;
import jersey.repackaged.com.google.common.collect.Lists;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/courses")
public class CoursesEndpoint {

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getCourses(@QueryParam("lecturer") String lecturer){

        List<Course> courses = DataService.getCoursesByLecturer(lecturer);

        if (courses == null || courses.size() == 0){
            return Response.status(Response.Status.NOT_FOUND).entity("No matching courses").build();
        }

        GenericEntity<List<Course>> entity = new GenericEntity<List<Course>>(Lists.newArrayList(courses)) {};

        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Course getCourses(@PathParam("id") int id){
        if (DataService.getCourseById(id) != null)
            return DataService.getCourseById(id);
        else
            throw new NotFoundException(new JsonError("Error", "Course " + String.valueOf(id) + " not found"));
    }

    @POST
    public Response postCourse(Course course){
        Course newCourse = DataService.postCourse(course);
        return Response.status(Response.Status.CREATED).header("Location", "http://localhost:8080/courses" + newCourse.getId()).build();
    }

    @PUT
    @Path("/{id}")
    public Response putStudent(@PathParam("id") int id, Course course){
        return DataService.putCourse(id, course);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCourse(@PathParam("id") int id){
        return DataService.deleteCourse(id);
    }
}
