package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.exceptions;

import ca.ulaval.glo4002.thunderbird.boarding.application.baggage.exceptions.PassengerNotCheckedInException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.eclipse.jetty.http.HttpStatus.Code.BAD_REQUEST;

@Provider
public class PassengerNotCheckedInExceptionMapper implements ExceptionMapper<PassengerNotCheckedInException> {
    @Override
    public Response toResponse(PassengerNotCheckedInException e) {
        return Response.status(BAD_REQUEST.getCode()).build();
    }
}
