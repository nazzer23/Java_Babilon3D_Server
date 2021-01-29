/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Requests.User.Maps;

import Game.Handlers.ConnectionHandler;
import Game.Interfaces.ICommand;
import Game.Objects.Packet;
import Game.Objects.User;
import Game.World.World;
import org.json.JSONObject;

/**
 *
 * @author nazze
 */
public class GetEntitiesInRoom implements ICommand {

    @Override
    public void process(Packet data, ConnectionHandler user) {
        for(User userRoom : user.userObject.Room.usersInInstance) {
            if(userRoom != user.userObject) {
                JSONObject params = new JSONObject();
                params.put("userID", userRoom.CharacterID);
                params.put("userName", userRoom.Username);
                params.put("userLevel", userRoom.Level);
                params.put("PosX", userRoom.positionX);
                params.put("PosY", userRoom.positionY);
                params.put("PosZ", userRoom.positionZ);
                params.put("RotX", userRoom.rotationX);
                params.put("RotY", userRoom.rotationY);
                params.put("RotZ", userRoom.rotationZ);
                Packet userJoin = new Packet("userJoin", params);
                System.out.println("Sending userJoin for user " + userRoom.Username + " to user " + user.userObject.Username);
                World.instance.sendToSingleUser(userJoin, user.userObject);
            }
        }
    }
    
}
