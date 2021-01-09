public class Airport {

    private int x_position,y_position,fees;

    public Airport (int x_position, int y_position,int fees){
        this.x_position = x_position;
        this.y_position = y_position;
        this.fees = fees;
    }

    public int getFees(){
        return this.fees;
    }

    public static int getDistance(Airport departure, Airport arrival){
        int distance;
        distance = (int)Math.ceil(Math.sqrt(Math.pow(departure.x_position-arrival.x_position,2)+(Math.pow(departure.y_position-arrival.y_position,2))));
        return distance;
    }
}
