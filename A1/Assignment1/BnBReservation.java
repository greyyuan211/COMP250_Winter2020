public class BnBReservation extends HotelReservation{

    public BnBReservation(String clientName, Hotel hotelToReserve, String typeToReserve, int nightsSpent) {
        super(clientName, hotelToReserve, typeToReserve, nightsSpent);
    }

    public int getCost() {
        return super.getCost() + this.getNumOfNights()*1000;
    }

}
