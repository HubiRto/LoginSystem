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
import java.util.Objects;

public class Email implements CommandExecutor {
    private final LoginSystem plugin;

    public Email(LoginSystem plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("email")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            try {
                Players players_info = this.plugin.getDatabase().findPlayerByUUID(p.getUniqueId().toString());

                if (players_info != null) {
                    if (OnJoin.LoggedIn.get(p.getUniqueId())) {
                        if(!players_info.isEmail_confirm()) {
                            if (args.length == 1) {
                                if (args[0].contains("@")) {
                                    if (args[0].contains(".")) {
                                        players_info.setEmail(args[0]);
                                        players_info.setEmail_confirm(true);
                                        this.plugin.getDatabase().updatePlayers(players_info);
                                        p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.GREEN + "Adres email " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + "zostal przypisany do konta.");
                                    }else {
                                        p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "Podany adres email wyglada na nieprawidlowy.");
                                    }
                                }else {
                                    p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "Podany adres email wyglada na nieprawidlowy.");
                                }
                            }else {
                                p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "Poprawne uzycie komendy:" + ChatColor.GRAY + " /email <email>" + ChatColor.RED + ".");
                            }
                        }else {
                            p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "L" + ChatColor.YELLOW + "S" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + ">> " + ChatColor.RED + "Email juz zostal podany!");
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
