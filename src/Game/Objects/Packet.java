package Game.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

public class Packet {
    
    private String Command;
    private JSONObject Params;
    public JSONObject object;
    
    public Packet(String Command, JSONObject Params) {
        this.Command = Command;
        this.Params = Params;
        
        JSONObject _local = new JSONObject();
        _local.put("Cmd", this.Command);
        if(Params.length() > 0) {
            _local.put("Params", this.Params);
        }
        this.object = _local;
    }
    
    public String getCmd() {
        return this.Command;
    }
    
    public JSONObject getParams() {
        return Params;
    }
    
}
