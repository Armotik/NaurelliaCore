package fr.armotik.naurelliacore;

import fr.armotik.naurelliacore.listerners.EventManager;
import fr.armotik.naurelliacore.listerners.PermissionManager;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Naurelliacore extends JavaPlugin {

    private static Naurelliacore plugin;
    public static Naurelliacore getPlugin() {
        return plugin;
    }
    private static final Logger logger = Logger.getLogger(Naurelliacore.class.getName());

    /**
     * Plugin start
     */
    @Override
    public void onEnable() {
        // Plugin startup logic

        plugin = this;
        logger.log(Level.INFO, "[NaurelliaCore] -> NaurelliaCore is loading ...");
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        new WorldCreator("Earth").createWorld();

        logger.log(Level.INFO, "[NaurelliaCore] -> [WorldLoad] Loaded world Earth");

        this.getServer().getPluginManager().registerEvents(new EventManager(), this);
        this.getServer().getPluginManager().registerEvents(new PermissionManager(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
