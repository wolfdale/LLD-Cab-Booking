package com.cab.booking.model;

/*
    Cab types and respective commission
 */
public enum CabType {
    MICRO(0.1),
    MINI(0.2),
    SEDAN(0.3),
    SUV(0.4),
    OUTSTATION(0.5);

    private double commission;

    CabType(double commission) {
        this.commission = commission;
    }

    public double getCommission() {
        return commission;
    }
}
