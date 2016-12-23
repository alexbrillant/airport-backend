package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import java.util.ArrayList;
import java.util.List;

public class BaggagesListDTO {
    public double totalPrice;
    public List<BaggageDTO> baggages = new ArrayList<>();
}