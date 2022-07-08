package pl.pomoku.loginsystem;

import org.bukkit.plugin.java.JavaPlugin;
import pl.pomoku.loginsystem.cmd.*;
import pl.pomoku.loginsystem.db.Database;
import pl.pomoku.loginsystem.events.OnJoin;
import pl.pomoku.loginsystem.events.OnMove;
import pl.pomoku.loginsystem.events.OnQuit;

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
        new Email(this);
        new ChangePassword(this);
        new Logout(this);

        getServer().getPluginManager().registerEvents(new OnJoin(this), this);
        getServer().getPluginManager().registerEvents(new OnMove(this), this);
        getServer().getPluginManager().registerEvents(new OnQuit(this), this);
    }

    public Database getDatabase() {
        return database;
    }
}
