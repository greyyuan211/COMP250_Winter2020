import java.util.Arrays;
import java.util.Random;

public class TrainLine {

	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	public static Random rand;

	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
		/*
		 * Constructor for TrainStation input: stationList - An array of TrainStation
		 * containing the stations to be placed in the line name - Name of the line
		 * goingRight - boolean indicating the direction of travel
		 */
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];

		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 0; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]);
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name,
					 boolean goingRight) {/*
	 * Constructor for TrainStation. input: stationNames - An array of String
	 * containing the name of the stations to be placed in the line name - Name of
	 * the line goingRight - boolean indicating the direction of travel
	 */
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i]));
		}

		this.lineMap = this.getLineArray();

	}

	// adds a station at the last position before the right terminus
	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft();
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}

	public String getName() {
		return this.lineName;
	}

	public int getSize() {
        if(this.lineMap == null) {
            return 0;
        }else {
            return this.lineMap.length;
        }
	}

	public void reverseDirection() {
		this.goingRight = !(this.goingRight);
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation travelOneStation(TrainStation current, TrainStation previous) {
		findStation(current.getName());
		if (current.getTransferStation() == null) {
			return getNext(current);
		}//catch null pointer exception
		if ((current.hasConnection)&&(previous.getTransferStation() == current)) {
			return getNext(current);
		}
		return current.getTransferStation();
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation getNext(TrainStation station) {
		findStation(station.getName());
		Boolean goingLeft = !goingRight;
		if (goingLeft){
			if(station == leftTerminus) {
				reverseDirection();
				return station.getRight();
			}
			return station.getLeft();
		}
		if (goingRight){
			if(station == rightTerminus) {
				reverseDirection();
				return station.getLeft();
			}
			return station.getRight();
		}
		throw new StationNotFoundException(station.getName());
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation findStation(String name) {
		for (int i = 0; i < lineMap.length ; i++) {
			if (name == lineMap[i].getName()) {
				return lineMap[i];
			}
		}
		throw new StationNotFoundException(name);
		// change this!
	}

	public TrainStation swap(TrainStation ts1, TrainStation ts2) {

		TrainStation leftS = ts1.getLeft();
		TrainStation rightS = ts2.getRight();
		leftS.setRight(ts2);
		rightS.setLeft(ts1);
		ts2.setLeft(leftS);
		ts1.setRight(rightS);
		ts1.setLeft(ts2);
		ts2.setRight(ts1);
		return ts2;
	}

	public void sortLine() {

		TrainStation leftNode = new TrainStation("lNull");
		TrainStation rightNode = new TrainStation("rNull");

		leftNode.setRight(leftTerminus);
		rightNode.setLeft(rightTerminus);
		leftTerminus.setLeft(leftNode);    //set
		rightTerminus.setRight(rightNode);
		leftNode.setLeftTerminal();
		rightNode.setRightTerminal();
		leftTerminus.setNonTerminal();     //clear
		rightTerminus.setNonTerminal();

		for (int i = 0; i < this.getSize()-1; i++) {
			TrainStation ts = leftNode.getRight();
			boolean flag = true;
			for (int j = 0; j < this.getSize()-i-1; j++) {
				if (ts.getName().compareTo(ts.getRight().getName())>0) {
					flag = false;
					ts = swap(ts,ts.getRight());
				}
				ts = ts.getRight();
			}
			if (flag) break;
		}

		leftTerminus = leftNode.getRight();
		rightTerminus = rightNode.getLeft();
		leftTerminus.setLeftTerminal();
		rightTerminus.setRightTerminal();
		leftTerminus.setLeft(null);
		rightTerminus.setRight(null);
		leftTerminus.setTrainLine(this);
		rightTerminus.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}


	public TrainStation[] getLineArray(){

		// YOUR CODE GOES HERE
		TrainStation[] line = {leftTerminus,rightTerminus};
		TrainStation rightT = rightTerminus;

		while(true) {
			if(!(rightT.equals(leftTerminus.getRight()))) {
				TrainStation[] newLine = new TrainStation[line.length+1];
				for(int i = 0; i < line.length; i++) {
					newLine[i+1] = line[i];
				}
				newLine[1] = rightT.getLeft();
				rightT = rightT.getLeft();
				newLine[0] = leftTerminus;
				line = newLine;
			}else {
				break;
			}
		}
		return line;
	}

	private TrainStation[] shuffleArray(TrainStation[] array) {
		Random rand = new Random();
		rand.setSeed(11);
		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}

	public void shuffleLine() {

		// you are given a shuffled array of trainStations to start with
		TrainStation[] lineArray = this.getLineArray();
		TrainStation[] shuffledArray = shuffleArray(lineArray);

		// YOUR CODE GOES HERE
		int lastOne = shuffledArray.length - 1;
		shuffledArray[0].setRight(shuffledArray[lastOne]);
		shuffledArray[lastOne].setLeft(shuffledArray[0]);

		leftTerminus.setNonTerminal();
		rightTerminus.setNonTerminal();
		leftTerminus = shuffledArray[0];
		rightTerminus = shuffledArray[lastOne];
		leftTerminus.setLeftTerminal();
		rightTerminus.setRightTerminal();
		leftTerminus.setTrainLine(this);
		rightTerminus.setTrainLine(this);
		leftTerminus.setLeft(null);
		rightTerminus.setRight(null);

		for (int i = 1; i < shuffledArray.length - 1; i++) {
			addStation(shuffledArray[i]);
		}
	}

	// YOUR CODE GOES HERE

	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2))
					return false;
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus;
	}

}

//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}
}