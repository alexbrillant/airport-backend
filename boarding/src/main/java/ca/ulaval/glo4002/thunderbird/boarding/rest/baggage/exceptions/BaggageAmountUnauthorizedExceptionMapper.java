package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.exceptions;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.RegisterBaggageResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.eclipse.jetty.http.HttpStatus.Code.OK;

@Provider
public class BaggageAmountUnauthorizedExceptionMapper implements ExceptionMapper<BaggageAmountUnauthorizedException> {
    @Override
    public Response toResponse(BaggageAmountUnauthorizedException e) {
        RegisterBaggageResponse registerBaggageResponse = RegisterBaggageResponse.refused("baggage amount authorized is reached");
        return Response.status(OK.getCode()).entity(registerBaggageResponse).build();
    }
}