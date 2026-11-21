package com.kaleidoscope.backportedcookery;

import com.kaleidoscope.backportedcookery.event.ModEvents;
import com.kaleidoscope.backportedcookery.registry.ModBlocks;
import com.kaleidoscope.backportedcookery.registry.ModItems;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

/**
 * Kaleidoscope: Backported Cookery.
 *
 * <p>A standalone addon for {@code kaleidoscope_cookery} (NeoForge 1.21.1) that backports
 * exactly two pieces of content from Kaleidoscope Cookery Refabricated 1.6.x / MC 26.3:
 * the copper kitchen knife and the straw bed.</p>
 *
 * <p>The mod id is {@code kaleidoscope_backportedcookery}, but every item / block id is
 * registered in the {@code kaleidoscope_cookery} namespace. This matches the naming
 * convention of the parent mod and of its existing addons, so recipe books, world saves
 * and data packs see {@code kaleidoscope_cookery:copper_kitchen_knife} and
 * {@code kaleidoscope_cookery:straw_bed} exactly as in the 26.3 original.</p>
 */
@Mod(KaleidoscopeBackportedCookery.MOD_ID)
public final class KaleidoscopeBackportedCookery {
    public static final String MOD_ID = "kaleidoscope_backportedcookery";
    public static final String COOKERY_ID = "kaleidoscope_cookery";
    public static final Logger LOGGER = LogUtils.getLogger();

    public KaleidoscopeBackportedCookery(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, BackportedConfig.SPEC);

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);

        // Mod-bus events
        modEventBus.addListener(ModEvents::onCommonSetup);
        modEventBus.addListener(ModEvents::onBuildCreativeTabContents);

        // Gameplay events (forge bus)
        NeoForge.EVENT_BUS.addListener(ModEvents::onPlayerWakeUp);
        NeoForge.EVENT_BUS.addListener(ModEvents::onPlayerSetSpawn);
        NeoForge.EVENT_BUS.addListener(ModEvents::onPlayerLoggedOut);
        NeoForge.EVENT_BUS.addListener(ModEvents::onServerTick);
    }
}
