package com.cab.booking.services;

import com.cab.booking.model.Rider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RiderService {
    public static final long MILI_PER_DAY =  86400000;

    private Map<String, Rider> riderData = new HashMap<>();

    public void registerRider(Rider rider) {
        riderData.put(rider.getId(), rider);
    }
    public Rider getRider(String id) {
        return riderData.get(id);
    }

    public List<Rider> getActiveUsers() {
        long currentTime = System.currentTimeMillis();
        long twoDaysBack = currentTime - MILI_PER_DAY;

        return riderData.values().stream()
                .filter(rider -> (rider.getLastBookedTrip() - twoDaysBack) > 0)
                .collect(Collectors.toList());

    }

    public List<Rider> getDormantUsers() {
        long currentTime = System.currentTimeMillis();
        long twoDaysBack = currentTime - MILI_PER_DAY;

        return riderData.values().stream()
                .filter(rider -> (rider.getLastBookedTrip() - twoDaysBack) < 0)
                .collect(Collectors.toList());
    }
}
