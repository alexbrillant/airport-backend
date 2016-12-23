package ca.ulaval.glo4002.thunderbird.boarding.persistence.exceptions;


public class RepositorySavingException extends RuntimeException {
    public RepositorySavingException(String reason)   {
        super(reason);
    }
}
