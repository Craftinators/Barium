package me.craftinators.barium.event.trap;

import me.craftinators.barium.core.Trap;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a trap related event
 */
public abstract class TrapEvent extends Event {
    protected final Trap trap;

    public TrapEvent(@NotNull Trap trap) {
        this.trap = trap;
    }

    /**
     * Returns the trap involved in this event
     *
     * @return Trap that is involved in this event
     */
    public final @NotNull Trap getTrap() {
        return trap;
    }
}