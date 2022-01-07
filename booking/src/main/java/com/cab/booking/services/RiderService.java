package com.cab.booking.services;

import com.cab.booking.model.Rider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RiderService {
    private Map<String, Rider> riderData = new HashMap<>();

    public void registerRider(Rider rider) {
        riderData.put(rider.getId(), rider);
    }
    public Rider getRider(String id) {
        return riderData.get(id);
    }
}
