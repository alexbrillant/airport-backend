package ca.ulaval.glo4002.thunderbird.boarding.contexts.exceptions;

public class UnableResolveServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnableResolveServiceException(Class<?> contract) {
        super("Unable to resolve a service for '" + contract.getCanonicalName() + "'. Did you forget to register it?");
    }
}
