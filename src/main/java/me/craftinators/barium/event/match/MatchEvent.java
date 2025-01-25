package me.craftinators.barium.event.match;

import me.craftinators.barium.core.Match;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a match related event
 */
public abstract class MatchEvent extends Event {
    protected final Match match;

    public MatchEvent(@NotNull Match match) {
        this.match = match;
    }

    /**
     * Returns the match involved in this event
     *
     * @return Match that is involved in this event
     */
    public final @NotNull Match getMatch() {
        return match;
    }
}
