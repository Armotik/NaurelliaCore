package fr.armotik.naurelliacore.listerners;

import fr.armotik.naurelliacore.Louise;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.logging.Logger;

public class EventManager implements Listener {

    private final Logger logger = Logger.getLogger(EventManager.class.getName());
    private final World world = Bukkit.getWorld("world");



    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        PermissionManager.setupPermissions(player);

        player.setPlayerListHeader("\n §6§lNaurelliaCraft §7- §eMinecraft EarthRP  \n");
        player.setPlayerListFooter("\n §a§nnaurelliacraft.com \n");
    }

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
}
