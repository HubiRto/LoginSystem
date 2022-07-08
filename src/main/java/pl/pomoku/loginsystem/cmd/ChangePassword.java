package pl.pomoku.loginsystem.cmd;

import com.google.common.hash.Hashing;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.pomoku.loginsystem.LoginSystem;
import pl.pomoku.loginsystem.events.OnJoin;
import pl.pomoku.loginsystem.models.Players;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Objects;

public class ChangePassword implements CommandExecutor {
    private final LoginSystem plugin;

    public ChangePassword(LoginSystem plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("changepassword")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            try{
                Players players_info = this.plugin.getDatabase().findPlayerByUUID(p.getUniqueId().toString());
                if(players_info != null){
                    if (OnJoin.LoggedIn.get(p.getUniqueId())) {
                        if(args.length == 3){
                            if(args[1].equals(args[2])){
                                String sha256hex_old = Hashing.sha256().hashString(args[0], StandardCharsets.UTF_8).toString();
                                if(sha256hex_old.equals(players_info.getPassword())){
                                    String sha256hex = Hashing.sha256().hashString(args[1], StandardCharsets.UTF_8).toString();
                                    players_info.setPassword(sha256hex);
                                    this.plugin.getDatabase().updatePlayers(players_info);
                                    p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.GREEN + "Poprawnie zmieniono haslo.");
                                }else {
                                    p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "Haslo jest nieprawidlowe.");
                                }
                            }else {
                                p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "Nowe hasla nie sa identyczne.");
                            }
                        }else {
                            p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "Poprawne uzycie komendy: " + ChatColor.GRAY + "/changepassword <stare haslo> <nowe haslo> <powtorz nowe haslo>" + ChatColor.RED + ".");
                        }
                    }else {
                        p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "W tej chwili nie mozesz uzyc tej komendy!");
                    }
                }else {
                    p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "W tej chwili nie mozesz uzyc tej komendy!");
                }
            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }
        return true;
    }
}
