package ca.ulaval.glo4002.thunderbird.app;

import ca.ulaval.glo4002.thunderbird.boarding.BoardingServer;
import ca.ulaval.glo4002.thunderbird.reservation.ReservationServer;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread boardingThread = new Thread(new BoardingServer());
        Thread reservationThread = new Thread(new ReservationServer());

        boardingThread.start();
        reservationThread.start();

        boardingThread.join();
        reservationThread.join();
    }
}