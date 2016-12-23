package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class SeatAssignationDTO {
    @NotNull
    public UUID passengerHash;
    @NotBlank
    public String mode;
}