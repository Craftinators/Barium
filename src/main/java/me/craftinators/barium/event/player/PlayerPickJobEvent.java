package me.craftinators.barium.event.player;

import me.craftinators.barium.core.Job;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player picks a job.
 */
public class PlayerPickJobEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    protected final Job job;

    private boolean cancel = false;

    public PlayerPickJobEvent(@NotNull Player player, @NotNull Job job) {
        super(player);
        this.job = job;
    }

    /**
     * Returns the {@link Job} that the player picked
     * @return Job that the player picked
     */
    public @NotNull Job getJob() {
        return job;
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
