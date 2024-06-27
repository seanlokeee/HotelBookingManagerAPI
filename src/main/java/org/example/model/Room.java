package org.example.model;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Room {
    private Map<String, List<LocalDate>> guestBookings;
    private List<LocalDate> allBookingDates; //to prevent heavy booking date verification on map

    public Room() {
        guestBookings = new ConcurrentHashMap<>();
        allBookingDates = new ArrayList<>();
    }

    public void addBooking(String username, List<LocalDate> bookingDates) {
        guestBookings.put(username, bookingDates);
    }

    public List<LocalDate> getAllBookingDates() {
        return allBookingDates;
    }

    public Map<String, List<LocalDate>> getGuestBookings() {
        return guestBookings;
    }
}