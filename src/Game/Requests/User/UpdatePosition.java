package Game.Requests.User;

import Game.Handlers.ConnectionHandler;
import Game.Interfaces.ICommand;
import Game.Objects.Packet;
import Game.World.World;
import org.json.JSONObject;

public class UpdatePosition implements ICommand {

    @Override
    public void process(Packet data, ConnectionHandler user) {
        long PositionX = data.getParams().getLong("PosX");
        long PositionY = data.getParams().getLong("PosY");
        long PositionZ = data.getParams().getLong("PosZ");
        long RotationX = data.getParams().getLong("RotX");
        long RotationY = data.getParams().getLong("RotY");
        long RotationZ = data.getParams().getLong("RotZ");
        
        user.userObject.positionX = PositionX;
        user.userObject.positionY = PositionY;
        user.userObject.positionZ = PositionZ;
        user.userObject.rotationX = RotationX;
        user.userObject.rotationY = RotationY;
        user.userObject.rotationZ = RotationZ;
        
        JSONObject params = new JSONObject();
        
        params.put("userID", user.userObject.CharacterID);
        
        params.put("PosX", PositionX);
        params.put("PosY", PositionY);
        params.put("PosZ", PositionZ);
        params.put("RotX", RotationX);
        params.put("RotY", RotationY);
        params.put("RotZ", RotationZ);
        
        Packet packet = new Packet("updatePosition", params);
        
        World.instance.sendToRoomExceptMe(packet, user.userObject.Room, user.userObject);
        
    }
    
}
