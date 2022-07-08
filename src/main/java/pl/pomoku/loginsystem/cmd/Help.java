package pl.pomoku.loginsystem.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.pomoku.loginsystem.LoginSystem;
import pl.pomoku.loginsystem.events.OnJoin;

import java.util.Objects;

public class Help implements CommandExecutor {
    private final LoginSystem plugin;

    public Help(LoginSystem plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("pomoc")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (OnJoin.LoggedIn.get(p.getUniqueId())) {
                p.sendMessage(" ");
                p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "+" + ChatColor.DARK_GRAY + "]" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.RESET + "" + ChatColor.DARK_GRAY + "[ " + ChatColor.AQUA + "LOGIN SYSTEM" + ChatColor.DARK_GRAY + " ]" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.RESET + "" + ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "+" + ChatColor.DARK_GRAY + "]");
                p.sendMessage(" ");
                p.sendMessage(ChatColor.DARK_GRAY + " >> " + ChatColor.YELLOW + "/pomoc");
                p.sendMessage(ChatColor.DARK_GRAY + " >> " + ChatColor.YELLOW + "/login " + ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "twoje haslo" + ChatColor.DARK_GRAY + "]");
                p.sendMessage(ChatColor.DARK_GRAY + " >> " + ChatColor.YELLOW + "/register " + ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "twoje haslo" + ChatColor.DARK_GRAY + "] [" + ChatColor.GOLD + "powtorz haslo" + ChatColor.DARK_GRAY + "]");
                p.sendMessage(ChatColor.DARK_GRAY + " >> " + ChatColor.YELLOW + "/changepassword " + ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "stare haslo" + ChatColor.DARK_GRAY + "]");
                p.sendMessage(ChatColor.DARK_GRAY + " >> [" + ChatColor.GOLD + "nowe haslo" + ChatColor.DARK_GRAY + "] [" + ChatColor.GOLD + "powtorz nowe haslo" + ChatColor.DARK_GRAY + "]");
                p.sendMessage(ChatColor.DARK_GRAY + " >> " + ChatColor.YELLOW + "/logout");
                p.sendMessage(ChatColor.DARK_GRAY + " >> " + ChatColor.YELLOW + "/email " + ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "email" + ChatColor.DARK_GRAY + "]");
                p.sendMessage(" ");
                p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "+" + ChatColor.DARK_GRAY + "]" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.RESET + "" + ChatColor.DARK_GRAY + "[ " + ChatColor.AQUA + "LOGIN SYSTEM" + ChatColor.DARK_GRAY + " ]" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.RESET + "" + ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "+" + ChatColor.DARK_GRAY + "]");
                p.sendMessage(" ");
            }
        }
        return true;
    }
}
