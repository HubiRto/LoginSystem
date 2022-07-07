package pl.pomoku.loginsystem.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.pomoku.loginsystem.LoginSystem;
import pl.pomoku.loginsystem.models.Players;

import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

public class Register implements CommandExecutor {
    private final LoginSystem plugin;


    public Register(LoginSystem plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("register")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            try{
                Players players_info =  this.plugin.getDatabase().findPlayerByUUID(p.getUniqueId().toString());

                if(players_info == null) {
                    players_info = new Players(p.getUniqueId().toString(), p.getDisplayName(), p.getAddress().getHostName(), null, false, args[0], new Date(), false, 0, 0, 0);
                    this.plugin.getDatabase().createPlayers(players_info);
                }else {
                    p.sendMessage(ChatColor.RED + "Masz juz konto!");
                }
            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }
        return true;
    }
}
