package me.craftinators.barium.event.player;

import me.craftinators.barium.core.WrappedPlayer;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link WrappedPlayer} related event.
 */
public abstract class WrappedPlayerEvent extends Event {
    protected final WrappedPlayer player;

    public WrappedPlayerEvent(@NotNull WrappedPlayer player) {
        this.player = player;
    }

    /**
     * Returns the {@link WrappedPlayer} involved in this event.
     *
     * @return Player that is involved in this event
     */
    public final @NotNull WrappedPlayer getPlayer() {
        return player;
    }
}
