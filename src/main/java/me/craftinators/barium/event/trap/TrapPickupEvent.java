package me.craftinators.barium.event.trap;

import me.craftinators.barium.core.WrappedPlayer;
import me.craftinators.barium.core.Trap;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a trap is picked up
 */
public class TrapPickupEvent extends TrapEvent implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final WrappedPlayer player;

    private boolean cancel = false;

    public TrapPickupEvent(@NotNull Trap trap, @NotNull WrappedPlayer player) {
        super(trap);
        this.player = player;
    }

    /**
     * Returns the {@link WrappedPlayer} who picked up the trap.
     *
     * @return The player who picked up the trap
     */
    public @NotNull WrappedPlayer getPlayer() {
        return player;
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
