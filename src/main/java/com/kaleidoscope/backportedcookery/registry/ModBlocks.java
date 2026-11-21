package com.kaleidoscope.backportedcookery.registry;

import com.kaleidoscope.backportedcookery.BackportedConfig;
import com.kaleidoscope.backportedcookery.KaleidoscopeBackportedCookery;
import com.kaleidoscope.backportedcookery.block.BackportedFoodBiteBlock;
import com.kaleidoscope.backportedcookery.block.BackportedPotSoupBlock;
import com.kaleidoscope.backportedcookery.block.StrawBedBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Blocks are registered in the parent namespace on purpose:
 * {@code kaleidoscope_cookery:straw_bed} is the exact id used by
 * Kaleidoscope Cookery Refabricated 26.3.
 */
public final class ModBlocks {
    public static final DeferredRegister<net.minecraft.world.level.block.Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, KaleidoscopeBackportedCookery.COOKERY_ID);

    /**
     * Mirrors the 26.3 block properties: yellow map colour, straw sound, 0.2 hardness,
     * no occlusion, ignited by lava and popped by pistons. 1.21.1 has no
     * {@code SoundType.STRAW_BED} / {@code PushReaction.POPPED}, so the closest vanilla
     * equivalents ({@link SoundType#GRASS} / {@link PushReaction#DESTROY}) are used.
     */
    public static final DeferredHolder<net.minecraft.world.level.block.Block, StrawBedBlock> STRAW_BED =
            BLOCKS.register("straw_bed", () -> new StrawBedBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_YELLOW)
                            .sound(SoundType.GRASS)
                            .strength(0.2F)
                            .noOcclusion()
                            .ignitedByLava()
                            .pushReaction(PushReaction.DESTROY)));

    // ---------------------------------------------------------------------
    // 26.3 placeable dishes (4 servings each). FoodBiteBlock already handles
    // bites, quality state, eating effects, comparator output and drops.
    // ---------------------------------------------------------------------
    private static final int FOOD_BLOCK_BITES =
            BackportedConfig.safe(BackportedConfig.FOOD_BLOCK_MAX_BITES);

    public static final DeferredHolder<net.minecraft.world.level.block.Block, BackportedFoodBiteBlock> BRAISED_FISH =
            BLOCKS.register("braised_fish", () -> new BackportedFoodBiteBlock(
                    BackportedFoods.BRAISED_FISH_BLOCK, FOOD_BLOCK_BITES));

    public static final DeferredHolder<net.minecraft.world.level.block.Block, BackportedFoodBiteBlock> YAKITORI =
            BLOCKS.register("yakitori", () -> new BackportedFoodBiteBlock(
                    BackportedFoods.YAKITORI_BLOCK, FOOD_BLOCK_BITES));

    public static final DeferredHolder<net.minecraft.world.level.block.Block, BackportedFoodBiteBlock> FRUIT_PLATTER =
            BLOCKS.register("fruit_platter", () -> new BackportedFoodBiteBlock(
                    BackportedFoods.FRUIT_PLATTER_BLOCK, FOOD_BLOCK_BITES));

    // 26.3 pot soups always have exactly two servings, regardless of the
    // generic "foodBlockServings" option.
    public static final DeferredHolder<net.minecraft.world.level.block.Block, BackportedPotSoupBlock> SHELF_MUSHROOM_POT_SOUP =
            BLOCKS.register("shelf_mushroom_pot_soup", () -> new BackportedPotSoupBlock(
                    BackportedFoods.SHELF_MUSHROOM_POT_SOUP_BLOCK, 2));

    private ModBlocks() {
    }
}
