package Game.Objects;

import Game.Enums.PlayerStates;
import Game.Handlers.ConnectionHandler;
import Game.World.World;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    public int PlayerID = 0;
    public int CharacterID = 0;

    public Room Room;
    public ConnectionHandler connectionHandler;

    public String Username;
    public int Level;
    public int Access;

    public PlayerStates state = PlayerStates.IDLE;

    public int HP = 100;
    public int MP = 100;
    public int HPMax = 100;
    public int MPMax = 100;

    public float positionX;
    public float positionY;
    public float positionZ;
    
    public float rotationX;
    public float rotationY;
    public float rotationZ;

    public User(int PlayerID, int CharacterID, ConnectionHandler connectionHandler) {
        this.PlayerID = PlayerID;
        this.CharacterID = CharacterID;
        this.connectionHandler = connectionHandler;
    }

    public void handleUserLogin(ResultSet resultSet, Packet data) {
        try {
            
            this.Username = resultSet.getString("Name");
            this.Level = resultSet.getInt("Level");            
            World.instance.sendLoginResponse(connectionHandler, true, null);
            
        } catch (SQLException e) {
            World.instance.sendLoginResponse(connectionHandler, false, "There was an error retrieving user data.");
            e.printStackTrace();
        }
    }

}
