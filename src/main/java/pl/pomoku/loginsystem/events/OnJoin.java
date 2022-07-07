package pl.pomoku.loginsystem.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.pomoku.loginsystem.LoginSystem;
import pl.pomoku.loginsystem.models.Players;

import java.sql.SQLException;
import java.util.Date;

public class OnJoin implements Listener {
    private final LoginSystem plugin;

    public OnJoin(LoginSystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();

        try{
            Players players_info =  this.plugin.getDatabase().findPlayerByUUID(p.getUniqueId().toString());

            if(players_info == null){
                p.sendMessage("/register haslo powtorz_haslo");
            }else {
                p.sendMessage("/login haslo");
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
    }
}
