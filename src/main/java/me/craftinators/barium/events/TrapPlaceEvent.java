package me.craftinators.barium.events;

import me.craftinators.barium.traps.Trap;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a trap is placed
 */
public class TrapPlaceEvent extends TrapEvent implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;

    private boolean cancel = false;

    public TrapPlaceEvent(@NotNull Trap trap, @NotNull Player player) {
        super(trap);
        this.player = player;
    }

    /**
     * Gets the {@link Player} who placed the trap.
     *
     * @return The player responsible for placing the trap
     */
    public @NotNull Player getPlayer() {
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
