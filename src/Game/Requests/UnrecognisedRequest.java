package Game.Requests;

import Game.Handlers.ConnectionHandler;
import Game.Interfaces.ICommand;
import Game.Objects.Packet;
import Game.World.World;
import org.json.JSONObject;

public class UnrecognisedRequest implements ICommand {

    @Override
    public void process(Packet data, ConnectionHandler user) {
        Packet p = new Packet("invalidCommand", new JSONObject());
        World.instance.sendPacket(p, user);
    }
  
}
