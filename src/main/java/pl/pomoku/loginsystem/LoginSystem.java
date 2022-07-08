package pl.pomoku.loginsystem;

import org.bukkit.ChatColor;
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
        saveDefaultConfig();
        this.database = new Database(
                getConfig().getString("database.host"),
                getConfig().getString("database.port"),
                getConfig().getString("database.user"),
                getConfig().getString("database.password"),
                getConfig().getString("database.database_name"),
                getConfig().getString("database.type"));
        try{
            database.initializeDatabase();
        }catch (SQLException ex){
            getServer().getConsoleSender().sendMessage(ChatColor.RED + " >> Unable to connect to database and create tables.");
            ex.printStackTrace();
        }
        StartMessage();
        CommandsLoad();
        EventsLoad();
    }
    @Override
    public void onDisable(){
        EndMessage();
    }

    private void EventsLoad() {
        getServer().getPluginManager().registerEvents(new OnJoin(this), this);
        getServer().getPluginManager().registerEvents(new OnMove(this), this);
        getServer().getPluginManager().registerEvents(new OnQuit(this), this);
    }

    private void CommandsLoad() {
        new Register(this);
        new Login(this);
        new Email(this);
        new ChangePassword(this);
        new Logout(this);
        new Help(this);
    }

    private void StartMessage() {
        getServer().getConsoleSender().sendMessage(" ");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "=============================");
        getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "LoginSystem v1.0 by" + ChatColor.DARK_PURPLE + " HubiRto");
        getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "Action:" + ChatColor.GREEN + " Enabling" + ChatColor.GRAY + "...");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "=============================");
        getServer().getConsoleSender().sendMessage(" ");
    }
    public void EndMessage() {
        getServer().getConsoleSender().sendMessage(" ");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "=============================");
        getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "LoginSystem v1.0 by" + ChatColor.DARK_PURPLE + " HubiRto");
        getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "Action:" + ChatColor.RED + " Disabling" + ChatColor.GRAY + "...");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "=============================");
        getServer().getConsoleSender().sendMessage(" ");
    }

    public Database getDatabase() {
        return database;
    }
}
