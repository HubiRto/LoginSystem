package pl.pomoku.loginsystem.cmd;

import com.google.common.hash.Hashing;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import pl.pomoku.loginsystem.LoginSystem;
import pl.pomoku.loginsystem.events.OnJoin;
import pl.pomoku.loginsystem.models.Players;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Login implements CommandExecutor {
    private final LoginSystem plugin;


    public Login(LoginSystem plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("login")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            UUID uuid = p.getUniqueId();
            try{
                Players players_info =  this.plugin.getDatabase().findPlayerByUUID(p.getUniqueId().toString());

                if(args.length == 1) {
                    if (players_info != null) {
                        if (!OnJoin.LoggedIn.get(uuid)) {
                            String sha256hex = Hashing.sha256().hashString(args[0], StandardCharsets.UTF_8).toString();
                            if (Objects.equals(players_info.getPassword(), sha256hex)) {

                                Location location = p.getLocation();
                                location.setX(players_info.getLast_x());
                                location.setY(players_info.getLast_y());
                                location.setZ(players_info.getLast_z());
                                if(location != null) {
                                    p.teleport(location);
                                }

                                p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.GREEN + "Zostales pomyslnie zalogowany.");
                                p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.GREEN + "Nacisnij klawisz " + ChatColor.GRAY + "SHIFT" + ChatColor.GREEN + ", aby zapamietac logowanie na " + ChatColor.GRAY + "3 dni" + ChatColor.GREEN + ".");

                                OnJoin.LoggedIn.put(uuid, true);
                            } else {
                                p.kickPlayer(ChatColor.RED + "Haslo jest nieprawidlowe.");
                            }
                        } else {
                            p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.GREEN + "Jestes juz zalogowany.");
                        }
                    } else {
                        p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "To konto nie jest zarejestrowane, utworz je.");
                    }
                }else {
                    p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "Prawidlowe uzycie komendy: " + ChatColor.GRAY + "/login <twoje haslo>" + ChatColor.RED + ".");
                }
            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }
        return true;
    }
}
