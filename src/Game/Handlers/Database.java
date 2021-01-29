package Game.Handlers;

import Game.ConfigData;
import Game.Exceptions.DatabaseSQLException;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import java.sql.*;
import java.util.Properties;

public class Database {
    public static Database instance;

    protected Connection con;
    protected Statement st;
    protected Properties sqlData = new Properties();

    public Database() {
        super();
        instance = this;
    }

    public void connect() throws DatabaseSQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://"+ ConfigData.getString("mysql_host")+":"+ConfigData.getInt("mysql_port")+"/"+ConfigData.getString("mysql_db"), ConfigData.getString("mysql_user"), ConfigData.getString("mysql_pass"));

            if(!con.equals(null)) {
                System.out.println("[DatabaseHandler] Successfully connected to the database.");
            }

            st = con.createStatement();
        } catch (CommunicationsException e) {
            throw new DatabaseSQLException("Database connection failed.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void runQuery(String query){
        try {
            st.executeUpdate(query);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResult(String query){
        ResultSet rs = null;
        try {
            rs = st.executeQuery(query);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
}
