package org.example.handlers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.dto.BookingReq;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

import static org.example.HotelBookingManagerAPI.hotelBookingManager;
import static org.example.HotelBookingManagerAPI.jsonMapper;

public class MakeBooking implements Handler {

    private static final Logger LOGGER = Logger.getLogger(MakeBooking.class.getName());

    @Override
    public void handle(@NotNull Context context) {
        try {
            BookingReq bookingReq = jsonMapper.readValue(context.body(), BookingReq.class);
            context.status(200).json(hotelBookingManager.storeBooking(bookingReq));
        } catch (JsonProcessingException e) {
            context.status(400).json("Failed to process JSON Booking Request");
            LOGGER.info("Failed to process JSON Booking Request");
        } catch (Exception e) {
            context.status(500).json("Internal server error");
            LOGGER.info("Internal server error");
        }
    }
}