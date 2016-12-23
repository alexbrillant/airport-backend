package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

public class RegisterBaggageDTO {
    @Min(0)
    public int linearDimension;
    @NotBlank
    public String linearDimensionUnit;
    @Min(0)
    public int weight;
    @NotBlank
    public String weightUnit;
    @NotBlank
    public String type;

    @JsonCreator
    public RegisterBaggageDTO(String linearDimensionUnit, int linearDimension, String weightUnit,
                              int weight, String type) {
        this.linearDimensionUnit = linearDimensionUnit;
        this.linearDimension = linearDimension;
        this.weightUnit = weightUnit;
        this.weight = weight;
        this.type = type;
    }
}