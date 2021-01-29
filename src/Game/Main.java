package Game;

import Game.Exceptions.DatabaseSQLException;
import Game.Handlers.ConnectionHandler;
import Game.Handlers.Database;
import Game.World.World;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    private static Database db;
    private static World world;
    public static ArrayList<ConnectionHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        ConfigData.initConfig();
        try {
            db = new Database();
            db.connect();
        } catch (DatabaseSQLException e) {
            e.printStackTrace();
        }

        world = new World();

        Database.instance.runQuery("UPDATE servers SET Online=1, Count=0 WHERE Name='" + ConfigData.getString("server_name") + "'");
        try {
            ServerSocket ss = new ServerSocket(ConfigData.getInt("server_port"));
            System.out.println("Listening on "+ ConfigData.getInt("server_port"));
            int c = 0;
            while(true) {
                Socket cs = ss.accept();
                ConnectionHandler ch = new ConnectionHandler(cs, c++);
                clients.add(ch);
                ch.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
