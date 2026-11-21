package com.kaleidoscope.backportedcookery.block;

import com.github.ysbbbbbb.kaleidoscopecookery.block.food.FoodBiteBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.init.registry.FoodBiteAnimateTicks;
import com.kaleidoscope.backportedcookery.BackportedConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * A {@link FoodBiteBlock} whose right-click eating can be disabled by config.
 * Everything else (bites, quality state, effects, drops, comparator output) is inherited
 * from the parent mod, so the 26.3 serving behaviour stays identical.
 */
public class BackportedFoodBiteBlock extends FoodBiteBlock {
    public BackportedFoodBiteBlock(FoodProperties foodProperties, int maxBites) {
        super(foodProperties, maxBites, null);
    }

    public BackportedFoodBiteBlock(FoodProperties foodProperties, int maxBites,
                                   FoodBiteAnimateTicks.AnimateTick animateTick) {
        super(foodProperties, maxBites, animateTick);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                            Player player, BlockHitResult hit) {
        if (!BackportedConfig.safe(BackportedConfig.ENABLE_FOOD_BLOCK_BITES)) {
            return InteractionResult.PASS;
        }
        return super.useWithoutItem(state, level, pos, player, hit);
    }
}
