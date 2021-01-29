package Game.Interfaces;

import Game.Handlers.ConnectionHandler;
import Game.Objects.Packet;

public interface ICommand {
    public void process(Packet data, ConnectionHandler user);
}
