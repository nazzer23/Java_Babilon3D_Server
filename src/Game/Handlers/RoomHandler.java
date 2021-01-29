package Game.Handlers;

import Game.Objects.Map;
import Game.Objects.Packet;
import Game.Objects.Room;
import Game.Objects.User;
import Game.World.World;
import java.util.ArrayList;
import org.json.JSONObject;

public class RoomHandler {

    public static RoomHandler instance;
    public ArrayList<Room> roomList = new ArrayList<>();

    public RoomHandler() {
        instance = this;
    }

    public void createRoomInstance(Map m) {
        Room r = new Room(m, 1);
        roomList.add(r);
    }
        
    public void joinRoom(String roomName, User user) {
        if(this.roomList.size() > 0) {       
            for(Room r : this.roomList) {
                if(r.MapObj.mapName.equals(roomName)) {
                    if(r.usersInInstance.size() < r.MapObj.maxPlayers) {
                        JSONObject params = new JSONObject();
                        params.put("areaName", r.MapObj.mapName);
                        params.put("sceneName", r.MapObj.file);
                        
                        JSONObject position = new JSONObject();
                        position.put("SpawnPosX", r.MapObj.SpawnPosX);
                        position.put("SpawnPosY", r.MapObj.SpawnPosY);
                        position.put("SpawnPosZ", r.MapObj.SpawnPosZ);
                        position.put("SpawnRotX", r.MapObj.SpawnRotX);
                        position.put("SpawnRotY", r.MapObj.SpawnRotY);
                        position.put("SpawnRotZ", r.MapObj.SpawnRotZ);
                        
                        params.put("position", position);
                        
                        Packet p = new Packet("joinRoom", params);
                        World.instance.sendToSingleUser(p, user);
                        
                        params = new JSONObject();
                        
                        params.put("userID", user.CharacterID);
                        params.put("userName", user.Username);
                        params.put("userLevel", user.Level);
                        
                        params.put("PosX", r.MapObj.SpawnPosX);
                        params.put("PosY", r.MapObj.SpawnPosY);
                        params.put("PosZ", r.MapObj.SpawnPosZ);
                        params.put("RotX", r.MapObj.SpawnRotX);
                        params.put("RotY", r.MapObj.SpawnRotY);
                        params.put("RotZ", r.MapObj.SpawnRotZ);
                        
                        Packet userJoin = new Packet("userJoin", params);
                        
                        World.instance.sendToRoomExceptMe(userJoin, r, user);
                        
                        r.usersInInstance.add(user);
                        user.Room = r;
                    } else {
                        this.createRoomInstance(MapHandler.instance.getMap(roomName));
                        joinRoom(roomName, user);
                    }
                }
            }
        } else {
            if(MapHandler.instance.DoesMapExist(roomName)) {
                this.createRoomInstance(MapHandler.instance.getMap(roomName));
                joinRoom(roomName, user);
            } else {
                JSONObject params = new JSONObject();
                params.put("Message","The room you tried to join does not exist.");
                Packet p = new Packet("roomFail", params);
                World.instance.sendToSingleUser(p, user);
            }
        }
    }
    
    public void leaveRoom(Room r, User user) {
        
    }

}
