package me.craftinators.barium.core.monster;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MonsterSelector {
    private static final ImmutableMap<Monster, Double> INITIAL_MONSTER_PROBABILITIES;

    static {
        EnumMap<Monster, Double> initialMonsterProbabilities = new EnumMap<>(Monster.class);
        double defaultChance = 1d / Monster.values().length;
        for (Monster monster : Monster.values()) {
            initialMonsterProbabilities.put(monster, defaultChance);
        }
        INITIAL_MONSTER_PROBABILITIES = Maps.immutableEnumMap(initialMonsterProbabilities);
    }

    // TODO: Would like to make "DEFAULT_REDUCTION_PERCENTAGE" and "MINIMUM_PROBABILITY" configurable
    private static final double DEFAULT_REDUCTION_PERCENTAGE = 50d / 100d; // 50%
    private static final double REDUCTION_FACTOR = 1 - DEFAULT_REDUCTION_PERCENTAGE;
    private static final double PROMOTION_FACTOR = 1 + DEFAULT_REDUCTION_PERCENTAGE / (Monster.values().length - 1);
    private static final double MINIMUM_PROBABILITY = 1d / 100d; // 1%

    private final ArrayList<Monster> previouslySelectedMonsters = new ArrayList<>();
    private final Random random;

    public MonsterSelector(Random random) {
        this.random = random;
    }

    public @NotNull Monster selectMonster() {
        // Selects a monster, with monsters that contain the tags in the previously selected monsters being less likely
        // to be selected.
        EnumMap<Monster, Double> monsterProbabilities = generateUnnormalizedMonsterProbabilities();

        // Normalize the probabilities so they sum to 1
        normalizeProbabilities(monsterProbabilities);

        // And then select a monster
        return selectWeightedMonster(monsterProbabilities);
    }

    public @NotNull EnumSet<@NotNull Monster> getMostProbableMonsters() {
        EnumMap<Monster, Double> monsterProbabilities = generateUnnormalizedMonsterProbabilities();

        // No need to normalize the probabilities, as we just want the most probable monsters
        final double highestProbability = Collections.max(monsterProbabilities.values());

        EnumSet<Monster> mostProbableMonsters = EnumSet.noneOf(Monster.class);
        for (Map.Entry<Monster, Double> entry : monsterProbabilities.entrySet()) {
            if (entry.getValue() == highestProbability) {
                mostProbableMonsters.add(entry.getKey());
            }
        }
        return mostProbableMonsters;
    }

    private EnumMap<Monster, Double> generateUnnormalizedMonsterProbabilities() {
        EnumMap<Monster, Double> unnormalizedMonsterProbabilities = new EnumMap<>(INITIAL_MONSTER_PROBABILITIES);

        for (Monster previouslySelectedMonster : previouslySelectedMonsters) {
            for (MonsterAttribute attribute : previouslySelectedMonster.getAttributes()) {
                for (Monster monster : Monster.values()) {
                    // Reduce the probability of monsters with the same attribute, but increase the probability of
                    // monsters not with the same attribute
                    final double currentProbability = unnormalizedMonsterProbabilities.get(monster);
                    if (monster.containsAttributes(attribute)) {
                        unnormalizedMonsterProbabilities.put(monster,
                                Math.max(currentProbability * REDUCTION_FACTOR, MINIMUM_PROBABILITY));
                    } else {
                        unnormalizedMonsterProbabilities.put(monster, currentProbability * PROMOTION_FACTOR);
                    }
                }
            }
        }

        return unnormalizedMonsterProbabilities;
    }

    private void normalizeProbabilities(EnumMap<Monster, Double> monsterProbabilities) {
        double sum = monsterProbabilities.values().stream().mapToDouble(Double::doubleValue).sum();
        for (Monster monster : Monster.values()) {
            monsterProbabilities.put(monster, monsterProbabilities.get(monster) / sum);
        }
    }

    private Monster selectWeightedMonster(EnumMap<Monster, Double> monsterProbabilities) {
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0d;

        for (Map.Entry<Monster, Double> entry : monsterProbabilities.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (randomValue <= cumulativeProbability) {
                previouslySelectedMonsters.add(entry.getKey());
                return entry.getKey();
            }
        }

        throw new IllegalStateException("Failed to select a monster. This should not happen.");
    }
}
