package Game.Handlers;

import Game.ConfigData;
import Game.Main;
import Game.Objects.Packet;
import Game.Objects.User;
import Game.World.World;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import org.json.JSONObject;

public class ConnectionHandler extends Thread implements Runnable {

    public int connectionID = 0;

    public Socket clientSocket = null;
    public Scanner socketIn;
    public PrintWriter socketOut;

    private boolean connected = false;

    private CommandHandler commandHandler;
    public User userObject;

    public ConnectionHandler(Socket client, int cID) {
        this.clientSocket = client;
        this.connectionID = cID;
        try {
            this.socketIn = new Scanner(clientSocket.getInputStream());
            this.socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
            
            World.instance.sendPacket(new Packet("authenticate", new JSONObject()), this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.commandHandler = new CommandHandler(this);
    }

    @Override
    public void run() {
        this.connected = true;
        this.readData();
        this.disconnect();
    }

    private void readData() {
        while(this.connected){
            while(!this.socketIn.hasNext())
                return;
            String msg = this.socketIn.nextLine();
            this.handleData(msg);
        }
    }

    public void handleData(String packet) {
        try {
            System.out.println("[RECV] "+packet);
            
            JSONObject packetStruct = new JSONObject(packet);
            Packet packetObj = new Packet(packetStruct.getString("Cmd"), packetStruct.getJSONObject("Params"));



            switch (packetObj.getCmd()) {
                default:
                    commandHandler.handleCommand(packetObj);
            }
        } catch(Exception e) {
            System.out.println("There was an error whilst executing command.");
            e.printStackTrace();
        }
    }

    public void disconnect() {
        this.connected = false;
        Main.clients.remove(this);
    }
}
