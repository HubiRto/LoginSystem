package pl.pomoku.loginsystem.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.pomoku.loginsystem.LoginSystem;
import pl.pomoku.loginsystem.models.Players;

import java.sql.SQLException;
import java.util.UUID;

public class OnQuit implements Listener {
    private final LoginSystem plugin;

    public OnQuit(LoginSystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnPlayerQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        try {
            Players players_info = this.plugin.getDatabase().findPlayerByUUID(p.getUniqueId().toString());
            if(players_info != null){
                if(OnJoin.LoggedIn.get(uuid)) {
                    players_info.setLast_x(p.getLocation().getX());
                    players_info.setLast_y(p.getLocation().getY());
                    players_info.setLast_z(p.getLocation().getZ());
                    this.plugin.getDatabase().updatePlayers(players_info);
                }
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
    }
}
