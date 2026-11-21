package com.kaleidoscope.backportedcookery.item;

import com.github.ysbbbbbb.kaleidoscopecookery.item.BowlFoodBlockItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

/**
 * 26.3 pot soups use a flower pot as their container instead of a bowl.
 * {@link BowlFoodBlockItem} already returns the flower pot when eaten via
 * {@code usingConvertsTo}; this subclass only corrects {@code getContainerItem()}
 * so container-aware mods also see the flower pot.
 */
public class BackportedPotSoupItem extends BowlFoodBlockItem {
    public BackportedPotSoupItem(Block block, FoodProperties properties, ItemLike usingConvertsTo) {
        super(block, properties, usingConvertsTo);
    }

    @Override
    public Item getContainerItem() {
        return Items.FLOWER_POT;
    }
}
