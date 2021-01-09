public abstract class Reservation {
    private String clientName;

    public Reservation (String clientName) {
        this.clientName = clientName;
    }

    public final String reservationName (){
        return this.clientName;
    }

    public abstract int getCost(); //return price which depends on the type of the reservation

    public abstract boolean equals(Object object);//different conditions should be met in order for two reservations to be considered equal

}
