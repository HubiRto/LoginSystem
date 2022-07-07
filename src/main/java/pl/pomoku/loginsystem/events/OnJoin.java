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
                players_info = new Players(p.getUniqueId().toString(), p.getDisplayName(), p.getAddress().getHostName(), null, false, "", new Date(), false, 0, 0, 0);
                this.plugin.getDatabase().createPlayers(players_info);
            }else {
                players_info.setIp(players_info.getIp() + "a");
                this.plugin.getDatabase().updatePlayers(players_info);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
    }
}
