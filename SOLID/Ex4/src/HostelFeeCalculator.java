import java.util.*;

public class HostelFeeCalculator {


    private final FakeBookingRepo repo;

    private static final Map<Integer , Double> ROOM_PRICE = new HashMap<>();
    private static final Map<Integer , Double> ADDON_PRICE = new HashMap<>() ;

    static {
        ROOM_PRICE.put(LegacyRoomTypes.SINGLE, 14000.0) ;
        ROOM_PRICE.put(LegacyRoomTypes.DOUBLE, 15000.0) ;
        ROOM_PRICE.put(LegacyRoomTypes.TRIPLE, 12000.0) ;


        ADDON_PRICE.put(AddOn.MESS.ordinal(),1000.0) ;
        ADDON_PRICE.put(AddOn.GYM.ordinal(), 500.0) ;
        ADDON_PRICE.put(AddOn.LAUNDRY.ordinal(), 300.0) ;
    }


    public HostelFeeCalculator(FakeBookingRepo repo) {
        this.repo = repo;
    }

    // OCP violation: switch + add-on branching + printing + persistence.
    public void process(BookingRequest req) {
        Money monthly = calculateMonthly(req);
        Money deposit = new Money(5000.00);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000)); // deterministic-ish
        repo.save(bookingId, req, monthly, deposit);
    }

    private Money calculateMonthly(BookingRequest req) {
       double base = ROOM_PRICE.getOrDefault(req.roomType, 16000.0) ;
        double extra = 0.0;
        for (AddOn a : req.addOns) {
            extra += ADDON_PRICE.getOrDefault(a, 0.0);
        }

        return new Money(base + extra);
    }
}
