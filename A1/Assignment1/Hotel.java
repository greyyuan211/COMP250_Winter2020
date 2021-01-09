public class Hotel {
    private String hotelName;
    private Room[] rooms;

    public Hotel (String hotelName, Room[] rooms) {
        //initialize the corresponding field
        this.hotelName = hotelName;
        this.rooms = new Room[rooms.length];
        for(int i=0; i<rooms.length; i++) {
            this.rooms[i] = new Room(rooms[i]);
        }
    }

    public int reserveRoom (String roomType) {
        if(Room.findAvailableRoom(rooms, roomType)!= null) {
            Room roomChosen = Room.findAvailableRoom(rooms,roomType);
            roomChosen.changeAvailability();
            return roomChosen.getPrice();
        }else {
            throw new IllegalArgumentException("No room of such type is available");//not sure about the message
        }
    }

    public boolean cancelRoom (String roomType){
        return Room.makeRoomAvailable(rooms, roomType);
    }
}
