package org.example.service;

import org.example.model.Room;
import org.example.dto.BookingReq;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class HotelBookingManager {
    private int maxNumberOfRooms;
    private Map<Integer, Room> rooms;
    private static final Logger LOGGER = Logger.getLogger(HotelBookingManager.class.getName());

    public HotelBookingManager(int maxNumberOfRooms) {
        this.maxNumberOfRooms = maxNumberOfRooms;
        this.rooms = new ConcurrentHashMap<>();
        for (int i = 1; i <= maxNumberOfRooms; i++) {
            rooms.put(i, new Room());
        }
    }

    public String storeBooking(BookingReq bookingReq) {
        String response = "";
        try {
            if (bookingReq.getNumOfDays() <= 0) {
                throw new IllegalArgumentException("To Book a room, please input at least 1 day of stay");
            }

            if (!rooms.containsKey(bookingReq.getRoomNumber())) {
                throw new IllegalArgumentException("Room " + bookingReq.getRoomNumber() + " does not exist");
            }

            if (bookingReq.getBookingDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Booking Date " + bookingReq.getBookingDate() + " is earlier than"
                    + " current date " + LocalDate.now());
            }

            Room room = rooms.get(bookingReq.getRoomNumber());
            List<LocalDate> verifiedBookingDates = verifyBookingDates(room, bookingReq.getBookingDate(), bookingReq.getNumOfDays());

            for (LocalDate verifiedDate : verifiedBookingDates) {
                room.getAllBookingDates().add(verifiedDate);
            }

            room.addBooking(bookingReq.getUsername(), verifiedBookingDates);
            String success = String.format("Room %d Booked, Starting On: %s Ending On: %s for %s", bookingReq.getRoomNumber(), verifiedBookingDates.get(0),
                    verifiedBookingDates.get(verifiedBookingDates.size()-1), bookingReq.getUsername());
            response = success;
            LOGGER.info(success);
        } catch (Exception e) {
            LOGGER.info("Failed to make booking: " + e.getMessage());
            response = "Failed to make booking: " + e.getMessage();
        }
        return response;
    }

    private List<LocalDate> verifyBookingDates(Room room, LocalDate startBookingDate, int numOfDays) { //gap stays not allowed for same user
        List<LocalDate> verifiedBookingDates = new ArrayList<>();
        for (int i = 0; i <= numOfDays; i++) {
            LocalDate bookingDate = startBookingDate.plusDays(i);
            if (room.getAllBookingDates().contains(bookingDate)) {
                throw new IllegalArgumentException("Booking Date " + bookingDate + " has already been booked");
            }
            verifiedBookingDates.add(bookingDate);
        }
        return verifiedBookingDates;
    }

    public Map<Integer, List<LocalDate>> getBookingsFor(String username) {
        Map<Integer, List<LocalDate>> roomBookings = new HashMap<>();
        for (int roomNumber : rooms.keySet()) {
            Map<String, List<LocalDate>> roomGuestBookings = rooms.get(roomNumber).getGuestBookings();
            if (roomGuestBookings.containsKey(username)) {
                roomBookings.put(roomNumber, roomGuestBookings.get(username));
            }
        }
        return roomBookings;
    }

    public String getAvailableRoomsOn(LocalDate availableDate) {
        StringBuilder response = new StringBuilder("Room: ");
        try {
            if (availableDate.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Available Date " + availableDate + " is earlier than"
                        + " current date " + LocalDate.now());
            }

            for (int roomNumber : rooms.keySet()) {
                if (!rooms.get(roomNumber).getAllBookingDates().contains(availableDate)) {
                    if (roomNumber == maxNumberOfRooms) {
                        response.append(roomNumber);
                    } else {
                        response.append(roomNumber).append(", ");
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("Failed to get available rooms: " + e.getMessage());
            response = new StringBuilder("Failed to get available rooms: " + e.getMessage());
        }
        return response.toString();
    }

    public Map<Integer, Room> getRooms() {
        return rooms;
    }

    public int getMaxNumberOfRooms() {
        return maxNumberOfRooms;
    }
}