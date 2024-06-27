package org.example.handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

import static org.example.HotelBookingManagerAPI.hotelBookingManager;

public class Bookings implements Handler {
    private static final Logger LOGGER = Logger.getLogger(MakeBooking.class.getName());

    @Override
    public void handle(@NotNull Context context) {
        try {
            context.status(200).json(hotelBookingManager.getBookingsFor(context.pathParam("username")));
        } catch (Exception e) {
            context.status(500).json("Internal server error");
            LOGGER.info("Internal server error");
        }
    }
}