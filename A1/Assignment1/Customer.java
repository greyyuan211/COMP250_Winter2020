public class Customer {
    private String name;
    private int balance;
    private Basket reservationsToMake;

    public Customer(String name, int balance) {
        this.name = name;
        this.balance = balance;
        this.reservationsToMake = new Basket();
    }

    public String getName() {
        return this.name;
    }

    public int getBalance() {
        return this.balance;
    }

    public Basket getBasket() {
        return this.reservationsToMake;
    }

    public int addFunds(int funds) {
        if(funds < 0) {
            throw new IllegalArgumentException("Funds received cannot be a negative number.");
        }else {
            balance += funds;
            return balance;
        }
    }

    public int addToBasket(Reservation newCustomer) {
        if(newCustomer.reservationName().equalsIgnoreCase(this.name)) {
            reservationsToMake.add(newCustomer);
            return reservationsToMake.getNumOfReservations();
        }else {
            throw new IllegalArgumentException("Reservation cannot be added because the customer' name does not match");
        }
    }

    public int addToBasket(Hotel hotel, String type, int numNights, boolean breakfast) {
        HotelReservation hotelCustomer = new HotelReservation(this.name, hotel, type, numNights);
        reservationsToMake.add(hotelCustomer);
        return reservationsToMake.getNumOfReservations();
    }

    public int addToBasket(Airport departure, Airport arrival) {
        reservationsToMake.add(new FlightReservation(this.name, departure, arrival));
        return reservationsToMake.getNumOfReservations();
    }

    public boolean removeFromBasket(Reservation reservationRemoved) {
        return reservationsToMake.remove(reservationRemoved);
    }

    public int checkOut() {
        int cost = reservationsToMake.getTotalCost();
        if(cost > this.balance) {
            throw new IllegalStateException("Customer's balance is insufficient");
        }else {
            this.balance -= cost;
            reservationsToMake.clear();
        }
        return this.balance;
    }

}