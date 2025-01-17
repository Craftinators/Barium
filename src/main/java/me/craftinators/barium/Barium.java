package me.craftinators.barium;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Barium extends JavaPlugin {
    private ProtocolManager protocolManager;

    @Override
    public void onLoad() {
        // Plugin load logic
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    // Should never be null because it should be impossible to call this method before the plugin is loaded!
    public @NotNull ProtocolManager getProtocolManager() {
        return protocolManager;
    }
}
