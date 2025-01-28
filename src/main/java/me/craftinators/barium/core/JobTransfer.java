package me.craftinators.barium.core;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a transfer of a {@link Job} from one job to another.
 */
public record JobTransfer(Job oldJob, Job newJob) implements Comparable<JobTransfer> {
    /**
     * Checks if this {@link JobTransfer} is redundant, meaning that the old job is the same as the new job.
     * @return {@code true} if the old job is the same as the new job, {@code false} otherwise
     */
    public boolean isRedundant() {
        return oldJob.equals(newJob);
    }

    /**
     * Compares this {@link JobTransfer} with the specified {@link JobTransfer} for order.
     * The comparison is based first on {@code oldJob}, and if they are equal, on {@code newJob}.
     *
     * @param other the {@link JobTransfer} to be compared
     * @return a negative integer, zero, or a positive integer as this {@link JobTransfer}
     *         is less than, equal to, or greater than the specified {@link JobTransfer}.
     */
    @Override
    public int compareTo(@NotNull JobTransfer other) {
        int oldJobComparison = oldJob.compareTo(other.oldJob());
        if (oldJobComparison == 0) return newJob.compareTo(other.newJob());
        return oldJobComparison;
    }
}
