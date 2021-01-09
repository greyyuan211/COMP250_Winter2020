public class HotelReservation extends Reservation{
    private Hotel hotelToReserve;
    private String typeToReserve;
    private int nightsSpent;
    private int pricePerNight;

    public HotelReservation(String clientName, Hotel hotelToReserve, String typeToReserve, int nightsSpent){
        super(clientName);
        this.hotelToReserve = hotelToReserve;
        this.typeToReserve = typeToReserve;
        this.nightsSpent = nightsSpent;
        this.pricePerNight = hotelToReserve.reserveRoom(typeToReserve);//exception will be thrown in reserveRoom()
    }

    public int getNumOfNights(){
        return this.nightsSpent;
    }

    public int getCost() {
        return this.nightsSpent*this.pricePerNight;
    }

    public boolean equals(Object object) {
        if(object instanceof HotelReservation) {
            if(((HotelReservation)object).hotelToReserve.equals(this.hotelToReserve)&&
                    ((HotelReservation)object).typeToReserve.equalsIgnoreCase(this.typeToReserve)&&
                    ((HotelReservation)object).reservationName().equalsIgnoreCase(this.reservationName())&&
                    ((HotelReservation)object).getNumOfNights()==(this.nightsSpent)&&
                    ((HotelReservation)object).getCost()==(this.pricePerNight*this.nightsSpent)) {
                return true;
            }
        }
        return false;
    }
}
