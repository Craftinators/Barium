package me.craftinators.barium.core;

import com.google.common.collect.ImmutableSet;
import me.craftinators.barium.Barium;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public abstract class Match {
    private final Barium plugin;
    private final Random random;
    private final Set<WrappedPlayer> players = new HashSet<>();

    public Match(Barium plugin) {
        this.plugin = plugin;
        random = ThreadLocalRandom.current();
    }

    /**
     * Gets the players in the match.
     * @return An immutable set of {@link WrappedPlayer} in the match.
     */
    public final @NotNull ImmutableSet<@NotNull WrappedPlayer> getPlayers() {
        return ImmutableSet.copyOf(players);
    }

    // <editor-fold desc="Job Utility">

    /**
     * Balances the {@link Job} among players to ensure that the difference between the most populous job and the least populous job
     * is at most 1, or 0 if the number of players is a multiple of the number of jobs.
     * @return A map of players and their job transfers, indicating which job they were moved from and to.
     */
    public final @NotNull Map<@NotNull WrappedPlayer, @NotNull JobTransfer> balanceJobs() {
        final Map<WrappedPlayer, JobTransfer> transfers = new HashMap<>();

        // Remove from the most populous job and give to the least populous job until the difference is at most 1 (or if
        // the number of players is a multiple of the number of jobs, until all jobs have the same amount of players)
        final long targetDifference = players.size() % Job.values().length == 0 ? 0 : 1;

        // Get the difference between the most popular job and the least popular job
        int difference = getLargestDifferenceBetweenJobs();

        while (difference > targetDifference) {
            // Get the most popular job
            Job mostPopulousJob = getMostPopulousJob();
            Job leastPopulousJob = getLeastPopulousJob();

            // Remove a player (by random) from the most populous job and add them to the least populous job

            // Get all the players in the most populous job (This set is guaranteed to have at least two people, or else
            // the difference would be at most 1)
            Set<WrappedPlayer> playersInMostPopulousJob = players.stream()
                    .filter(player -> player.getJob() == mostPopulousJob)
                    .collect(Collectors.toSet());

            // Get a random player from the most populous job
            WrappedPlayer playerToMove = playersInMostPopulousJob.stream()
                    .skip(random.nextInt(playersInMostPopulousJob.size()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No players found in the most populous job. This should not happen."));

            // Remove the player from the most populous job
            playerToMove.setJob(leastPopulousJob);
            transfers.put(playerToMove, new JobTransfer(mostPopulousJob, leastPopulousJob));

            difference = getLargestDifferenceBetweenJobs();
        }

        return transfers;
    }

    /**
     * Get the amount of players in each job
     * @return A map with the amount of players in each job
     */
    public final @NotNull EnumMap<@NotNull Job, @NotNull Integer> getCountPerJob() {
        return getPlayers().stream()
                .collect(Collectors.groupingBy(
                        WrappedPlayer::getJob,
                        () -> new EnumMap<>(Job.class),
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
    }

    /**
     * Get the largest difference between the amount of players in each job, i.e. the difference between the most popular
     * job and the least popular job.
     * @return The largest difference between the amount of players in each job
     */
    public final int getLargestDifferenceBetweenJobs() {
        Map<Job, Integer> countPerJob = getCountPerJob();

        return countPerJob.values().stream().max(Integer::compareTo).orElse(0) -
                countPerJob.values().stream().min(Integer::compareTo).orElse(0);
    }

    /**
     * Get the most populous job
     * @return The most populous job
     */
    public final @NotNull Job getMostPopulousJob() {
        return getCountPerJob().entrySet().stream()
                .max(Map.Entry.comparingByValue()) // Gets the highest value
                .map(Map.Entry::getKey) // Get the associated key
                .orElse(Job.Default); // In the case that the optional was empty, return the default job
    }

    /**
     * Get the least populous job
     * @return The least populous job
     */
    public final @NotNull Job getLeastPopulousJob() {
        return getCountPerJob().entrySet().stream()
                .min(Map.Entry.comparingByValue()) // Gets the lowest value
                .map(Map.Entry::getKey) // Get the associated key
                .orElse(Job.Default); // In the case that the optional was empty, return the default job
    }
}
