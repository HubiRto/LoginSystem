package pl.pomoku.loginsystem.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import pl.pomoku.loginsystem.LoginSystem;
import pl.pomoku.loginsystem.events.OnJoin;
import pl.pomoku.loginsystem.models.Players;

import java.sql.SQLException;
import java.util.Objects;

public class Logout implements CommandExecutor {
    private final LoginSystem plugin;

    public Logout(LoginSystem plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("logout")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            try{
                Players players_info = this.plugin.getDatabase().findPlayerByUUID(p.getUniqueId().toString());
                if(players_info != null){
                    if (OnJoin.LoggedIn.get(p.getUniqueId())) {
                        if (args.length == 0) {
                            p.kickPlayer(ChatColor.GREEN + "Pomyslnie wylogowano z konta.");
                        }else {
                            p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "Poprawne uzycie komendy:" + ChatColor.GRAY + " /logout" + ChatColor.RED + ".");
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
