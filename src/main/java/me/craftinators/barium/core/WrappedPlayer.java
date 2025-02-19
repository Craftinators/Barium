package me.craftinators.barium.core;

import me.craftinators.barium.Barium;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public abstract class WrappedPlayer {
    protected final Barium plugin;
    protected final UUID uuid;

    private Job job;

    public WrappedPlayer(@NotNull Barium plugin, @NotNull UUID uuid, @NotNull Job job) {
        this.plugin = plugin;
        this.uuid = uuid;
        this.job = job;
    }

    /**
     * Returns the {@link UUID} of the player associated with this wrapper
     * @return UUID of the player
     */
    public final @NotNull UUID getUniqueId() {
        return uuid;
    }

    /**
     * Returns the {@link Player} associated with this wrapper, or an {@link Optional#empty()} if the player is not online
     * @return Optional containing the Player object, or an empty Optional if the player is not online
     */
    public final @NotNull Optional<@NotNull Player> getPlayer() {
        return Optional.ofNullable(
                plugin.getServer().getPlayer(uuid)
        );
    }

    /**
     * Returns the job of the player associated with this wrapper
     * @return Job of the player
     */
    public final @NotNull Job getJob() {
        return job;
    }

    /**
     * Sets the job of the player associated with this wrapper
     * @param newJob New job of the player
     */
    public final void setJob(@NotNull Job newJob) {
        job = newJob;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        WrappedPlayer otherPlayer = (WrappedPlayer) obj;
        return uuid.equals(otherPlayer.uuid);
    }
}
