package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;

import java.util.Set;

public class BaggagesListAssembler {
    private BaggageAssembler baggageAssembler;

    public BaggagesListAssembler() {
        this.baggageAssembler = new BaggageAssembler();
    }

    public BaggagesListDTO toDTO(double totalPrice, Set<Baggage> baggages) {
        BaggagesListDTO baggagesListDTO = new BaggagesListDTO();

        for (Baggage baggage : baggages) {
            BaggageDTO baggageDTO = baggageAssembler.toDTO(baggage);
            baggagesListDTO.baggages.add(baggageDTO);
        }
        baggagesListDTO.totalPrice = totalPrice;

        return baggagesListDTO;
    }
}