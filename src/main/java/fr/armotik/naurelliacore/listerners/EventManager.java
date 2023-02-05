package fr.armotik.naurelliacore.listerners;

import fr.armotik.naurelliacore.Louise;
import fr.armotik.naurelliacore.utiles.Database;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventManager implements Listener {

    private final Logger logger = Logger.getLogger(EventManager.class.getName());
    private final World world = Bukkit.getWorld("world");

    /**
     * When a player join the server
     * @param event Player Join Event
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        LocalDate date = LocalDate.now();

        player.teleport(new Location(world, 0, 2, 0));

        PermissionManager.setupPermissions(player);

        player.setPlayerListHeader("\n §6§lNaurelliaCraft §7- §eMinecraft EarthRP  \n");
        player.setPlayerListFooter("\n §a§nnaurelliacraft.com \n");

        /*
        If the player didn't play before, add it in the databases
         */
        if (!player.hasPlayedBefore()) {

            int req1 = Database.executeUpdate("INSERT INTO usersIG(uuid, username, joinedAt, isOnline) VALUES ('"
                    + player.getUniqueId() + "','" + player.getName() + "','" + date + "','1');");

            if (req1 <= 0) {
                logger.log(Level.WARNING, "[NaurelliaCore] -> EventManager : PlayerJoinEvent ERROR - req1 <= 0");
            } else {

                logger.log(Level.INFO, "[NaurelliaCore] -> EventManager : PlayerJoinEvent INFO - " + player.getName() + " added to usersIG");
            }

            Database.close();

            int req2 = Database.executeUpdate("INSERT INTO igPermissions(uuid, playerPermission, staffPermission) VALUES ('"
                    + player.getUniqueId() + "'," + null + "," + null + ");");

            if (req2 <= 0) {
                logger.log(Level.WARNING, "[NaurelliaCore] -> EventManager : PlayerJoinEvent ERROR - req2 <= 0");
            } else {

                logger.log(Level.INFO, "[NaurelliaCore] -> EventManager : PlayerJoinEvent INFO - " + player.getName() + " added to igPermissions");
            }

            Database.close();

            /*
            If the player already joined the server, update the database
             */
        } else {

            int req3 = Database.executeUpdate("UPDATE usersIG SET isOnline='1' WHERE uuid='" + player.getUniqueId() + "';");

            if (req3 <= 0) {
                logger.log(Level.WARNING, "[NaurelliaCore] -> EventManager : PlayerJoinEvent ERROR - req3 <= 0");
            }

            Database.close();

            PermissionManager.readPermissions(player);
        }
    }

    /**
     * When a player break a block
     * @param event Block Break Event
     */
    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {

        Player player = event.getPlayer();

        assert world != null;
        if (world.getName().equalsIgnoreCase("world")) {

            if (!player.hasPermission("naurellia.admin")) {

                player.sendMessage(Louise.permissionMissing());
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityBlockChange(EntityChangeBlockEvent event) {;

        if (event.getBlock().getWorld().equals(world)) {

            event.setCancelled(true);

            if (event.getEntity() instanceof Player) {

                Player player = (Player) event.getEntity();

                player.sendMessage(Louise.permissionMissing());
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        int req = Database.executeUpdate("UPDATE usersIG SET isOnline='0' WHERE uuid='" + player.getUniqueId() + "';");

        if (req <= 0) {
            logger.log(Level.WARNING, "[NaurelliaCore] -> EventManager : PlayerQuitEvent ERROR - req <= 0");
        }

        Database.close();
    }
}
