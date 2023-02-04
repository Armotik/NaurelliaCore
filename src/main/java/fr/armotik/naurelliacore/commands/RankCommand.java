package fr.armotik.naurelliacore.commands;

import fr.armotik.naurelliacore.Louise;
import fr.armotik.naurelliacore.utiles.Database;
import fr.armotik.naurelliacore.utiles.ExceptionsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RankCommand implements CommandExecutor {
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

        if (sender instanceof Player) {

            Player player = ((Player) sender).getPlayer();

            assert player != null;
            ResultSet res = Database.executeQuery("SELECT playerPermission, staffPermission FROM igPermissions WHERE uuid='" + player.getUniqueId() + "'");

            try {

                if (res == null) {

                    player.sendMessage(Louise.getName() + "§cYou don't have rank");
                    return true;
                } else if (res.next()) {

                    String pp = res.getString("playerPermission");
                    String sp = res.getString("staffPermission");

                    if ((pp == null) && (sp == null)) {
                        player.sendMessage(Louise.getName() + "§cYou don't have rank");
                        return true;

                    } else if  ((pp != null) && (sp == null)) {

                        player.sendMessage(Louise.getName() + "§cHere is your rank\n§aPlayer Rank §8: §f" +  pp);
                        return true;
                    } else if (pp == null) {

                        player.sendMessage(Louise.getName() + "§cHere are your ranks\n§aPlayer Rank §8: §fNo rank\n§aStaff Rank §8: §f" + sp);
                        return true;
                    } else {

                        player.sendMessage(Louise.getName() + "§cHere are your ranks\n§aPlayer Rank §8: §f" + pp + "\n§aStaff Rank §8: §f" + sp);
                        return true;
                    }

                }

                Database.close();

            } catch (SQLException e) {
                ExceptionsManager.sqlExceptionLog(e);
                Database.close();
                return false;
            }
        }

        return false;
    }
}
