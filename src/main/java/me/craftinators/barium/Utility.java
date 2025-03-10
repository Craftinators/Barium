package me.craftinators.barium;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
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

    /**
     * Gets a random element from the array. Fails fast if the array is empty.
     * @param array The array to get the random element from.
     * @param random The random number generator to use.
     * @return A random element from the array.
     * @param <T> The type of the elements in the array.
     */
    public static <T> @NotNull T getRandomElement(@NotNull T[] array, @NotNull Random random) {
        if (array.length == 0) throw new IllegalArgumentException("Array cannot be empty.");

        return array[random.nextInt(array.length)];
    }

    public static <E extends Enum<E>> @NotNull EnumSet<E> createEnumSetFromArray(E[] array) {
        if (array.length == 0) {
            @SuppressWarnings("unchecked")
            Class<E> enumType = (Class<E>) array.getClass().getComponentType();
            return EnumSet.noneOf(enumType);
        }
        return EnumSet.copyOf(Arrays.asList(array));
    }
}
