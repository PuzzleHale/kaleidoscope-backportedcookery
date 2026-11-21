package com.kaleidoscope.backportedcookery.registry;

import com.kaleidoscope.backportedcookery.BackportedConfig;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

/**
 * 1.21.1 has no {@code Tiers.COPPER}. This tier reproduces the 26.3 copper tool
 * material: 190 durability, 5.0 speed, 13 enchantability, repaired with copper ingots.
 *
 * <p>1.21.1 also has no {@code #incorrect_for_copper_tool} tag, so the stone-tool
 * incorrect tag is used. The parent mod's {@code KitchenKnifeItem} restricts the item
 * to {@code SWORD_DIG} anyway, so the mining tier has no gameplay impact.</p>
 */
public enum CopperTier implements Tier {
    INSTANCE;

    @Override
    public int getUses() {
        return BackportedConfig.safe(BackportedConfig.COPPER_KNIFE_DURABILITY);
    }

    @Override
    public float getSpeed() {
        return BackportedConfig.safe(BackportedConfig.COPPER_KNIFE_MINING_SPEED).floatValue();
    }

    @Override
    public float getAttackDamageBonus() {
        return BackportedConfig.safe(BackportedConfig.COPPER_KNIFE_ATTACK_BONUS).floatValue();
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return BlockTags.INCORRECT_FOR_STONE_TOOL;
    }

    @Override
    public int getEnchantmentValue() {
        return BackportedConfig.safe(BackportedConfig.COPPER_KNIFE_ENCHANTABILITY);
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Items.COPPER_INGOT);
    }
}
