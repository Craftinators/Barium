package me.craftinators.barium.core;

import me.craftinators.barium.Barium;

import java.util.UUID;

public final class HumanPlayer extends WrappedPlayer {
    private final Job job;

    public HumanPlayer(Barium plugin, UUID uuid, Job job) {
        super(plugin, uuid);
        this.job = job;
    }

    /**
     * Returns the {@link Job} of the player
     * @return Job of the player
     */
    public Job getJob() {
        return job;
    }
}
