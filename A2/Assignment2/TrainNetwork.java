public class TrainNetwork {
    final int swapFreq = 2;
    TrainLine[] networkLines;

    public TrainNetwork(int nLines) {
        this.networkLines = new TrainLine[nLines];
    }

    public void addLines(TrainLine[] lines) {
        this.networkLines = lines;
    }

    public TrainLine[] getLines() {
        return this.networkLines;
    }

    public void dance() {
        //YOUR CODE GOES HERE

        for (int i=0; i<networkLines.length;i++) {
            networkLines[i].shuffleLine();
        }
        System.out.println("The tracks are moving!");
    }

    public void undance() {
        //YOUR CODE GOES HERE

        for (int i=0; i<networkLines.length;i++) {
            networkLines[i].sortLine();
        }
    }

    public int travel(String startStation, String startLine, String endStation, String endLine) {

        TrainLine curLine = null; //use this variable to store the current line.
        TrainStation curStation= null; //use this variable to store the current station.

        int hoursCount = 0;
        System.out.println("Departing from "+startStation);

        //YOUR CODE GOES HERE

        TrainStation temp = null;
        curLine = this.getLineByName(startLine);
        curStation = curLine.findStation(startStation);
        TrainStation previous = new TrainStation("previous");

        while((curStation.getName())!=endStation/*you can change this*/) {

            if(hoursCount == 168) {
                System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
                return hoursCount;
            }
            hoursCount++;

            if ((hoursCount!=1) && ((hoursCount%swapFreq)==1)) dance();

            if (curStation.hasConnection && previous == null){
                temp = curStation;
                curStation = curStation.getTransferStation();
                previous = temp;
            } else {
                //swap
                temp = curStation;
                curStation = curLine.travelOneStation(curStation, previous);
                previous = temp;
            }
            curLine = curStation.getLine();

            //prints an update on your current location in the network.
            System.out.println("Traveling on line "+curLine.getName()+":"+curLine.toString());
            System.out.println("Hour "+hoursCount+". Current station: "+curStation.getName()+" on line "+curLine.getName());
            System.out.println("=============================================");
        }

        System.out.println("Arrived at destination after "+hoursCount+" hours!");
        return hoursCount;
    }


    //you can extend the method header if needed to include an exception. You cannot make any other change to the header.
    public TrainLine getLineByName(String lineName){
        //YOUR CODE GOES HERE

        for (int i = 0; i < networkLines.length; i++) {
            if (networkLines[i].getName().equalsIgnoreCase(lineName))  return networkLines[i];
        }
        throw new LineNotFoundException(lineName);
    }

    //prints a plan of the network for you.
    public void printPlan() {
        System.out.println("CURRENT TRAIN NETWORK PLAN");
        System.out.println("----------------------------");
        for(int i=0;i<this.networkLines.length;i++) {
            System.out.println(this.networkLines[i].getName()+":"+this.networkLines[i].toString());
        }
        System.out.println("----------------------------");
    }
}

//exception when searching a network for a LineName and not finding any matching Line object.
class LineNotFoundException extends RuntimeException {
    String name;

    public LineNotFoundException(String n) {
        name = n;
    }

    public String toString() {
        return "LineNotFoundException[" + name + "]";
    }
}