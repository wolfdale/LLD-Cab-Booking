package com.cab.booking.controllers;

import com.cab.booking.Exception.CabNotAvailableException;
import com.cab.booking.model.Cab;
import com.cab.booking.model.Location;
import com.cab.booking.model.Rider;
import com.cab.booking.model.Trip;
import com.cab.booking.services.RiderService;
import com.cab.booking.services.TripsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RiderController {
    RiderService riderService;
    TripsService tripsService;

    @Autowired
    public RiderController(RiderService riderService, TripsService tripsService) {
        this.riderService = riderService;
        this.tripsService = tripsService;
    }

    @RequestMapping(value = "/rider/register", method = RequestMethod.POST)
    public ResponseEntity register(String id, String name) {
        riderService.registerRider(new Rider(id, name));
        return ResponseEntity.ok(id);
    }

    @RequestMapping(value = "/rider/bookings", method = RequestMethod.POST)
    public List<Trip> fetchBooking(String id) {
        return tripsService.fetchPastBookings(id);
    }

    @RequestMapping(value = "/rider/book", method = RequestMethod.POST)
    public ResponseEntity bookCab(String id, Location to, Location from) {
        try {
            return ResponseEntity.ok(tripsService.book(id, to, from));
        }
        catch (CabNotAvailableException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
