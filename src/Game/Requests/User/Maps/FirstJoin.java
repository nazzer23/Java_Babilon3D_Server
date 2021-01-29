package Game.Requests.User.Maps;

import Game.Handlers.ConnectionHandler;
import Game.Handlers.RoomHandler;
import Game.Interfaces.ICommand;
import Game.Objects.Packet;

public class FirstJoin implements ICommand {

    @Override
    public void process(Packet data, ConnectionHandler user) {
        RoomHandler.instance.joinRoom("Home", user.userObject);
    }
    
}
