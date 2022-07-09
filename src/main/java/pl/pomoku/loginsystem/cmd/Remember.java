package pl.pomoku.loginsystem.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.pomoku.loginsystem.LoginSystem;
import pl.pomoku.loginsystem.events.OnJoin;
import pl.pomoku.loginsystem.models.Players;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class Remember implements CommandExecutor {
    private final LoginSystem plugin;

    public Remember(LoginSystem plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("remember")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            try{
                Players players_info = this.plugin.getDatabase().findPlayerByUUID(p.getUniqueId().toString());
                if(players_info != null) {
                    if (OnJoin.LoggedIn.get(p.getUniqueId())) {
                        players_info.setRem_password(true);
                        Date dt = new Date();
                        Date to = new Date(dt.getTime() + (1000*60*60*24*3));
                        //LocalDateTime.from(dt.toInstant()).plusDays(1);
                        players_info.setExpiry_date(to);
                        this.plugin.getDatabase().updatePlayers(players_info);
                        p.sendMessage(ChatColor.GREEN + "Wlaczyles automatyczne logowanie na 3 dni.");
                    }
                }
            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }
        return true;
    }
}
