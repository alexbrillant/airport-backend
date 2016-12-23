package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

public class BaggageAssembler {

    public BaggageDTO toDTO(Baggage baggage) {
        BaggageDTO baggageDTO = new BaggageDTO();

        Mass weight = baggage.getWeight();
        Length length = baggage.getDimension();

        baggageDTO.weight = (int) weight.toGrams();
        baggageDTO.linearDimension = (int) length.toMillimeters();
        baggageDTO.price = baggage.getPrice();

        return baggageDTO;
    }
}
