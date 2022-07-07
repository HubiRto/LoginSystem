package pl.pomoku.loginsystem;

import org.bukkit.plugin.java.JavaPlugin;
import pl.pomoku.loginsystem.cmd.Login;
import pl.pomoku.loginsystem.cmd.Register;
import pl.pomoku.loginsystem.db.Database;
import pl.pomoku.loginsystem.events.OnJoin;

import java.sql.SQLException;

public final class LoginSystem extends JavaPlugin {

    Database database;
    @Override
    public void onEnable() {
        try{
            this.database = new Database();
            database.initializeDatabase();
        }catch (SQLException ex){
            System.out.println("Unable to connect to database and create tables.");
            ex.printStackTrace();
        }

        new Register(this);
        new Login(this);

        getServer().getPluginManager().registerEvents(new OnJoin(this), this);
    }

    public Database getDatabase() {
        return database;
    }
}
