package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.exceptions;

import ca.ulaval.glo4002.thunderbird.boarding.application.baggage.exceptions.NoMoreSpaceInFlightException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.RegisterBaggageResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.eclipse.jetty.http.HttpStatus.Code.OK;

@Provider
public class NoMoreSpaceInFlightExceptionMapper implements ExceptionMapper<NoMoreSpaceInFlightException> {
    @Override
    public Response toResponse(NoMoreSpaceInFlightException noMoreSpaceInFlightException) {
        RegisterBaggageResponse registerBaggageResponse = RegisterBaggageResponse.refused("Flight has no more space available for baggage");
        return Response.status(OK.getCode()).entity(registerBaggageResponse).build();
    }
}
