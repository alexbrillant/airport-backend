package ca.ulaval.glo4002.thunderbird.boarding.rest;

import ca.ulaval.glo4002.thunderbird.boarding.BoardingServer;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerServiceRestTest;
import ca.ulaval.glo4002.thunderbird.boarding.contexts.DevContext;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.BaggageResourceRestTest;
import ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations.SeatAssignationsResourceRestTest;
import ca.ulaval.glo4002.thunderbird.reservation.ReservationServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.TEST_SERVER_PORT_BOARDING;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.TEST_SERVER_PORT_RESERVATION;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        SeatAssignationsResourceRestTest.class,
        BaggageResourceRestTest.class,
        PassengerServiceRestTest.class
})
public class RestTestSuite {
    private static ReservationServer reservationServer;
    private static BoardingServer boardingServer;

    @BeforeClass
    public static void setUpClass() {
        reservationServer = new ReservationServer();
        ca.ulaval.glo4002.thunderbird.reservation.contexts.DevContext devContextReservation =
                new ca.ulaval.glo4002.thunderbird.reservation.contexts.DevContext();
        reservationServer.start(TEST_SERVER_PORT_RESERVATION, devContextReservation);
        boardingServer = new BoardingServer();
        DevContext devContextBoarding = new DevContext();
        boardingServer.start(TEST_SERVER_PORT_BOARDING, devContextBoarding);
    }

    @AfterClass
    public static void tearDownClass() {
        boardingServer.stop();
        reservationServer.stop();
    }
}
