package Game.Objects;

import java.util.ArrayList;

public class Room {

    public Map MapObj;
    public int InstanceID = 0;
    public ArrayList<User> usersInInstance = new ArrayList<>();
    
    public Room(Map map, int instanceID) {
        this.MapObj = map;
        this.InstanceID = instanceID;
    }

}
