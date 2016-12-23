package ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions;

import java.io.Serializable;

public class ElementNotFoundException extends RuntimeException implements Serializable {
    public ElementNotFoundException(String element) {
        super(element + " not found");
    }
}
