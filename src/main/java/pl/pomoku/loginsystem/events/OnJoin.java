package pl.pomoku.loginsystem.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import pl.pomoku.loginsystem.LoginSystem;
import pl.pomoku.loginsystem.models.Players;

import javax.swing.text.LayoutQueue;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OnJoin implements Listener {
    private final LoginSystem plugin;

    public OnJoin(LoginSystem plugin) {
        this.plugin = plugin;
    }

    public static Map<UUID, Boolean> LoggedIn = new HashMap<UUID, Boolean>();

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        LoggedIn.put(uuid, false);
        e.setJoinMessage(null);

        try{
            Players players_info =  this.plugin.getDatabase().findPlayerByUUID(p.getUniqueId().toString());

            if(players_info == null){
                p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "Zarejestruj sie, uzywajac komendy " + ChatColor.GRAY + "/register <twoje haslo> <powtorz haslo>" + ChatColor.RED + ".");
            }else {
                p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "Zaloguj sie, uzywajac komendy " + ChatColor.GRAY + "/login <twoje haslo>" + ChatColor.RED + ".");
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        new BukkitRunnable(){
            @Override
            public void run(){
                if (!OnJoin.LoggedIn.get(uuid)) {
                    p.kickPlayer(ChatColor.RED + "Twoj czas na zalogowanie sie minal.");
                }
            }
        }.runTaskLater(plugin, 20 * 30);
    }
}
