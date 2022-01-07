package com.cab.booking.services;

import com.cab.booking.model.Cab;
import com.cab.booking.model.CabType;
import com.cab.booking.model.Location;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CabService {
    private Map<String, Cab> cabs = new HashMap<>();

    public void register(Cab cab) {
        cabs.put(cab.getId(), cab);
    }

    public void updateAvailability(String cabId, boolean available) {
        Cab cab = cabs.get(cabId);
        cab.setAvailable(available);
    }

    public void updateLocation(String cabId, Location location) {
        Cab cab = cabs.get(cabId);
        cab.setLocation(location);
    }

    public Cab getCab(String cabId) {
        return cabs.get(cabId);
    }

    public List<Cab> getAllCabsAtLocationByType(Location startingPoint, CabType cabType) {
        return cabs.values().stream()
                .filter(cab -> cab.isCabAtGivenLocation(startingPoint))
                .filter(cab -> cab.getType().equals(cabType))
                .collect(Collectors.toList());
    }
}
