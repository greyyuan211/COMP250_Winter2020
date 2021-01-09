public class Basket {
    private Reservation[] reservations;

    public Basket() {
        reservations = new Reservation[0];
    }

    public Reservation[] getProducts() {
        Reservation[] products = new Reservation[reservations.length];
        for(int i=0; i<reservations.length; i++) {
            products[i] = reservations[i];
        }
        return products;
    }

    public int add(Reservation reservationAdded) {
        Reservation[] copy = new Reservation[reservations.length+1];
        for(int i=0; i<reservations.length; i++) {
            copy[i] = reservations[i];
        }
        copy[reservations.length] = reservationAdded;
        reservations = copy;
        return reservations.length;
    }

    public boolean remove(Reservation reservationRemoved) {
        int s = 0;
        int i = 0;
        boolean isRemoved = false;
        int n = reservations.length;
        if(n == 0)
            return false;
        Reservation[] newReservation = new Reservation[n-1];

        while(i < n-1) {

            if(reservations[i].equals(reservationRemoved)) {
                s=1;
                isRemoved = true;
            }

            switch(s) {
                case 0:
                    newReservation[i] = reservations[i];
                    i++;
                    break;
                case 1:
                    newReservation[i] = reservations[i+1];
                    i++;
                    break;
            }
        }
        if(isRemoved)
            this.reservations = newReservation;
        return isRemoved;
    }

    public void clear() {
        this.reservations = new Reservation[0];
    }

    public int getNumOfReservations() {
        return this.reservations.length;
    }

    public int getTotalCost() {
        int sum = 0;
        for(int i=0; i<this.reservations.length; i++) {
            sum += this.reservations[i].getCost();
        }
        return sum;
    }

}