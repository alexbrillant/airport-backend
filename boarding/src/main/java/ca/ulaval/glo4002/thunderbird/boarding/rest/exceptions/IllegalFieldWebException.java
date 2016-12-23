package ca.ulaval.glo4002.thunderbird.boarding.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static org.eclipse.jetty.http.HttpStatus.Code.BAD_REQUEST;

public class IllegalFieldWebException extends WebApplicationException {
    @Override
    public Response getResponse() {
        return Response.status(BAD_REQUEST.getCode()).build();
    }
}
