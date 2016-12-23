package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.exceptions;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.RegisterBaggageResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.eclipse.jetty.http.HttpStatus.Code.OK;

@Provider
public class BaggageDimensionInvalidExceptionMapper implements ExceptionMapper<BaggageDimensionInvalidException> {
    @Override
    public Response toResponse(BaggageDimensionInvalidException e) {
        RegisterBaggageResponse registerBaggageResponse = RegisterBaggageResponse.refused("dimension invalid");

        return Response.status(OK.getCode()).entity(registerBaggageResponse).build();
    }
}
