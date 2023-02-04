package fr.armotik.naurelliacore.commands;

import fr.armotik.naurelliacore.Louise;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EarthCommand implements CommandExecutor {
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

        if (!(sender instanceof Player)) return false;

        Player player = ((Player) sender).getPlayer();

        assert player != null;

        if (player.getWorld().getName().equalsIgnoreCase("world")) {

            player.sendMessage(Louise.getName() + "§aTeleporting to the Earth ...");
            player.teleport(new Location(Bukkit.getWorld("Earth"), 7771, 72, -7771));
            return true;
        }

        player.sendMessage(Louise.getName() + "§cYou already joined the Earth ...");
        return false;
    }
}
