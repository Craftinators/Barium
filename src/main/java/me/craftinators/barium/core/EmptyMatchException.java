package me.craftinators.barium.core;

/**
 * Thrown when an illegal operation is attempted on an empty match.
 */
public class EmptyMatchException extends RuntimeException {
    public EmptyMatchException() {
        super("Cannot perform operation on an empty match.");
    }
}
