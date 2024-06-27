package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class BookingReq {
    private final String username;
    private final int roomNumber;
    private final LocalDate bookingDate;
    private final int numOfDays;

    public BookingReq(@JsonProperty("username") String username, @JsonProperty("roomNumber") int roomNumber,
                      @JsonProperty("bookingDate") LocalDate bookingDate, @JsonProperty("numOfDays") int numOfDays) {
        this.username = username;
        this.roomNumber = roomNumber;
        this.bookingDate = bookingDate;
        this.numOfDays = numOfDays;
    }

    public String getUsername() {
        return username;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public int getNumOfDays() {
        return numOfDays;
    }
}
