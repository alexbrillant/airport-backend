package ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions;

public class MissingFieldException extends RuntimeException {
    public MissingFieldException(String field)   {
        super("field " + field + " required");
    }
}
