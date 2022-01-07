package com.cab.booking.controllers;

import com.cab.booking.model.Cab;
import com.cab.booking.model.CabType;
import com.cab.booking.model.Location;
import com.cab.booking.services.CabService;
import com.cab.booking.services.TripsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CabsController {
    private CabService cabService;
    private TripsService tripsService;

    @Autowired
    public CabsController(TripsService tripsService, CabService cabService) {
        this.cabService = cabService;
        this.tripsService = tripsService;
    }

    @RequestMapping(value = "/cab/register", method = RequestMethod.POST)
    public ResponseEntity registerCab(String id, String driverName, String cabType) {
        cabService.register(new Cab(id, driverName, CabType.valueOf(cabType)));
        return ResponseEntity.ok(id);
    }

    @RequestMapping(value = "/cab/available", method = RequestMethod.POST)
    public ResponseEntity updateAvailability(String cabId, boolean isAvailable) {
        cabService.updateAvailability(cabId, isAvailable);
        return ResponseEntity.ok(cabId);
    }

    @RequestMapping(value = "/cab/update/location", method = RequestMethod.POST)
    public ResponseEntity updateLocation(String cabId, Location location) {
        cabService.updateLocation(cabId, location);
        return ResponseEntity.ok(cabId);
    }

    @RequestMapping(value = "/cab/endtrip", method = RequestMethod.POST)
    public ResponseEntity endTrip(String cabId, Location location) {
        tripsService.endTrip(cabService.getCab(cabId), location);
        return ResponseEntity.ok(cabId);
    }
}
