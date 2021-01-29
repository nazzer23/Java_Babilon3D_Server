package Game.Handlers;

import Game.Objects.Map;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MapHandler {
    
    public static MapHandler instance;
    
    public ArrayList<Map> mapList = new ArrayList<>();
    
    public MapHandler() {
        instance = this;
        this.loadMaps();
    }

    private void loadMaps() {
        ResultSet rs = Database.instance.getResult("SELECT * FROM maps");
        while(true) {
            try {
                if (!rs.next()) break;

                Map m = new Map();
                m.mapName = rs.getString("Name");
                m.file = rs.getString("SceneName");
                m.maxPlayers = rs.getInt("MaxUsers");
                
                m.SpawnPosX = rs.getFloat("SpawnPosX");
                m.SpawnPosY = rs.getFloat("SpawnPosY");
                m.SpawnPosZ = rs.getFloat("SpawnPosZ");
                m.SpawnRotX = rs.getFloat("SpawnRotX");
                m.SpawnRotY = rs.getFloat("SpawnRotY");
                m.SpawnRotZ = rs.getFloat("SpawnRotZ");
                
                mapList.add(m);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        System.out.println("[MapHandler] " + mapList.size() + " maps have loaded.");

    }
    
    public Map getMap(String name) {
        for(Map m : mapList) {
            if(m.mapName.equals(name)) {
                return m;
            }
        }
        return null;
    }
    
    public boolean DoesMapExist(String name) {
        for(Map m : mapList) {
            if(m.mapName.equals(name)) {
                return true;
            }
        }
        return false;
    }

}
