package me.craftinators.barium.event.trap;

import me.craftinators.barium.trap.Trap;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called when a trap is activated
 */
public class TrapActivateEvent extends TrapEvent implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;
    private final ActivationReason reason;

    private boolean cancel = false;

    public TrapActivateEvent(@NotNull Trap trap, @Nullable Player player, @NotNull ActivationReason reason) {
        super(trap);
        this.player = player;
        this.reason = reason;
    }

    /**
     * Returns the {@link Player} who activated the trap, or {@code null} if no player was involved.
     *
     * @return The player who activated the trap, or {@code null} if the trap was activated by a non-player event
     */
    public @Nullable Player getPlayer() {
        return player;
    }

    /**
     * Returns the {@link ActivationReason} indicating how the trap was activated.
     *
     * @return The reason for the trap's activation
     */
    public @NotNull ActivationReason getReason() {
        return reason;
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

    public enum ActivationReason {
        // TODO
    }
}
