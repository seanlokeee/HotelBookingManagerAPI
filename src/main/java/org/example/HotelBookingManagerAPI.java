package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import org.example.handlers.AvailableRooms;
import org.example.handlers.Bookings;
import org.example.handlers.MakeBooking;
import org.example.service.HotelBookingManager;

import java.util.logging.Logger;

public class HotelBookingManagerAPI {
    public static final ObjectMapper jsonMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
    public static HotelBookingManager hotelBookingManager = null;
    private static final Logger LOGGER = Logger.getLogger(HotelBookingManagerAPI.class.getName());

    public static void main(String[] args) {
        hotelBookingManager = new HotelBookingManager(6);
        Javalin server = Javalin.create(/*config*/)
                .get("/", ctx -> ctx.html(homeUI(hotelBookingManager)))
                .post("/make-booking", new MakeBooking())
                .get("/bookings/{username}", new Bookings())
                .post("/available-room", new AvailableRooms())
                .start(6060);
        LOGGER.info(String.format("Room 1 -> %s created", hotelBookingManager.getMaxNumberOfRooms()));
    }

    private static String homeUI(HotelBookingManager hotelBookingManager) {
        final String welcome = "<h2>Welcome to Capgemini's Hotel</h2>";
        String allRooms = "<h3>Here are all the hotel rooms</h3><ul>";
        for (int roomNumber : hotelBookingManager.getRooms().keySet()) {
            allRooms += String.format("<li>Room %s</li>", roomNumber);
        }
        return welcome + allRooms + "</ul>";
    }
}