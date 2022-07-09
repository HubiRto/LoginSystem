package pl.pomoku.loginsystem.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.pomoku.loginsystem.LoginSystem;
import pl.pomoku.loginsystem.models.Players;

import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.util.*;

public class OnJoin implements Listener {
    private final LoginSystem plugin;

    public OnJoin(LoginSystem plugin) {
        this.plugin = plugin;
    }

    public static Map<UUID, Boolean> LoggedIn = new HashMap<>();

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        LoggedIn.put(uuid, false);
        e.joinMessage(null);

        try{
            Players players_info =  this.plugin.getDatabase().findPlayerByUUID(p.getUniqueId().toString());

            if(players_info == null){
                p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "Zarejestruj sie, uzywajac komendy " + ChatColor.GRAY + "/register <twoje haslo> <powtorz haslo>" + ChatColor.RED + ".");
            }else {
                String s = ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "Zaloguj sie, uzywajac komendy " + ChatColor.GRAY + "/login <twoje haslo>" + ChatColor.RED + ".";
                if(players_info.isRem_password()){
                    if(!isSameDay(new Date(), players_info.getExpiry_date())) {
                        if(Objects.equals(players_info.getIp(), Objects.requireNonNull(p.getAddress()).getHostName())) {
                            LoggedIn.put(uuid, true);
                            p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.GREEN + "Zostales automatycznie zalogowany.");
                        }else {
                            players_info.setRem_password(false);
                            this.plugin.getDatabase().updatePlayers(players_info);
                            p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "Zostales wylogowany, poniewaz twoj adres ip sie nie zgadzal.");
                            p.sendMessage(s);
                        }
                    }else {
                        players_info.setRem_password(false);
                        this.plugin.getDatabase().updatePlayers(players_info);
                        p.sendMessage(s);
                    }
                }else {
                    p.sendMessage(s);
                }
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
    public static boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date1).equals(fmt.format(date2));
    }
}
