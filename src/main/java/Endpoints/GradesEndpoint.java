package Endpoints;

import DataModel.Grade;
import DataService.DataService;
import Utils.JsonError;
import jersey.repackaged.com.google.common.collect.Lists;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("/students/{index}/grades")
public class GradesEndpoint {
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getStudentGrades(@PathParam("index") int index,
                                     @QueryParam("course") String course,
                                     @QueryParam("value") String value,
                                     @QueryParam("rel") String rel,
                                     @QueryParam("date") Date date){
        List<Grade> grades = DataService.getStudentByIndexGrades(index);

        if (grades == null || grades.size() == 0) {
            grades = new ArrayList<>();
        }


        if (course != null && !course.isEmpty()){
            grades = grades.stream().filter(grade -> grade.getCourse().getName().toLowerCase().contains(course.toLowerCase())).collect(Collectors.toList());
        }

        if (rel == null || rel.isEmpty()){
            rel = "equal";
        }

        if ( (value != null && !value.isEmpty()) && (rel != null && !rel.isEmpty()) ) {
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

        if (date != null){
            grades = grades.stream().filter(grade -> grade.getDate().equals(date)).collect(Collectors.toList());
        }

       // if (grades == null || grades.size() == 0)
          //  return Response.status(Response.Status.NOT_FOUND).entity("No matching grades").build();

        GenericEntity<List<Grade>> entity = new GenericEntity<List<Grade>>(Lists.newArrayList(grades)) {};

        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Grade getStudentGrade(@PathParam("index") int index, @PathParam("id") int id){
        Grade grade = DataService.getStudentByIndexGradeById(index, id);
        if(grade != null)
            return grade;
        else
            throw new Utils.NotFoundException(new JsonError("Error", "Grade " + String.valueOf(id) + " of Student " + String.valueOf(index) + " not found."));
    }

    @POST
    public Response postGrade(@PathParam("index") int index, Grade grade){
        return DataService.postGrade(index, grade);
    }

    @PUT
    @Path("/{id}")
    public Response putGrade(@PathParam("index") int index, @PathParam("id") int id, Grade grade){
        return DataService.putGrade(index, id, grade);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteGrade(@PathParam("index") int index, @PathParam("id") int id){
        return DataService.deleteGrade(index, id);
    }
}
