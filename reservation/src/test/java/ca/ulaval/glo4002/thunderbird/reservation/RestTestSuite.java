package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.checkin.CheckinResourceRestTest;
import ca.ulaval.glo4002.thunderbird.reservation.contexts.DevContext;
import ca.ulaval.glo4002.thunderbird.reservation.event.EventsResourceRestTest;
import ca.ulaval.glo4002.thunderbird.reservation.heartbeat.HeartbeatResourceRestTest;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.PassengersResourceRestTest;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.ReservationsResourceRestTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.TEST_SERVER_PORT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CheckinResourceRestTest.class,
        EventsResourceRestTest.class,
        HeartbeatResourceRestTest.class,
        ReservationsResourceRestTest.class,
        PassengersResourceRestTest.class
})
public class RestTestSuite {

    private static ReservationServer reservationServer;

    @BeforeClass
    public static void setUpClass() {
        reservationServer = new ReservationServer();
        reservationServer.start(TEST_SERVER_PORT, new DevContext());
    }

    @AfterClass
    public static void tearDownClass() {
        reservationServer.stop();
    }
}