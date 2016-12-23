package fixtures.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.event.EventsResource;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.ReservationsResource;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.*;

public class RestReservationFixture extends ReservationBaseRestFixture {
    private static final int RESERVATION_NUMBER = 37353;
    private static final String RESERVATION_DATE = "2016-01-31";
    private static final String RESERVATION_CONFIRMATION = "A3833";
    private static final String PAYMENT_LOCATION = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
    private static final String FIRST_NAME = "Alice";
    private static final String LAST_NAME = "Trembley";
    private static final int AGE = 21;
    private static final String PASSPORT_NUMBER = "p1234";
    private static final String SEAT_CLASS = "economy";
    public static final int FIRST_PASSENGER = 0;
    public static final String PASSENGER_HASH = "passenger_hash";
    public static final String PASSENGERS = "passengers";

    public UUID givenAPassengerWithAReservation(String flightNumber, String flightDate) {
        createReservation(flightNumber, flightDate);
        return getReservationPassengerHash(RESERVATION_NUMBER);
    }

    private void createReservation(String flightNumber, String flightDate) {
        UriBuilder uriBuilder = UriBuilder.fromUri(EventsResource.PATH);
        String createReservationPath = uriBuilder.path(EventsResource.RESERVATION_CREATED).toString();

        Map<String, Object> body = getReservationJsonAsMap(flightNumber, flightDate);
        givenRequest().body(body)
                .when().post(createReservationPath);
    }

    private Map<String, Object> getReservationJsonAsMap(String flightNumber, String flightDate) {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("reservation_number", Integer.toString(RESERVATION_NUMBER));
        jsonAsMap.put("reservation_date", RESERVATION_DATE);
        jsonAsMap.put("reservation_confirmation", RESERVATION_CONFIRMATION);
        jsonAsMap.put("flight_number", flightNumber);
        jsonAsMap.put("flight_date", flightDate);
        jsonAsMap.put("payment_location", PAYMENT_LOCATION);
        jsonAsMap.put("passengers", getPassengerJsonAsMap());
        return jsonAsMap;
    }

    private List<Map> getPassengerJsonAsMap() {
        Map<String, Object> passengerAsMap = new HashMap<>();
        passengerAsMap.put("first_name", FIRST_NAME);
        passengerAsMap.put("last_name", LAST_NAME);
        passengerAsMap.put("age", AGE);
        passengerAsMap.put("passport_number", PASSPORT_NUMBER);
        passengerAsMap.put("seat_class", SEAT_CLASS);

        List<Map> passengersList = new ArrayList<>();
        passengersList.add(passengerAsMap);
        return passengersList;
    }

    private UUID getReservationPassengerHash(int reservationNumber) {
        UriBuilder uriBuilder = UriBuilder.fromPath(ReservationsResource.PATH);
        String integerNumberString = Integer.toString(reservationNumber);
        URI uri = uriBuilder.path(integerNumberString).build();

        List<Map> passengers = givenRequest().when().get(uri)
                .thenReturn().path(PASSENGERS);
        String passengerHashString = passengers.get(FIRST_PASSENGER).get(PASSENGER_HASH).toString();
        return UUID.fromString(passengerHashString);
    }
}