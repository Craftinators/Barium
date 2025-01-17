package me.craftinators.barium.core;

import me.craftinators.barium.Barium;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class WrappedPlayer {
    protected final Barium plugin;
    protected final UUID uuid;

    protected WrappedPlayer(Barium plugin, UUID uuid) {
        this.plugin = plugin;
        this.uuid = uuid;
    }

    /**
     * Checks if the player associated with this wrapper is online
     * @return {@code true} if the player is online, {@code false} otherwise
     */
    public final boolean isOnline() {
        return getPlayer() != null;
    }

    /**
     * Returns the {@link UUID} of the player associated with this wrapper
     * @return UUID of the player
     */
    public final @NotNull UUID getUniqueId() {
        return uuid;
    }

    /**
     * Returns the {@link Player} associated with this wrapper, or {@code null} if the player is not online
     * @return Player object, or {@code null} if the player is not online
     */
    public final @Nullable Player getPlayer() {
        return plugin.getServer().getPlayer(uuid);
    }
}
