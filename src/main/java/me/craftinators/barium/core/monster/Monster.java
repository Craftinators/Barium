package me.craftinators.barium.core.monster;

import me.craftinators.barium.Utility;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;


public enum Monster {
    TEMPORARY_MONSTER_1(MonsterAttribute.MELEE, MonsterAttribute.HORDE), // zombie
    TEMPORARY_MONSTER_2(MonsterAttribute.RANGED), // skeleton
    TEMPORARY_MONSTER_3(MonsterAttribute.RANGED, MonsterAttribute.SURGE, MonsterAttribute.DEBUFF), // spider
    TEMPORARY_MONSTER_4(MonsterAttribute.MELEE, MonsterAttribute.SUPPORT, MonsterAttribute.SURGE), // slime
    TEMPORARY_MONSTER_5(MonsterAttribute.MELEE, MonsterAttribute.HORDE, MonsterAttribute.DESTROY), // Silverfish
    TEMPORARY_MONSTER_6(MonsterAttribute.RANGED, MonsterAttribute.DEBUFF), // Guardian
    TEMPORARY_MONSTER_7(MonsterAttribute.SUPPORT, MonsterAttribute.SURGE), // Enderman
    TEMPORARY_MONSTER_8(MonsterAttribute.DESTROY), // Creeper
    TEMPORARY_MONSTER_9(MonsterAttribute.DESTROY); // Ravager

    private final EnumSet<MonsterAttribute> attributes;

    Monster(MonsterAttribute... attributes) {
        this.attributes = Utility.createEnumSetFromArray(attributes);
    }

    public @NotNull Iterable<@NotNull MonsterAttribute> getAttributes() {
        return attributes;
    }

    public boolean containsAttributes(@NotNull MonsterAttribute attribute) {
        return attributes.contains(attribute);
    }
}
