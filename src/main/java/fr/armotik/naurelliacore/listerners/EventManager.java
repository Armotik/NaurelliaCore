package fr.armotik.naurelliacore.listerners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.logging.Logger;

public class EventManager implements Listener {

    private final Logger logger = Logger.getLogger(EventManager.class.getName());

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        PermissionManager.setupPermissions(player);

        player.setPlayerListHeader("\n §6§lNaurelliaCraft §7- §eMinecraft EarthRP  \n");
        player.setPlayerListFooter("\n §a§nnaurelliacraft.com \n");
    }
}
