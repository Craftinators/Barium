package me.craftinators.barium;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Random;

public final class Utility {
    private Utility() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    /**
     * Gets a random element from the collection. Fails fast if the collection is empty.
     * @param collection The collection to get the random element from.
     * @param random The random number generator to use.
     * @return A random element from the collection.
     * @param <T> The type of the elements in the collection.
     */
    public static <T> @NotNull T getRandomElement(@NotNull Collection<T> collection, @NotNull Random random) {
        if (collection.isEmpty()) throw new IllegalArgumentException("Collection cannot be empty.");

        return collection.stream()
                .skip(random.nextInt(collection.size()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "No elements found in the collection. This should not happen."
                ));
    }
}
