package com.cab.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.cab.booking.Exception.CabNotAvailableException;
import com.cab.booking.model.*;
import com.cab.booking.services.CabService;
import com.cab.booking.services.RiderService;
import com.cab.booking.services.TripsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BookingApplicationTests {
    @Autowired
    private RiderService riderService;
    @Autowired
    private CabService cabService;
    @Autowired
    private TripsService tripsService;

    @BeforeEach
    void init() {
        // create 6 rider
        for (int counter = 0; counter < 5; counter++) {
            riderService.registerRider(new Rider(counter + "", "Dummy-" + counter));
        }

        // create 3 cabs
        for (int counter = 0; counter < 3; counter++) {
            cabService.register(new Cab(counter + "", "CabDriver-" + counter, CabType.MICRO));
        }

        // location of 3 cabs
        // 1) 0,0
        // 2) 100,100
        // 3) 200,200
        for (int counter = 0; counter < 3; counter++) {
            cabService.updateLocation(counter + "", new Location(counter * 100, counter * 100));
        }
    }

    @Test
    void testBookCabs() throws CabNotAvailableException {
        // book a cab for rider 1
        String riderId = "0";
        Cab cab = tripsService.book(riderId, new Location(900, 900), new Location(0, 0), CabType.MICRO);

        assertNotNull(cab);
        assertEquals(cab.getTrip().getStatus(), TripStatus.IN_PROGRESS);

        // cab location at this moment when cab is booked
        assertEquals(cab.getLocation().getX(), 0);
        assertEquals(cab.getLocation().getY(), 0);


        // End this trip & now cab reached to destination
        tripsService.endTrip(cab, new Location(900, 900));
        assertEquals(cab.getLocation().getX(), 900);
        assertEquals(cab.getLocation().getY(), 900);
        assertEquals(cab.getTrip().getStatus(), TripStatus.COMPLETED);
    }

    @Test
    public void testPastBooking() throws CabNotAvailableException {
        // book a cab for rider 1
        String riderId = "0";
        Cab cab = tripsService.book(riderId, new Location(900, 900), new Location(0, 0), CabType.MICRO);
        assertNotNull(cab);
        tripsService.endTrip(cab, new Location(900, 900));

        cab = tripsService.book(riderId, new Location(900, 900), new Location(100, 100), CabType.MICRO);
        assertNotNull(cab);
        tripsService.endTrip(cab, new Location(900, 900));

        List<Trip> trips = tripsService.fetchPastBookings(riderId);
        assertEquals(trips.size(), 2);

        for (Trip t : trips) {
            assertEquals(t.getStatus(), TripStatus.COMPLETED);
        }
    }
}
