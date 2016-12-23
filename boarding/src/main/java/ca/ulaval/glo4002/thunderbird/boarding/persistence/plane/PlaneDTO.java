package ca.ulaval.glo4002.thunderbird.boarding.persistence.plane;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlaneDTO {
    public String model;
    public String manufacturer;
    @JsonProperty("number_of_seats")
    public int numberOfSeats;
    @JsonProperty("cargo_weight")
    public int cargoWeight;

    public PlaneDTO() {

    }
}
