package pl.pomoku.loginsystem.cmd;

import com.google.common.hash.Hashing;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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

public class Register implements CommandExecutor {
    private final LoginSystem plugin;


    public Register(LoginSystem plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("register")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            try{
                Players players_info =  this.plugin.getDatabase().findPlayerByUUID(p.getUniqueId().toString());

                if(players_info == null) {
                    if(args.length == 2) {
                        if (args[0].equals(args[1])) {
                            String sha256hex = Hashing.sha256().hashString(args[0], StandardCharsets.UTF_8).toString();
                            players_info = new Players(p.getUniqueId().toString(), p.getDisplayName(), Objects.requireNonNull(p.getAddress()).getHostName(), null, false, sha256hex, new Date(), false, 0, 0, 0);
                            this.plugin.getDatabase().createPlayers(players_info);
                            UUID uuid = p.getUniqueId();
                            OnJoin.LoggedIn.put(uuid, true);
                            
                            p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.GREEN + "Zostales pomyslnie zarejestrowany.");
                            p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.GREEN + "Aby dokonczyc rejestracje, podaj swoj adres email " + ChatColor.GRAY + "/email <email>" + ChatColor.GREEN + ".");
                        } else {
                            p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "Podane hasla nie zgadzaja sie.");
                        }
                    }else {
                        p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "Prawidlowe uzycie komendy: " + ChatColor.GRAY + "/register <twoje haslo> <powtorz haslo>" + ChatColor.RED + ".");
                    }
                }else {
                    p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "To konto jest juz zarejestrowane, zaloguj sie.");
                }
            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }
        return true;
    }
}
