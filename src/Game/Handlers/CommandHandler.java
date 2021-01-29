package Game.Handlers;

import Game.Interfaces.ICommand;
import Game.Objects.Packet;

import Game.Requests.*;
import Game.Requests.User.*;
import Game.Requests.User.Maps.*;

import java.util.HashMap;

public class CommandHandler {

    private HashMap<String, ICommand> commandMap = new HashMap<>();
    private ConnectionHandler connectionHandler;

    public CommandHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
        initializeCommandMap();
    }

    private void initializeCommandMap() {
        commandMap.put("authenticateUser", new Login());
        commandMap.put("firstJoin", new FirstJoin());
        commandMap.put("updatePosition", new UpdatePosition());
        commandMap.put("getEntitiesInRoom", new GetEntitiesInRoom());
    }

    public void handleCommand(Packet packet) {
        String sysSwitch = packet.getCmd();
        if(commandMap.containsKey(sysSwitch)) {
            ICommand commandRequest = commandMap.get(sysSwitch);
            commandRequest.process(packet,this.connectionHandler);
        } else {
            System.out.println("Command Class not found: " + sysSwitch);
        }
    }

}
