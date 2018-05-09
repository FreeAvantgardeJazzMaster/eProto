package Utils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AppExtentionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception ex) {
        return Response.status(500).entity(new Exception(ex)).type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
