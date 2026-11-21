package com.kaleidoscope.backportedcookery.block;

import com.github.ysbbbbbb.kaleidoscopecookery.init.registry.FoodBiteAnimateTicks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;

/**
 * 26.3 clay-pot soup ({@code shelf_mushroom_pot_soup}).
 *
 * <p>Reproduces the parent mod's {@code FoodBiteRegistry.FoodData}
 * for pot soups: two servings, the soup-pot collision shape
 * ({@code 1,0,1 -> 15,1,15} plus {@code 4,1,4 -> 12,7,12}) and the bubbling
 * {@code POT_SOUP_ANIMATE_TICK} particle behaviour. Drops are handled by the
 * inherited {@code FoodBiteBlock} code and the block loot table.</p>
 */
public class BackportedPotSoupBlock extends BackportedFoodBiteBlock {
    public BackportedPotSoupBlock(FoodProperties foodProperties, int maxBites) {
        super(foodProperties, maxBites, FoodBiteAnimateTicks.POT_SOUP_ANIMATE_TICK);
        this.setAABB(Shapes.or(
                Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D),
                Block.box(4.0D, 1.0D, 4.0D, 12.0D, 7.0D, 12.0D)));
    }
}
