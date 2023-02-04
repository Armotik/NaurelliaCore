package fr.armotik.naurelliacore;

import fr.armotik.naurelliacore.commands.*;
import fr.armotik.naurelliacore.listerners.EventManager;
import fr.armotik.naurelliacore.listerners.PermissionManager;
import fr.armotik.naurelliacore.utiles.Database;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Naurelliacore extends JavaPlugin {

    private static Naurelliacore plugin;
    public static Naurelliacore getPlugin() {
        return plugin;
    }
    private static final Logger logger = Logger.getLogger(Naurelliacore.class.getName());

    /**
     * Plugin starts
     */
    @Override
    public void onEnable() {
        // Plugin startup logic

        plugin = this;
        logger.log(Level.INFO, "[NaurelliaCore] -> NaurelliaCore is loading ...");
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        World world = new WorldCreator("Earth").createWorld();
        assert (world != null);

        logger.log(Level.INFO, "[NaurelliaCore] -> [WorldLoad] Loaded world Earth");

        Database.databaseTest();
        Database.close();

        /*
        Commands handlers
         */
        Objects.requireNonNull(getCommand("alert")).setExecutor(new AlertCommand());
        Objects.requireNonNull(getCommand("earth")).setExecutor(new EarthCommand());
        Objects.requireNonNull(getCommand("lobby")).setExecutor(new LobbyCommand());
        Objects.requireNonNull(getCommand("naurelliaadmin")).setExecutor(new NaurelliaAdminCommand());
        Objects.requireNonNull(getCommand("rank")).setExecutor(new RankCommand());

        /*
        Event Listeners
         */
        this.getServer().getPluginManager().registerEvents(new EventManager(), this);
        this.getServer().getPluginManager().registerEvents(new PermissionManager(), this);

        logger.log(Level.INFO, "[NaurelliaCore] -> Successfully loaded NaurelliaCore");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
