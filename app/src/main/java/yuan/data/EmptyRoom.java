package yuan.data;

import org.litepal.crud.LitePalSupport;

public class EmptyRoom extends LitePalSupport {

    private String RoomName;
    private int SizeOfRoom;
    private String Location;
    private int time;
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


    public EmptyRoom(String name,int number,String location,int time) {
        this.RoomName=name;
        this.SizeOfRoom=number;
        this.Location=location;
        this.time=time;
    }
    public  EmptyRoom(String name,int number,String location)
    {
        this.RoomName=name;
        this.SizeOfRoom=number;
        this.Location=location;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public int getSizeOfRoom() {
        return SizeOfRoom;
    }

    public void setSizeOfRoom(int sizeOfRoom) {
        SizeOfRoom = sizeOfRoom;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}