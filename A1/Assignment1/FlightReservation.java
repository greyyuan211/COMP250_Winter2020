public class FlightReservation extends Reservation{
    private Airport departure;//departure
    private Airport arrival;//arrival

    public FlightReservation (String name, Airport departure, Airport arrival){
        super(name);
        if (departure == arrival){
            throw new IllegalArgumentException("Two input airports are the same");
        } else {
            this.arrival = arrival;
            this.departure = departure;
        }
    }

    public int getCost(){
        int distance = Airport.getDistance(departure, arrival);
        double fuelGallon = distance/167.52;
        double fuelCost = 124*fuelGallon;
        int airportCost = departure.getFees()+arrival.getFees();
        return (int)Math.ceil(fuelCost+airportCost+5375);
    }

    public boolean equals(Object object) {
        if(object instanceof FlightReservation) {
            if(((FlightReservation)object).reservationName().equalsIgnoreCase(this.reservationName())&&
                    ((FlightReservation)object).departure.equals(this.departure)&&
                    ((FlightReservation)object).arrival.equals(this.arrival)) {
                return true;
            }
        }
        return false;
    }
}
