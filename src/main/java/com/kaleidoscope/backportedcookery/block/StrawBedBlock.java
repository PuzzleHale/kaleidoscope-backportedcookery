package com.kaleidoscope.backportedcookery.block;

import com.kaleidoscope.backportedcookery.BackportedConfig;
import com.kaleidoscope.backportedcookery.event.StrawBedSleepTracker;
import com.kaleidoscope.backportedcookery.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

/**
 * 1.21.1 backport of the 26.3 straw bed.
 *
 * <p>Behaviour reproduced from MC 26.3 / Kaleidoscope Cookery Refabricated:</p>
 * <ul>
 *   <li>Two-part horizontal bed ({@code facing}, {@code part}, {@code occupied}).</li>
 *   <li>Sleeps through the night but does <b>not</b> reset the spawn point.</li>
 *   <li>Single use: destroyed after waking up, and also when clicking "Get up".</li>
 *   <li>In dimensions where beds do not work it is destroyed without a drop and
 *       without an explosion (unlike a vanilla bed).</li>
 *   <li>Not added to {@code #minecraft:beds}, so villagers ignore it.</li>
 *   <li>Stacks to 16, is broken faster by a hoe (tag) and is compostable.</li>
 * </ul>
 *
 * <p>The class extends vanilla {@link BedBlock} so that {@code Player#startSleepInBed},
 * bed stand-up position lookup, two-part placement and breaking logic stay intact.
 * {@link #getRenderShape(BlockState)} is forced to {@link RenderShape#MODEL} (vanilla
 * beds render through a block entity) and {@link #newBlockEntity(BlockPos, BlockState)}
 * returns {@code null} so the JSON block-state models / textures are used instead.</p>
 */
public class StrawBedBlock extends BedBlock {

    /** Vanilla Backport registers the 26.3 straw bed as {@code minecraft:straw_bed}. */
    private static final ResourceLocation VANILLA_BACKPORT_STRAW_BED =
            ResourceLocation.fromNamespaceAndPath("minecraft", "straw_bed");

    @Nullable
    private static Block vanillaBackportStrawBed;

    public StrawBedBlock(Properties properties) {
        // 1.21.1 BedBlock requires a DyeColor; yellow is the straw colour and is only
        // used by the (disabled) vanilla bed block-entity renderer.
        super(DyeColor.YELLOW, properties);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    /**
     * Vanilla beds inherit {@code BedBlock#updateEntityAfterFallOn}, which calls
     * {@code bounceUp} and launches the entity back into the air. The 26.3 straw bed
     * used by this addon has no such trampoline behaviour, so the plain
     * {@code Block} response is used instead: horizontal momentum is kept, vertical
     * momentum is zeroed and no upward velocity is applied.
     *
     * <p>{@code BedBlock#fallOn} is intentionally left untouched, so the bed still
     * halves fall distance / fall damage like a normal bed.</p>
     */
    @Override
    public void updateEntityAfterFallOn(BlockGetter level, Entity entity) {
        entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D));
    }

    /**
     * 26.3 / Vanilla Backport straw beds use a much flatter collision shape than a
     * vanilla bed: a 4/16 high base, plus a 5/16 high pillow on the head half.
     *
     * <p>Instead of duplicating the shape maths, the exact shape is borrowed from
     * Vanilla Backport's {@code minecraft:straw_bed} block state. Only {@code part}
     * and {@code facing} are copied, which are the two properties the shape depends
     * on. If Vanilla Backport is ever absent the vanilla bed shape is used as a
     * graceful fallback.</p>
     */
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos,
                                  CollisionContext context) {
        Block bed = vanillaBackportStrawBed;
        if (bed == null) {
            bed = BuiltInRegistries.BLOCK.get(VANILLA_BACKPORT_STRAW_BED);
            vanillaBackportStrawBed = bed;
        }
        if (bed != Blocks.AIR) {
            BlockState other = bed.defaultBlockState();
            if (other.hasProperty(BlockStateProperties.BED_PART)
                    && other.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                other = other
                        .setValue(BlockStateProperties.BED_PART, state.getValue(PART))
                        .setValue(BlockStateProperties.HORIZONTAL_FACING, state.getValue(FACING));
                return other.getShape(level, pos, context);
            }
        }
        return super.getShape(state, level, pos, context);
    }

    /**
     * The parent implementation places the head half. It is repeated here defensively so
     * the straw bed always consists of two blocks even when placed by a plain BlockItem.
     */
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state,
                           @Nullable LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide) {
            Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);
            BlockPos headPos = pos.relative(facing);
            BlockState head = state.setValue(PART, BedPart.HEAD).setValue(OCCUPIED, false);
            level.setBlock(headPos, head, 3);
            level.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(level, pos, 3);
        }
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                            Player player, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.CONSUME;
        }
        if (!BackportedConfig.safe(BackportedConfig.ENABLE_STRAW_BED_SLEEP)) {
            return InteractionResult.PASS;
        }

        // The vanilla bed logic always sleeps at the HEAD part.
        BlockState headState = state;
        BlockPos headPos = pos;
        if (state.getValue(PART) != BedPart.HEAD) {
            headPos = pos.relative(state.getValue(HorizontalDirectionalBlock.FACING));
            headState = level.getBlockState(headPos);
            if (!headState.is(this)) {
                return InteractionResult.CONSUME;
            }
        }
        BlockPos footPos = headPos.relative(
                headState.getValue(HorizontalDirectionalBlock.FACING).getOpposite());

        // 26.3: "Cannot be used in the Nether or the End and will be destroyed upon
        // attempt to use, without dropping as an item." No explosion, no damage.
        if (!level.dimensionType().natural()) {
            if (BackportedConfig.safe(BackportedConfig.STRAW_BED_DESTROY_IN_WRONG_DIMENSION)) {
                removeWithoutDrops(level, headPos);
                removeWithoutDrops(level, footPos);
            }
            player.displayClientMessage(
                    Component.translatable("message.kaleidoscope_backportedcookery.straw_bed.no_sleep"), true);
            return InteractionResult.SUCCESS;
        }

        if (headState.getValue(OCCUPIED)) {
            player.displayClientMessage(
                    Component.translatable("message.kaleidoscope_backportedcookery.straw_bed.occupied"), true);
            return InteractionResult.SUCCESS;
        }

        // Track BEFORE startSleepInBed: ServerPlayer sets the respawn position inside
        // that call, which fires PlayerSetSpawnEvent. The tracker lets us cancel it.
        StrawBedSleepTracker.track(player, headPos, footPos);
        var result = player.startSleepInBed(headPos);
        if (result.left().isPresent()) {
            StrawBedSleepTracker.cancel(player);
            player.displayClientMessage(result.left().get().getMessage(), true);
            return InteractionResult.SUCCESS;
        }

        level.setBlock(headPos, headState.setValue(OCCUPIED, true), 2);
        BlockState footState = level.getBlockState(footPos);
        if (footState.is(this)) {
            level.setBlock(footPos, footState.setValue(OCCUPIED, true), 2);
        }
        return InteractionResult.SUCCESS;
    }

    /** Removes a half without producing a drop (used after sleeping / wrong dimension). */
    public static void removeWithoutDrops(Level level, BlockPos pos) {
        if (level.getBlockState(pos).is(ModBlocks.STRAW_BED.get())) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
    }

    /** Clears the occupied flag when the configuration disables single-use beds. */
    public static void setUnoccupied(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.is(ModBlocks.STRAW_BED.get()) && state.getValue(OCCUPIED)) {
            level.setBlock(pos, state.setValue(OCCUPIED, false), 2);
        }
    }
}
