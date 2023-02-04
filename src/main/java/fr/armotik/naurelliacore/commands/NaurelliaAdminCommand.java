package fr.armotik.naurelliacore.commands;

import fr.armotik.naurelliacore.Louise;
import fr.armotik.naurelliacore.listerners.PermissionManager;
import fr.armotik.naurelliacore.utiles.Database;
import fr.armotik.naurelliacore.utiles.ExceptionsManager;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class NaurelliaAdminCommand implements CommandExecutor {

    private final List<String> playerRankList = new ArrayList<>();

    private final List<String> staffRankList = new ArrayList<>();

    public NaurelliaAdminCommand() {
        playerRankList.add(null);
        playerRankList.add("HERO");
        playerRankList.add("LEGEND");
        playerRankList.add("PREMIUM");

        staffRankList.add(null);
        staffRankList.add("TEST");
        staffRankList.add("HELPER");
        staffRankList.add("MOD");
        staffRankList.add("ADMIN");
    }

    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        System.out.println(args.length);
        System.out.println(Arrays.toString(args));

        if (sender instanceof ConsoleCommandSender || sender.hasPermission("naurellia.admin")) {


            TextComponent msg = new TextComponent(Louise.wrongCommand());
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/naurelliaadmin <add|remove> <player> <playerRank|staffRank> <rank>")));

            if (args.length < 3) {
                sender.spigot().sendMessage(msg);
                return false;
            }

            ResultSet res = Database.executeQuery("SELECT uuid FROM usersIG WHERE username= '" + args[1] + "'");

            if (res == null) {
                sender.sendMessage(Louise.playerNotFound());
                Database.close();
                return false;
            }

            try {

                if (res.next()) {

                    OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(res.getString("uuid")));

                    if ((!args[0].equalsIgnoreCase("ADD")) && (!args[0].equalsIgnoreCase("REMOVE")) || (!playerRankList.contains(args[3].toUpperCase(Locale.ENGLISH))) && (!staffRankList.contains(args[3].toUpperCase(Locale.ENGLISH)))) {

                        sender.spigot().sendMessage(msg);
                        Database.close();
                        return false;
                    }

                    switch (args[0].toUpperCase(Locale.ENGLISH)) {

                        case "ADD"-> PermissionManager.addPermission(sender, target, args[2], args[3]);
                        case "REMOVE" -> PermissionManager.removePermission(sender, target, args[2]);
                        default -> sender.spigot().sendMessage(msg);
                    }

                    Database.close();
                    return true;
                }
            } catch (SQLException e) {
                ExceptionsManager.sqlExceptionLog(e);
                Database.close();
            }
        }

        return false;
    }
}
