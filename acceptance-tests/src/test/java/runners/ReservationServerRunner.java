package runners;

import ca.ulaval.glo4002.thunderbird.reservation.ReservationServer;
import contexts.reservation.ReservationContext;

public class ReservationServerRunner {
    public static final int JETTY_TEST_PORT = 65535;

    private static boolean isStarted = false;
    private ReservationServer server;

    public void startJettyServer() {
        if (!isStarted) {
            isStarted = true;
            Runtime.getRuntime().addShutdownHook(new JettyServerShutdown());
            startMedServerInJetty();
        }
    }

    private void startMedServerInJetty() {
        // For api calls from boarding to reservation
        System.setProperty("reservation.port", Integer.toString(JETTY_TEST_PORT));

        server = new ReservationServer();
        server.start(JETTY_TEST_PORT, new ReservationContext());
    }

    private class JettyServerShutdown extends Thread {
        public void run() {
            try {
                server.stop();
            } catch (Exception e) {
                // Nothing do to anyways
            }
        }
    }
}