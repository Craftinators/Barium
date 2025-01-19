package me.craftinators.barium.event.player;

import me.craftinators.barium.core.Job;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player switches jobs
 */
public class PlayerSwitchJobEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Job oldJob;
    private final Job newJob;

    private boolean cancel = false;

    public PlayerSwitchJobEvent(@NotNull Player player, @NotNull Job oldJob, @NotNull Job newJob) {
        super(player);
        this.oldJob = oldJob;
        this.newJob = newJob;
    }

    /**
     * Returns the {@link Job} that the player switched from
     * @return Job that the player switched from
     */
    public @NotNull Job getOldJob() {
        return oldJob;
    }

    /**
     * Returns the {@link Job} that the player switched to
     * @return Job that the player switched to
     */
    public @NotNull Job getNewJob() {
        return newJob;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
