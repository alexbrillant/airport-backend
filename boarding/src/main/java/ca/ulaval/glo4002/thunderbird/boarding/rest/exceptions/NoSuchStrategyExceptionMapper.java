package ca.ulaval.glo4002.thunderbird.boarding.rest.exceptions;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.eclipse.jetty.http.HttpStatus.Code.BAD_REQUEST;

@Provider
public class NoSuchStrategyExceptionMapper implements ExceptionMapper<NoSuchStrategyException> {
    @Override
    public Response toResponse(NoSuchStrategyException e) {
        return Response.status(BAD_REQUEST.getCode()).build();
    }
}
