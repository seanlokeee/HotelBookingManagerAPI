package org.example.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.dto.AvailableDateReq;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

import static org.example.HotelBookingManagerAPI.hotelBookingManager;
import static org.example.HotelBookingManagerAPI.jsonMapper;

public class AvailableRooms implements Handler {

    private static final Logger LOGGER = Logger.getLogger(MakeBooking.class.getName());

    @Override
    public void handle(@NotNull Context context) {
        try {
            AvailableDateReq availableDateReq = jsonMapper.readValue(context.body(), AvailableDateReq.class);
            context.status(200).json(hotelBookingManager.getAvailableRoomsOn(availableDateReq.getAvailableDate()));
        } catch (JsonProcessingException e) {
            context.status(400).json("Failed to process JSON Available Rooms Request");
            LOGGER.info("Failed to process JSON Available Rooms Request");
        } catch (Exception e) {
            context.status(500).json("Internal server error");
            LOGGER.info("Internal server error");
        }
    }
}