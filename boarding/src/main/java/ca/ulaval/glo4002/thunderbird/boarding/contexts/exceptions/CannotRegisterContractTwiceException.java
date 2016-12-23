package ca.ulaval.glo4002.thunderbird.boarding.contexts.exceptions;

public class CannotRegisterContractTwiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CannotRegisterContractTwiceException(Class<?> contract) {
        super("You've tried to register this contract twice : '" + contract.getCanonicalName() + "'");
    }
}
