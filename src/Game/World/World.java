package Game.World;

import Game.Handlers.ConnectionHandler;
import Game.Handlers.MapHandler;
import Game.Handlers.RoomHandler;
import Game.Main;
import Game.Objects.Packet;
import Game.Objects.Room;
import Game.Objects.User;
import org.json.JSONObject;

public class World {

    public static World instance;
    public MapHandler mapHandler;
    public RoomHandler roomHandler;

    public World() {
        instance = this;
        mapHandler = new MapHandler();
        roomHandler = new RoomHandler();
        System.out.println("[World] Initialized.");
    }

    public void sendToEveryone(Packet packet) {
        for(ConnectionHandler ch : Main.clients) {
            this.sendPacket(packet, ch);
        }
    }

    public void sendToSingleUser(Packet packet, User user) {
        this.sendPacket(packet, user.connectionHandler);
    }

    public void sendToRoom(Packet packet, Room room) {
        for(User user : room.usersInInstance) {
            this.sendToSingleUser(packet, user);
        }

    }
    
    public void sendToRoomExceptMe(Packet packet, Room room, User me) {
        for(User user : room.usersInInstance) {
            if(user != me) {
                this.sendToSingleUser(packet, user);
            }
        }
    }

    public void sendPacket(Packet packet, ConnectionHandler user){
        String msg = packet.object.toString().trim();
        user.socketOut.println(msg);
        user.socketOut.flush();
        System.out.println("[SENT] ["+user.clientSocket.getInetAddress()+"] ["+msg.length()+"] "+msg);
    }

    public void sendLoginResponse(ConnectionHandler user, boolean success, String s) {
        JSONObject obj = new JSONObject();
        obj.put("Success", success);
        if(success) {
            if(s != null){
                obj.put("Message", s);
            }
        } else {
            obj.put("Message", s);
        }
        Packet p = new Packet("loginResponse", obj);
        sendPacket(p, user);
    }

}