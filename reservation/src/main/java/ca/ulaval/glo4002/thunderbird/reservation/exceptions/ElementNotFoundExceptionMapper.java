package ca.ulaval.glo4002.thunderbird.reservation.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.eclipse.jetty.http.HttpStatus.Code.NOT_FOUND;

@Provider
public class ElementNotFoundExceptionMapper implements ExceptionMapper<ElementNotFoundException> {
    @Override
    public Response toResponse(ElementNotFoundException exception) {
        return Response.status(NOT_FOUND.getCode()).entity(exception.getMessage()).build();
    }
}