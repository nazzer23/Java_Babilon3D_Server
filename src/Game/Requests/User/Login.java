package Game.Requests.User;

import Game.Handlers.ConnectionHandler;
import Game.Handlers.Database;
import Game.Interfaces.ICommand;
import Game.Objects.Packet;
import Game.Objects.User;
import Game.World.World;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Login implements ICommand {

    @Override
    public void process(Packet data, ConnectionHandler user) {
        int CharacterID = data.getParams().getInt("CharacterID");
        int PlayerID = data.getParams().getInt("PlayerID");
        
        String Token = data.getParams().getString("Token");

        ResultSet rs = Database.instance.getResult("SELECT users_characters.*, users.Password FROM users_characters INNER JOIN users ON users_characters.PlayerID = users.id WHERE PlayerID='"+PlayerID+"' AND CharID='"+CharacterID+"' AND Password='"+Token+"'");
        try {
            if (rs.next()) {
                user.userObject = new User(rs.getInt("PlayerID"), rs.getInt("CharID"), user);
                user.userObject.handleUserLogin(rs, data);
            } else {
                World.instance.sendLoginResponse(user, false, "Something went wrong when authenticating.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
