package pl.pomoku.loginsystem;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class LoginSystem extends JavaPlugin {

    @Override
    public void onEnable() {

        String url = "jdbc:mysql://localhost/users";
        String user = "root";
        String password = "";

        Connection con = null;

        try{
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the Users Database.");
        }catch (SQLException e){
            System.out.println("Unable to connect to the Users Database.");
            e.printStackTrace();
        }
        try{
            Statement statement = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXIST players(uuid TEXT, player_name TEXT, ip TEXT, email TEXT, email_confirm BOOLEAN, password TEXT, sign_in_date DATE, ban BOOLEAN, last_x DOUBLE, last_y DOUBLE, last_z DOUBLE)";
            statement.execute(sql);
            statement.close();
            con.close();
            System.out.println("Create the players table in the database.");
        }catch (SQLException e){
            System.out.println("Unable to create the players table in the database.");
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
