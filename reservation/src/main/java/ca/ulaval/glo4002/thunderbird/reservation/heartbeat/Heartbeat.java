package ca.ulaval.glo4002.thunderbird.reservation.heartbeat;

public class Heartbeat {
    private final String token;
    private final long date;

    Heartbeat(String token) {
        this.token = token;
        this.date = System.currentTimeMillis();
    }
}