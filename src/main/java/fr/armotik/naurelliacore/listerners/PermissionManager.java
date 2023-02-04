package fr.armotik.naurelliacore.listerners;

import fr.armotik.naurelliacore.Louise;
import fr.armotik.naurelliacore.Naurelliacore;
import fr.armotik.naurelliacore.utiles.Database;
import fr.armotik.naurelliacore.utiles.ExceptionsManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.permissions.PermissionAttachment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PermissionManager implements Listener {

    private static final Map<UUID, PermissionAttachment> playerPermissions = new HashMap<>();
    private static final Map<UUID, PermissionAttachment> staffPermissions = new HashMap<>();
    private static final Logger logger = Logger.getLogger(PermissionManager.class.getName());

    public static Map<UUID, PermissionAttachment> getPlayerPermissions() {
        return playerPermissions;
    }

    public static Map<UUID, PermissionAttachment> getStaffPermissions() {
        return staffPermissions;
    }

    /**
     * Setting up default permissions
     * @param player player
     */
    public static void setupPermissions(Player player) {
        PermissionAttachment attachment = player.addAttachment(Naurelliacore.getPlugin());
        playerPermissions.put(player.getUniqueId(), attachment);
        staffPermissions.put(player.getUniqueId(), attachment);
    }

    /**
     * Read player's permission when the player join the server
     * @param player player
     */
    public static void readPermissions(Player player) {

        ResultSet res = Database.executeQuery("SELECT playerPermission, staffPermission FROM igPermissions WHERE uuid='" + player.getUniqueId() + "'");
        PermissionAttachment playerAttachment = playerPermissions.get(player.getUniqueId());
        PermissionAttachment staffAttachment = staffPermissions.get(player.getUniqueId());

        if (res == null) {
            logger.log(Level.WARNING, "[NaurelliaCore] -> PermissionManager : readPermissions ERROR - res == null");
            return;
        }

        try {

            if (!res.next()) {
                logger.log(Level.WARNING, "[NaurelliaCore] -> PermissionManager : readPermissions ERROR - res is empty");
            } else {

                String playerRank = res.getString("playerpermission");
                String staffRank = res.getString("staffpermission");

                if (playerRank != null) {

                    for (String permission : Naurelliacore.getPlugin().getConfig().getStringList("PlayerRanks." + playerRank + ".permissions")) {

                        playerAttachment.setPermission(permission, true);
                    }
                }

                if (staffRank != null) {

                    for (String permission : Naurelliacore.getPlugin().getConfig().getStringList("StaffRanks." + staffRank + ".permissions")) {

                        staffAttachment.setPermission(permission, true);
                    }
                }
            }
        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
        }

        Database.close();
    }

    /**
     * Remove a permission from a sender
     * @param sender who executed the command
     * @param target who will lose the permissions
     * @param tag permission tag
     */
    public static void removePermission(CommandSender sender, OfflinePlayer target, String tag) {

        if (tag.toUpperCase(Locale.ENGLISH).equals("PLAYERRANK")) {

            int res1 = Database.executeUpdate("UPDATE igPermissions SET playerPermission=" + null + " WHERE uuid='" + target.getUniqueId() + "';");

            if (res1 > 0) {

                readPermissions(Objects.requireNonNull(target.getPlayer()));
                sender.sendMessage(Louise.getName() + "§aSuccessfully removed sender rank to " + target.getName());
            } else {

                sender.sendMessage(Louise.commandError());
                logger.log(Level.WARNING, "[NaurelliaCore] -> PermissionManager : removePermission ERROR - res 1 <= 0");
            }

            Database.close();
        } else if (tag.toUpperCase(Locale.ENGLISH).equals("STAFFRANK")) {

            int res2 = Database.executeUpdate("UPDATE igPermissions SET staffPermission=" + null + " WHERE uuid='" + target.getUniqueId() + "';");


            if (res2 > 0) {

                readPermissions(Objects.requireNonNull(target.getPlayer()));
                sender.sendMessage(Louise.getName() + "§aSuccessfully removed staff rank to " + target.getName());
            } else {

                sender.sendMessage(Louise.commandError());
                logger.log(Level.WARNING, "[NaurelliaCore] -> PermissionManager : removePermission ERROR - res 2 <= 0");
            }

            Database.close();
        }
    }

    /**
     * Add permissions to a sender
     * @param sender who executed the command
     * @param target who will get the permissions
     * @param tag permission tag
     * @param rank rank
     */
    public static void addPermission(CommandSender sender, OfflinePlayer target, String tag, String rank) {

        if (tag.toUpperCase(Locale.ENGLISH).equals("PLAYERRANK")) {

            int res1 = Database.executeUpdate("UPDATE igPermissions SET playerPermission='" + rank.toUpperCase(Locale.ENGLISH) + "' WHERE uuid='" + target.getUniqueId() + "';");

            if (res1 > 0) {

                readPermissions(Objects.requireNonNull(target.getPlayer()));
                sender.sendMessage(Louise.getName() + "§aSuccessfully added sender rank " + rank + " to " + target.getName());
            } else {

                sender.sendMessage(Louise.commandError());
                logger.log(Level.WARNING, "[NaurelliaCore] -> PermissionManager : addPermission ERROR - res 1 <= 0");
            }

        } else if (tag.toUpperCase(Locale.ENGLISH).equals("STAFFRANK")) {

            int res1 = Database.executeUpdate("UPDATE igPermissions SET staffPermission='" + rank.toUpperCase(Locale.ENGLISH) + "' WHERE uuid='" + target.getUniqueId() + "';");

            if (res1 > 0) {


                readPermissions(Objects.requireNonNull(target.getPlayer()));
                sender.sendMessage(Louise.getName() + "§aSuccessfully added  staff rank " + rank + " to " + target.getName());
            } else {

                System.out.println("test5");

                sender.sendMessage(Louise.commandError());
                logger.log(Level.WARNING, "[NaurelliaCore] -> PermissionManager : addPermission ERROR - res 2 <= 0");
            }
        }
    }
}
