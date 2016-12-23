package ca.ulaval.glo4002.thunderbird.boarding.rest.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.application.passenger.PassengerService;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.Assert.*;
import static ca.ulaval.glo4002.thunderbird.reservation.contexts.DevContext.EXISTENT_RESERVATION_NUMBER;

public class PassengerServiceRestTest {

    @Test
    public void givenAValidPassengerHashOnReservation_whenFetchingPassenger_shouldReturnPassenger(){
        UUID validUUID = getValidPassengerUUID();
        PassengerService passengerService = new PassengerService();

        Passenger passenger = passengerService.fetchPassenger(validUUID);

        UUID expectedUUID = validUUID;
        UUID actualUUID = passenger.getHash();
        assertEquals(expectedUUID,actualUUID);
    }

    @Test(expected = PassengerNotFoundException.class)
    public void givenAnInvalidPassengerHashOnReservation_whenFetchingPassenger_shouldThrowPassengerNotFound(){
        UUID invalidUUID = UUID.randomUUID();
        PassengerService passengerService = new PassengerService();

        passengerService.fetchPassenger(invalidUUID);
    }

    private UUID getValidPassengerUUID(){
        ArrayList<HashMap<String,String>> arrayList = RestTestConfig.givenBaseRequestReservation()
                .when().get("/reservations/{reservation_number}", EXISTENT_RESERVATION_NUMBER)
                .then().extract().path("passengers");
        String uuidString = arrayList.get(0).get("passenger_hash");
        return UUID.fromString(uuidString);
    }
}
