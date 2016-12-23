package ca.ulaval.glo4002.thunderbird.reservation.checkin.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CheckinNotOnTimeExceptionMapper implements ExceptionMapper<CheckinNotOnTimeException> {
    @Override
    public Response toResponse(CheckinNotOnTimeException exception) {
        return Response.status(Status.BAD_REQUEST).entity("checkin not on time").build();
    }
}