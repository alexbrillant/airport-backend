package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations.exceptions;

import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status;

@Provider
public class NoMoreSeatAvailableExceptionMapper implements ExceptionMapper<NoMoreSeatAvailableException> {
    @Override
    public Response toResponse(NoMoreSeatAvailableException exception) {
        return Response.status(Status.BAD_REQUEST).build();
    }
}