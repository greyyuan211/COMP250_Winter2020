public class Room {
    private String type;
    private int price;
    private boolean isAvailable;

    public Room (String type){
        this.isAvailable = true;
        if (type.equalsIgnoreCase("double")){
            this.type = type.toLowerCase();
            this.price = 9000;
            this.isAvailable = true;
        }
        else if(type.equalsIgnoreCase("queen")){
            this.type = type.toLowerCase();
            this.price = 11000;
            this.isAvailable = true;
        }
        else if(type.equalsIgnoreCase("king")){
            this.type = type.toLowerCase();
            this.price = 15000;
            this.isAvailable = true;
        }
        else{
            throw new IllegalArgumentException("no room of such type can be created");
        }
    }

    public Room (Room room){
        this.type = room.getType();
        this.price = room.getPrice();
        this.isAvailable = room.isAvailable;
    }

    public String getType(){
        return this.type;
    }

    public int getPrice(){
        return this.price;
    }

    public void changeAvailability(){
        isAvailable = !this.isAvailable;
    }

    public static Room findAvailableRoom(Room[] rooms, String roomType){
        Room room = null;
        for (int i=0; i<rooms.length;i++){
            if ((rooms[i].type == roomType) && (rooms[i].isAvailable)){
                room = rooms[i];
                break;
            }
        }
        return room;
    }

    public static boolean makeRoomAvailable (Room[] rooms, String roomType) {
        for (int i = 0; i < rooms.length; i++) {
            if ((rooms[i].isAvailable == false) && (rooms[i].type == roomType)) {
                rooms[i].isAvailable = true;
                return true;
            }
        }
        return false;
    }


}

