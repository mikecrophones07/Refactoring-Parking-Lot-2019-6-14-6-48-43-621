package com.oocl.cultivation;

import java.util.List;
import java.util.Objects;

public class SuperSmartParkingBoy extends SmartParkingBoy {

    public SuperSmartParkingBoy(ParkingBoy parkingBoy) {
        super(parkingBoy);
    }

    private List<ParkingLot> parkingLotList = getParkingLots();

    @Override
    public ParkingTicket park(Car car) {
        ParkingLot currentParkingLot = getLargerParkingLot();
        if(Objects.nonNull(currentParkingLot)) {
            ParkingTicket parkingTicket = new ParkingTicket();
            currentParkingLot.park(car, parkingTicket);
            return parkingTicket;
        }
        this.setMessage(Messages.NOT_ENOUGH_POSITION);
        return null;
    }

    @Override
    public ParkingLot getLargerParkingLot() {
        return parkingLotList.stream().reduce(null, this::getMoreAvailableParkingLot);
    }

    @Override
    public ParkingLot getMoreAvailableParkingLot(ParkingLot parkingLot1, ParkingLot parkingLot2) {
        if(parkingLot2.getAvailableParkingPosition() != 0) {
            if (Objects.nonNull(parkingLot1) && Math.abs(parkingLot1.getAvailableParkingPosition())/10 >= Math.abs(parkingLot2.getAvailableParkingPosition())/10) {
                return parkingLot1;
            }
            return parkingLot2;
        }
        return null;
    }
}
