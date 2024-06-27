import org.example.dto.BookingReq;
import org.example.service.HotelBookingManager;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class HotelBookingManagerTest {
    private HotelBookingManager bookingManager;

    @Before
    public void setUp() {
        bookingManager = new HotelBookingManager(3);
    }

    @Test
    public void testStoreBooking() {
        LocalDate now = LocalDate.now();
        String message = String.format("Failed to make booking: Booking Date %s has already been booked", now);
        bookingManager.storeBooking(new BookingReq("guest1", 1, now, 2));
        assertEquals(message, bookingManager.storeBooking(new BookingReq("guest2", 1, now, 2)));
    }

    @Test
    public void testFindAvailableRooms() {
        LocalDate now = LocalDate.now();
        LocalDate endDate = now.plusDays(2);
        assertEquals("Room: 1, 2, 3", bookingManager.getAvailableRoomsOn(now));
        bookingManager.storeBooking(new BookingReq("guest1", 1, now, 2));
        assertEquals("Room: 2, 3", bookingManager.getAvailableRoomsOn(endDate));
    }

    @Test
    public void testFindBookingsByGuest() {
        LocalDate now = LocalDate.now();
        String name = "guest1";
        bookingManager.storeBooking(new BookingReq(name, 1, now, 2));
        bookingManager.storeBooking(new BookingReq(name, 2, now, 2));
        assertEquals(2, bookingManager.getBookingsFor(name).size());
    }
}