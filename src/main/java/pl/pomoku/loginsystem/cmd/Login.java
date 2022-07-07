package pl.pomoku.loginsystem.cmd;

import net.md_5.bungee.api.ChatMessageType;
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

public class Login implements CommandExecutor {
    private final LoginSystem plugin;


    public Login(LoginSystem plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("login")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            try{
                Players players_info =  this.plugin.getDatabase().findPlayerByUUID(p.getUniqueId().toString());

                if(players_info != null) {
                    if(Objects.equals(players_info.getPassword(), args[0])){
                        p.sendMessage(ChatColor.GREEN + "Zalogowales sie!");
                    }else {
                        p.sendMessage(ChatColor.RED + "Bledne haslo!");
                    }
                }else {
                    p.sendMessage(ChatColor.RED + "Nie masz konta!");
                }
            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }
        return true;
    }
}
