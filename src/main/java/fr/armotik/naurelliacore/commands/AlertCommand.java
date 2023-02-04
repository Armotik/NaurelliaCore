package fr.armotik.naurelliacore.commands;

import fr.armotik.naurelliacore.Louise;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AlertCommand implements CommandExecutor {
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

        if (!sender.hasPermission("naurellia.admin")) {
            sender.sendMessage(Louise.permissionMissing());
        }

        if (args.length == 0) {

            TextComponent msg = new TextComponent(Louise.wrongCommand());
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§c/alert <message>")));
            sender.spigot().sendMessage(msg);
            return false;
        }

        StringBuilder bc = new StringBuilder();

        for (String part : args) {

            bc.append(part).append(" ");
        }

        Bukkit.broadcastMessage("§c[Alert] : §6" + bc);

        return true;
    }
}
