package net.yeyito.gambling;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Gambling extends JavaPlugin {
    private static Plugin instance;
    private static World OVERWORLD;

    public static World getOverworld() {
        return OVERWORLD;
    }

    public static Plugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Gambling enabled!");
        OVERWORLD = this.getServer().getWorlds().get(0);
        Server server = this.getServer();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
