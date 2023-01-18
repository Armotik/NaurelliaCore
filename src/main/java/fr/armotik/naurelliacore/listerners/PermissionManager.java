package fr.armotik.naurelliacore.listerners;

import fr.armotik.naurelliacore.Naurelliacore;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
}
