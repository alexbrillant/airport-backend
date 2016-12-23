package ca.ulaval.glo4002.thunderbird.boarding.domain.plane;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Comparator;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int rowNumber;
    private String seatName;
    private int legRoom;
    private boolean hasWindow;
    private boolean hasClearView;
    private double price;
    private SeatClass seatClass;
    private boolean isExitRow;

    public Seat(int rowNumber, String seatName, int legRoom, boolean hasWindow, boolean hasClearView, double price,
                SeatClass seatClass, boolean isExitRow) {
        this.rowNumber = rowNumber;
        this.seatName = seatName;
        this.legRoom = legRoom;
        this.hasWindow = hasWindow;
        this.hasClearView = hasClearView;
        this.price = price;
        this.seatClass = seatClass;
        this.isExitRow = isExitRow;
    }

    protected Seat() {
        // for hibernate
    }

    public int getId() {
        return id;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public boolean isExitRow() {
        return isExitRow;
    }

    @Override
    public String toString() {
        return rowNumber + "-" + seatName;
    }

    public enum SeatClass {
        ANY,
        ECONOMY,
        BUSINESS,
        BIG_FRONT,
        PREMIUM_ECONOMY
    }

    public static Comparator<Seat> LANDSCAPE_COMPARATOR = Comparator
            .comparing((Seat s) -> s.hasWindow)
            .thenComparing((Seat s) -> s.hasClearView)
            .thenComparing((Seat s) -> s.price, Comparator.reverseOrder());

    public static Comparator<Seat> LEG_ROOM_COMPARATOR = Comparator
            .comparingInt((Seat s) -> s.legRoom)
            .thenComparing((Seat s) -> s.price, Comparator.reverseOrder());

    public static Comparator<Seat> PRICE_COMPARATOR = Comparator
            .comparing((Seat s) -> s.price, Comparator.reverseOrder());
}