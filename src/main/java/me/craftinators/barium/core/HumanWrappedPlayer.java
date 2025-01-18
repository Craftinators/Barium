package me.craftinators.barium.core;

import me.craftinators.barium.Barium;

import java.util.UUID;

public final class HumanWrappedPlayer extends WrappedPlayer {
    private final Job job;

    public HumanWrappedPlayer(Barium plugin, UUID uuid, Job job) {
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
