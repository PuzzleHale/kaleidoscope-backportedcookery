package com.kaleidoscope.backportedcookery.event;

import com.github.ysbbbbbb.kaleidoscopecookery.block.dispenser.ChoppingBoardDispenseBehavior;
import com.kaleidoscope.backportedcookery.BackportedConfig;
import com.kaleidoscope.backportedcookery.KaleidoscopeBackportedCookery;
import com.kaleidoscope.backportedcookery.registry.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerSetSpawnEvent;
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.lang.reflect.Method;

/** All event handling for the addon. */
public final class ModEvents {
    private static final ResourceKey<CreativeModeTab> COOKERY_MAIN_TAB = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            ResourceLocation.fromNamespaceAndPath(KaleidoscopeBackportedCookery.COOKERY_ID, "cookery_main"));

    private static final ResourceKey<CreativeModeTab> COOKERY_FOOD_TAB = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            ResourceLocation.fromNamespaceAndPath(KaleidoscopeBackportedCookery.COOKERY_ID, "cookery_food"));

    private ModEvents() {
    }

    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // 26.3 fix: "A bug where copper knife can't be used inside dispenser".
            // Reuse the parent mod's chopping-board dispenser behaviour.
            if (BackportedConfig.safe(BackportedConfig.ENABLE_DISPENSER_CHOPPING)) {
                DispenserBlock.registerBehavior(
                        ModItems.COPPER_KITCHEN_KNIFE.get(), new ChoppingBoardDispenseBehavior(true));
            }

            float chance = BackportedConfig.safe(BackportedConfig.STRAW_BED_COMPOST_CHANCE).floatValue();
            if (chance > 0.0F) {
                registerCompostable(chance);
            }
        });
    }

    public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (COOKERY_MAIN_TAB.equals(event.getTabKey())) {
            // 26.3 main tab: ... kitchen shovel, copper knife, gold knife, iron knife ...
            // (1.21.1 puts the sickle between the shovel and the knives; keeping the
            // copper knife directly before the gold knife preserves the 26.3 knife order)
            insertBefore(event, "kaleidoscope_cookery:gold_kitchen_knife",
                    ModItems.COPPER_KITCHEN_KNIFE.get());
            // 26.3 main tab ends with the straw bed, directly after the red lantern.
            insertAfter(event, "kaleidoscope_cookery:red_lantern", ModItems.STRAW_BED.get());
            return;
        }

        if (!COOKERY_FOOD_TAB.equals(event.getTabKey())) {
            return;
        }

        // ------------------------------------------------------------------
        // 26.3 food tab order (ModCreativeTabs#COOKERY_FOOD_TAB):
        //   肉类:       cooked pork belly -> raw donkey -> cooked donkey -> cut small meats
        //   小碗菜:     ...tomato egg rice bowl -> beef offal -> beef offal rice bowl -> braised beef...
        //               sweet & sour pork rice bowl -> country mixed vegetables -> fish shredded pork
        //               ...fish shredded pork rice bowl -> braised fish rice bowl
        //               -> spicy chicken rice bowl -> suspicious stir-fry rice bowl
        //   汤类:       wild mushroom rabbit soup -> tomato beef brisket soup -> pufferfish soup
        //               chicken & mushroom stew -> donkey soup -> laba congee
        //   硬菜:       chorus fried egg -> braised fish -> spicy chicken -> yakitori
        //               ...fried spring roll -> fruit platter -> braised pork ribs
        //               ...buddha jumps over the wall -> shelf mushroom pot soup -> golden salad
        // ------------------------------------------------------------------
        insertAfter(event, "kaleidoscope_cookery:cooked_pork_belly", ModItems.RAW_DONKEY_MEAT.get());
        insertAfter(event, "kaleidoscope_cookery:raw_donkey_meat", ModItems.COOKED_DONKEY_MEAT.get());

        // 26.3: cooked rice -> egg fried rice -> delicious egg fried rice -> tomato egg ...
        insertAfter(event, "kaleidoscope_cookery:egg_fried_rice",
                ModItems.DELICIOUS_EGG_FRIED_RICE.get());
        insertAfter(event, "kaleidoscope_cookery:scramble_egg_with_tomatoes_rice_bowl",
                ModItems.STIR_FRIED_BEEF_OFFAL.get());
        insertAfter(event, "kaleidoscope_cookery:stir_fried_beef_offal",
                ModItems.STIR_FRIED_BEEF_OFFAL_RICE_BOWL.get());
        insertAfter(event, "kaleidoscope_cookery:sweet_and_sour_pork_rice_bowl",
                ModItems.COUNTRY_STYLE_MIXED_VEGETABLES.get());
        insertAfter(event, "kaleidoscope_cookery:fish_flavored_shredded_pork_rice_bowl",
                ModItems.BRAISED_FISH_RICE_BOWL.get());
        insertAfter(event, "kaleidoscope_cookery:braised_fish_rice_bowl",
                ModItems.SPICY_CHICKEN_RICE_BOWL.get());
        insertAfter(event, "kaleidoscope_cookery:spicy_chicken_rice_bowl",
                ModItems.SUSPICIOUS_STIR_FRY_RICE_BOWL.get());

        insertAfter(event, "kaleidoscope_cookery:wild_mushroom_rabbit_soup",
                ModItems.TOMATO_BEEF_BRISKET_SOUP.get());
        insertAfter(event, "kaleidoscope_cookery:chicken_and_mushroom_stew",
                ModItems.DONKEY_SOUP.get());

        insertAfter(event, "kaleidoscope_cookery:chorus_fried_egg", ModItems.BRAISED_FISH.get());
        insertAfter(event, "kaleidoscope_cookery:spicy_chicken", ModItems.YAKITORI.get());
        insertAfter(event, "kaleidoscope_cookery:fried_spring_roll", ModItems.FRUIT_PLATTER.get());
        insertAfter(event, "kaleidoscope_cookery:buddha_jumps_over_the_wall",
                ModItems.SHELF_MUSHROOM_POT_SOUP.get());
    }

    /**
     * Inserts {@code item} directly after the given parent-mod entry. The anchor is
     * looked up in the live tab contents first, because
     * {@code BuildCreativeModeTabContentsEvent#insertAfter} throws when an addon is used
     * with an older parent-mod version that lacks the anchor. In that case the item is
     * appended instead of crashing the creative menu.
     */
    private static void insertAfter(BuildCreativeModeTabContentsEvent event, String anchorId, ItemLike item) {
        insert(event, anchorId, item, false);
    }

    private static void insertBefore(BuildCreativeModeTabContentsEvent event, String anchorId, ItemLike item) {
        insert(event, anchorId, item, true);
    }

    private static void insert(BuildCreativeModeTabContentsEvent event, String anchorId,
                               ItemLike item, boolean before) {
        Item anchor = BuiltInRegistries.ITEM.get(ResourceLocation.parse(anchorId));
        ItemStack anchorStack = anchor == null ? ItemStack.EMPTY : anchor.getDefaultInstance();
        ItemStack newStack = item.asItem().getDefaultInstance();
        if (!anchorStack.isEmpty() && event.getParentEntries().contains(anchorStack)) {
            CreativeModeTab.TabVisibility visibility = CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;
            if (before) {
                event.insertBefore(anchorStack, newStack, visibility);
            } else {
                event.insertAfter(anchorStack, newStack, visibility);
            }
        } else {
            event.accept(item);
        }
    }

    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        StrawBedSleepTracker.consume(event.getEntity());
    }

    public static void onPlayerSetSpawn(PlayerSetSpawnEvent event) {
        // A 26.3 straw bed never resets the spawn point.
        if (StrawBedSleepTracker.isSleeping(event.getEntity())
                && !BackportedConfig.safe(BackportedConfig.STRAW_BED_SET_SPAWN_POINT)) {
            event.setCanceled(true);
        }
    }

    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        StrawBedSleepTracker.consume(event.getEntity());
    }

    public static void onServerTick(ServerTickEvent.Post event) {
        StrawBedSleepTracker.tick();
    }

    /**
     * 1.21.1 keeps {@code ComposterBlock#add(float, ItemLike)} private, so it is reached
     * reflectively. If a future NeoForge build changes this, the call fails gracefully
     * and only the (cosmetic) compostability of the straw bed is lost.
     */
    private static void registerCompostable(float chance) {
        try {
            Method add = ComposterBlock.class.getDeclaredMethod("add", float.class, ItemLike.class);
            add.setAccessible(true);
            add.invoke(null, chance, ModItems.STRAW_BED.get());
        } catch (ReflectiveOperationException | RuntimeException e) {
            KaleidoscopeBackportedCookery.LOGGER.warn(
                    "Could not register the straw bed as compostable: {}", e.toString());
        }
    }
}
