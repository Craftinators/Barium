package me.craftinators.barium.core;

import com.google.common.collect.ImmutableSet;
import me.craftinators.barium.Barium;
import me.craftinators.barium.Utility;
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
    public final @NotNull Set<@NotNull WrappedPlayer> getPlayers() {
        return ImmutableSet.copyOf(players);
    }

    // <editor-fold desc="Job Utility">

    /**
     * Balances the {@link Job} among players to ensure that the difference between the most populous job and the least populous job
     * is at most 1, or 0 if the number of players is a multiple of the number of jobs.
     * @return A map of players and their job transfers, indicating which job they were moved from and to.
     */
    public final @NotNull Map<@NotNull WrappedPlayer, @NotNull JobTransfer> balanceJobs() {
        if (players.isEmpty()) return Collections.emptyMap();

        final Map<WrappedPlayer, JobTransfer> transfers = new HashMap<>();

        final long targetDifference = players.size() % Job.values().length == 0 ? 0 : 1;
        int difference = getLargestDifferenceBetweenJobs();

        while (difference > targetDifference) {
            Job mostPopulousJob = getMostPopulousJob();
            Job leastPopulousJob = getLeastPopulousJob();

            Set<WrappedPlayer> playersInMostPopulousJob = getPlayersInJob(mostPopulousJob);

            WrappedPlayer playerToMove = Utility.getRandomElement(playersInMostPopulousJob, random);

            JobTransfer transfer = transferPlayer(playerToMove, leastPopulousJob);
            transfers.put(playerToMove, transfer);

            difference = getLargestDifferenceBetweenJobs();
        }

        return transfers;
    }

    /**
     * Get the amount of players in each job
     * @return A map with the amount of players in each job
     */
    public final @NotNull Map<@NotNull Job, @NotNull Integer> getCountPerJob() {
        return players.stream()
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
                .orElseThrow(EmptyMatchException::new);
    }

    /**
     * Get the least populous job
     * @return The least populous job
     */
    public final @NotNull Job getLeastPopulousJob() {
        return getCountPerJob().entrySet().stream()
                .min(Map.Entry.comparingByValue()) // Gets the lowest value
                .map(Map.Entry::getKey) // Get the associated key
                .orElseThrow(EmptyMatchException::new);
    }

    /**
     * Get the players in a specific job.
     * @param job The job to get the players from
     * @return A set of players in the specified job
     */
    public final Set<WrappedPlayer> getPlayersInJob(Job job) {
        return players.stream()
                .filter(player -> player.getJob() == job)
                .collect(Collectors.toSet());
    }

    /**
     * Transfer a player to a new job.
     * @param player The player to transfer
     * @param newJob The new job to transfer the player to
     * @return A {@link JobTransfer} object representing the transfer
     */
    public final @NotNull JobTransfer transferPlayer(@NotNull WrappedPlayer player, @NotNull Job newJob) {
        Job oldJob = player.getJob();
        player.setJob(newJob);
        return new JobTransfer(oldJob, newJob);
    }

    // </editor-fold>
}
