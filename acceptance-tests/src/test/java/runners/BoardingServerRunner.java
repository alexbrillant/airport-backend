package runners;

import ca.ulaval.glo4002.thunderbird.boarding.BoardingServer;
import contexts.boarding.BoardingContext;

public class BoardingServerRunner {
    public static final int JETTY_TEST_PORT = 49152;

    private static boolean isStarted = false;
    private BoardingServer server;

    public void startJettyServer() {
        if (!isStarted) {
            isStarted = true;
            Runtime.getRuntime().addShutdownHook(new JettyServerShutdown());
            startMedServerInJetty();
        }
    }

    private void startMedServerInJetty() {
        server = new BoardingServer();
        server.start(JETTY_TEST_PORT, new BoardingContext());
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