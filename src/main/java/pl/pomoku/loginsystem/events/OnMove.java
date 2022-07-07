package pl.pomoku.loginsystem.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.pomoku.loginsystem.LoginSystem;

import java.util.UUID;

public class OnMove implements Listener {
    private final LoginSystem plugin;

    public OnMove(LoginSystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnPlayerMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        if(uuid != null){
            if(!OnJoin.LoggedIn.get(uuid)){
                e.setCancelled(true);
            }
        }
    }
}
