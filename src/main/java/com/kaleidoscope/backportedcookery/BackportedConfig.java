package com.kaleidoscope.backportedcookery;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Common config ({@code config/kaleidoscope_backportedcookery-common.toml}).
 *
 * <p>All options are read through {@link #safe(ModConfigSpec.ConfigValue)}: during the
 * registry phase the config file may not be loaded yet, in which case the 26.3 default
 * value is used. Options read during registration (durability, attack bonus, stack size)
 * are baked into the registered items and need a restart to take effect; all other
 * options are consulted on every use.</p>
 */
public final class BackportedConfig {
    public static final ModConfigSpec SPEC = build();

    // ---- Copper kitchen knife -------------------------------------------------
    /** 26.3 copper tool material durability. */
    public static ModConfigSpec.IntValue COPPER_KNIFE_DURABILITY;
    /** 26.3 copper tool material mining speed. */
    public static ModConfigSpec.DoubleValue COPPER_KNIFE_MINING_SPEED;
    /**
     * Tier attack-damage bonus. In 26.3 the knife declares 2.5 + copper material (+1.0)
     * = 3.5 weapon modifier. The 1.21.1 KitchenKnifeItem hardcodes a base of 3, so a
     * bonus of 0.5 reproduces the exact same 3.5 modifier (4.5 damage with the player
     * base of 1).
     */
    public static ModConfigSpec.DoubleValue COPPER_KNIFE_ATTACK_BONUS;
    /** 26.3 copper tool material enchantability. */
    public static ModConfigSpec.IntValue COPPER_KNIFE_ENCHANTABILITY;
    /** Registers the chopping-board dispenser behaviour for the copper knife. */
    public static ModConfigSpec.BooleanValue ENABLE_DISPENSER_CHOPPING;

    // ---- Straw bed ------------------------------------------------------------
    /** 26.3 straw beds stack to 16. */
    public static ModConfigSpec.IntValue STRAW_BED_MAX_STACK_SIZE;
    /** Whether right-clicking the bed can start sleeping at all. */
    public static ModConfigSpec.BooleanValue ENABLE_STRAW_BED_SLEEP;
    /** A straw bed is consumed after one sleep (including "Get up"). */
    public static ModConfigSpec.BooleanValue STRAW_BED_SINGLE_USE;
    /** 26.3 straw beds do NOT set or reset the player spawn point. */
    public static ModConfigSpec.BooleanValue STRAW_BED_SET_SPAWN_POINT;
    /** In dimensions where beds do not work (Nether / End) the bed is destroyed, not exploded. */
    public static ModConfigSpec.BooleanValue STRAW_BED_DESTROY_IN_WRONG_DIMENSION;
    /** Composter success chance of a straw bed item; 0 disables the registration. */
    public static ModConfigSpec.DoubleValue STRAW_BED_COMPOST_CHANCE;

    // ---- 26.3 foods -----------------------------------------------------------
    /** Adds the 26.3 status effects to the ported foods (flatulence, warmth, ...). */
    public static ModConfigSpec.BooleanValue FOOD_EFFECTS;
    /** Number of servings of a placeable dish (26.3: 4). Read at block registration. */
    public static ModConfigSpec.IntValue FOOD_BLOCK_MAX_BITES;
    /** Allows right-clicking a placed dish to eat a serving. */
    public static ModConfigSpec.BooleanValue ENABLE_FOOD_BLOCK_BITES;

    private BackportedConfig() {
    }

    private static ModConfigSpec build() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("copper_knife");
        COPPER_KNIFE_DURABILITY = builder
                .comment("Durability of the copper kitchen knife (26.3 copper material: 190).",
                        "Read during item registration; restart to apply.")
                .defineInRange("durability", 190, 1, 32767);
        COPPER_KNIFE_MINING_SPEED = builder
                .comment("Mining speed of the copper tool material (26.3: 5.0).",
                        "Read during item registration; restart to apply.")
                .defineInRange("miningSpeed", 5.0D, 0.0D, 64.0D);
        COPPER_KNIFE_ATTACK_BONUS = builder
                .comment("Tier attack-damage bonus. 0.5 reproduces the 26.3 copper knife",
                        "(2.5 base + 1.0 copper material = 3.5 modifier, 4.5 damage).",
                        "Read during item registration; restart to apply.")
                .defineInRange("attackDamageBonus", 0.5D, -10.0D, 20.0D);
        COPPER_KNIFE_ENCHANTABILITY = builder
                .comment("Enchantability of the copper tool material (26.3: 13).",
                        "Read during item registration; restart to apply.")
                .defineInRange("enchantability", 13, 1, 100);
        ENABLE_DISPENSER_CHOPPING = builder
                .comment("Let the copper knife chop on a chopping board when fired from a dispenser.")
                .define("enableDispenserChopping", true);
        builder.pop();

        builder.push("straw_bed");
        STRAW_BED_MAX_STACK_SIZE = builder
                .comment("Maximum stack size (26.3: 16).",
                        "Read during item registration; restart to apply.")
                .defineInRange("maxStackSize", 16, 1, 64);
        ENABLE_STRAW_BED_SLEEP = builder
                .comment("If false the bed becomes purely decorative and cannot be slept in.")
                .define("enableSleep", true);
        STRAW_BED_SINGLE_USE = builder
                .comment("26.3 behaviour: the bed is destroyed after one sleep / after clicking 'Get up'.")
                .define("singleUse", true);
        STRAW_BED_SET_SPAWN_POINT = builder
                .comment("26.3 behaviour: sleeping in a straw bed does NOT reset the spawn point.",
                        "Set to true to restore vanilla bed spawn-point behaviour.")
                .define("setSpawnPoint", false);
        STRAW_BED_DESTROY_IN_WRONG_DIMENSION = builder
                .comment("26.3 behaviour: in the Nether / End the bed is destroyed on use (no explosion, no drop).")
                .define("destroyInWrongDimension", true);
        STRAW_BED_COMPOST_CHANCE = builder
                .comment("Composter success chance for the straw bed item (0 disables it).")
                .defineInRange("compostChance", 0.65D, 0.0D, 1.0D);
        builder.pop();

        builder.push("foods");
        FOOD_EFFECTS = builder
                .comment("Apply the 26.3 status effects of the ported dishes",
                        "(flatulence, warmth, preservation, vigor, satiated shield, ...).",
                        "Read during item registration; restart to apply.")
                .define("enableStatusEffects", true);
        FOOD_BLOCK_MAX_BITES = builder
                .comment("Servings of a placed dish (26.3: 4).",
                        "Read during block registration; restart to apply.")
                .defineInRange("foodBlockServings", 4, 1, 8);
        ENABLE_FOOD_BLOCK_BITES = builder
                .comment("Allow eating a placed dish by right-clicking it.")
                .define("enableFoodBlockBites", true);
        builder.pop();

        return builder.build();
    }

    /**
     * Returns the configured value, or the declared default while the config file has
     * not been loaded yet (registry phase). Never throws.
     */
    public static <T> T safe(ModConfigSpec.ConfigValue<T> value) {
        try {
            return value.get();
        } catch (IllegalStateException | NullPointerException ignored) {
            return value.getDefault();
        }
    }
}
