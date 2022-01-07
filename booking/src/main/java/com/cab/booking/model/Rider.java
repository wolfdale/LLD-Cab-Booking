package com.cab.booking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Rider {
    private String id;
    private String name;
    private long lastBookedTrip;

    public Rider(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
