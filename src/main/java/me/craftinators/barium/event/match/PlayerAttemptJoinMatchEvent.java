package me.craftinators.barium.event.match;

import me.craftinators.barium.core.Match;
import me.craftinators.barium.core.WrappedPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player attempts to join a match. Note that the {@link Match} instance is not yet modified to contain
 * the player that is attempting to join when this event is called.
 */
public class PlayerAttemptJoinMatchEvent extends MatchEvent implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final WrappedPlayer player;

    private boolean cancel = false;

    public PlayerAttemptJoinMatchEvent(@NotNull Match match, @NotNull WrappedPlayer player) {
        super(match);
        this.player = player;
    }

    /**
     * Returns the {@link WrappedPlayer} who joined the match.
     *
     * @return The player who joined the match
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
