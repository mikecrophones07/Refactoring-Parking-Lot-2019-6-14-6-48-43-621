package com.oocl.cultivation;

import java.util.List;
import java.util.Objects;

public class ParkingBoy {

    private final List<ParkingLot> parkingLotList;

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public ParkingBoy(List<ParkingLot> parkingLotList) {
        this.parkingLotList = parkingLotList;
    }

    public String getMessage() {
        return this.message;
    }

    protected List<ParkingLot> getParkingLots() {
        return this.parkingLotList;
    }

    public ParkingTicket park(Car car) {
        ParkingLot currentParkingLot = getAvailableParkingLot();
        if(Objects.nonNull(currentParkingLot) && currentParkingLot.getAvailableParkingPosition() != 0) {
            ParkingTicket parkingTicket = new ParkingTicket();
            currentParkingLot.park(car, parkingTicket);
            return parkingTicket;
        }
        message = "Not enough position.";
        return null;
    }

    private ParkingLot getAvailableParkingLot() {
        return parkingLotList.stream().filter(parkingLot -> parkingLot.getAvailableParkingPosition() != 0).findFirst().orElse(null);
    }

    public Car fetch(ParkingTicket ticket) {
        if(Objects.nonNull(ticket)) {
            ParkingLot currentParkingLot = parkingLotList.stream().filter(parkingLot -> findParkingLot(ticket, parkingLot)).findFirst().orElse(null);
            if (Objects.nonNull(currentParkingLot)) {
                return currentParkingLot.fetch(ticket);
            }
            message = "Please provide your parking ticket.";
            return null;
        }
        message = "Unrecognized parking ticket.";
        return null;
    }

    private boolean findParkingLot(ParkingTicket ticket, ParkingLot parkingLot) {
        return parkingLot.findCar(ticket);
    }


}
