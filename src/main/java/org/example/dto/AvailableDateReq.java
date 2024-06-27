package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class AvailableDateReq {
    private final LocalDate availableDate;

    public AvailableDateReq(@JsonProperty("availableDate") LocalDate availableDate) {
        this.availableDate = availableDate;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }
}