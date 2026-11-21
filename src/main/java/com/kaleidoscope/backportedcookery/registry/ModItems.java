package com.kaleidoscope.backportedcookery.registry;

import com.github.ysbbbbbb.kaleidoscopecookery.item.BowlFoodOnlyItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.KitchenKnifeItem;
import com.kaleidoscope.backportedcookery.BackportedConfig;
import com.kaleidoscope.backportedcookery.KaleidoscopeBackportedCookery;
import com.kaleidoscope.backportedcookery.item.BackportedFoodBlockItem;
import com.kaleidoscope.backportedcookery.item.BackportedPotSoupItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Item ids follow the parent mod convention exactly:
 * {@code kaleidoscope_cookery:copper_kitchen_knife} and
 * {@code kaleidoscope_cookery:straw_bed}.
 *
 * <p>The copper knife is an actual {@link KitchenKnifeItem} of the parent mod, so it
 * inherits the 26.3 tooltip, the no-sweep behaviour and full chopping-board support.
 * It is also added to {@code #kaleidoscope_cookery:kitchen_knife} by a tag file, which
 * is what the parent mod uses for chopping and for the pig / piglin / hoglin oil drops.</p>
 */
public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, KaleidoscopeBackportedCookery.COOKERY_ID);

    public static final DeferredHolder<Item, KitchenKnifeItem> COPPER_KITCHEN_KNIFE =
            ITEMS.register("copper_kitchen_knife", () -> new KitchenKnifeItem(CopperTier.INSTANCE));

    /** Plain BlockItem, like the 26.3 original; BedBlock#setPlacedBy creates the head half. */
    public static final DeferredHolder<Item, BlockItem> STRAW_BED =
            ITEMS.register("straw_bed", () -> new BlockItem(
                    ModBlocks.STRAW_BED.get(),
                    new Item.Properties().stacksTo(
                            BackportedConfig.safe(BackportedConfig.STRAW_BED_MAX_STACK_SIZE))));

    // ---------------------------------------------------------------------
    // 26.3 foods. Bowl dishes extend the parent's BowlFoodOnlyItem, so they
    // return a bowl, scale with cooking quality and show the "maxim" tooltip.
    // ---------------------------------------------------------------------
    public static final DeferredHolder<Item, BowlFoodOnlyItem> STIR_FRIED_BEEF_OFFAL =
            ITEMS.register("stir_fried_beef_offal",
                    () -> new BowlFoodOnlyItem(BackportedFoods.STIR_FRIED_BEEF_OFFAL));

    public static final DeferredHolder<Item, BowlFoodOnlyItem> STIR_FRIED_BEEF_OFFAL_RICE_BOWL =
            ITEMS.register("stir_fried_beef_offal_rice_bowl",
                    () -> new BowlFoodOnlyItem(BackportedFoods.STIR_FRIED_BEEF_OFFAL_RICE_BOWL));

    public static final DeferredHolder<Item, BowlFoodOnlyItem> COUNTRY_STYLE_MIXED_VEGETABLES =
            ITEMS.register("country_style_mixed_vegetables",
                    () -> new BowlFoodOnlyItem(BackportedFoods.COUNTRY_STYLE_MIXED_VEGETABLES));

    public static final DeferredHolder<Item, BowlFoodOnlyItem> BRAISED_FISH_RICE_BOWL =
            ITEMS.register("braised_fish_rice_bowl",
                    () -> new BowlFoodOnlyItem(BackportedFoods.BRAISED_FISH_RICE_BOWL));

    public static final DeferredHolder<Item, BowlFoodOnlyItem> SPICY_CHICKEN_RICE_BOWL =
            ITEMS.register("spicy_chicken_rice_bowl",
                    () -> new BowlFoodOnlyItem(BackportedFoods.SPICY_CHICKEN_RICE_BOWL));

    public static final DeferredHolder<Item, BowlFoodOnlyItem> SUSPICIOUS_STIR_FRY_RICE_BOWL =
            ITEMS.register("suspicious_stir_fry_rice_bowl",
                    () -> new BowlFoodOnlyItem(BackportedFoods.SUSPICIOUS_STIR_FRY_RICE_BOWL));

    public static final DeferredHolder<Item, BowlFoodOnlyItem> TOMATO_BEEF_BRISKET_SOUP =
            ITEMS.register("tomato_beef_brisket_soup",
                    () -> new BowlFoodOnlyItem(BackportedFoods.TOMATO_BEEF_BRISKET_SOUP));

    public static final DeferredHolder<Item, BowlFoodOnlyItem> DONKEY_SOUP =
            ITEMS.register("donkey_soup",
                    () -> new BowlFoodOnlyItem(BackportedFoods.DONKEY_SOUP));

    public static final DeferredHolder<Item, Item> RAW_DONKEY_MEAT =
            ITEMS.register("raw_donkey_meat",
                    () -> new Item(new Item.Properties().food(BackportedFoods.RAW_DONKEY_MEAT)));

    public static final DeferredHolder<Item, Item> COOKED_DONKEY_MEAT =
            ITEMS.register("cooked_donkey_meat",
                    () -> new Item(new Item.Properties().food(BackportedFoods.COOKED_DONKEY_MEAT)));

    // Placeable dishes: the item can be eaten directly (full nutrition) or placed
    // down and eaten in four servings, exactly like 26.3.
    public static final DeferredHolder<Item, BackportedFoodBlockItem> BRAISED_FISH =
            ITEMS.register("braised_fish", () -> new BackportedFoodBlockItem(
                    ModBlocks.BRAISED_FISH.get(),
                    new Item.Properties().food(BackportedFoods.BRAISED_FISH_ITEM),
                    "tooltip.kaleidoscope_cookery.braised_fish.maxim"));

    public static final DeferredHolder<Item, BackportedFoodBlockItem> YAKITORI =
            ITEMS.register("yakitori", () -> new BackportedFoodBlockItem(
                    ModBlocks.YAKITORI.get(),
                    new Item.Properties().food(BackportedFoods.YAKITORI_ITEM),
                    "tooltip.kaleidoscope_cookery.yakitori.maxim"));

    public static final DeferredHolder<Item, BackportedFoodBlockItem> FRUIT_PLATTER =
            ITEMS.register("fruit_platter", () -> new BackportedFoodBlockItem(
                    ModBlocks.FRUIT_PLATTER.get(),
                    new Item.Properties().food(BackportedFoods.FRUIT_PLATTER_ITEM),
                    "tooltip.kaleidoscope_cookery.fruit_platter.maxim"));

    /**
     * 26.3 clay-pot soup. {@link BowlFoodBlockItem} is exactly the class the parent
     * mod uses for every pot soup: it stacks to 16, scales with cooking quality,
     * shows the {@code .maxim} tooltip, returns the flower pot when eaten and drops
     * the pot loot when the placed block is emptied.
     */
    public static final DeferredHolder<Item, BackportedPotSoupItem> SHELF_MUSHROOM_POT_SOUP =
            ITEMS.register("shelf_mushroom_pot_soup", () -> new BackportedPotSoupItem(
                    ModBlocks.SHELF_MUSHROOM_POT_SOUP.get(),
                    BackportedFoods.SHELF_MUSHROOM_POT_SOUP_ITEM,
                    Items.FLOWER_POT));

    /** 26.3 极品蛋炒饭：普通碗装食物，食用后返碗。 */
    public static final DeferredHolder<Item, BowlFoodOnlyItem> DELICIOUS_EGG_FRIED_RICE =
            ITEMS.register("delicious_egg_fried_rice", () -> new BowlFoodOnlyItem(
                    BackportedFoods.DELICIOUS_EGG_FRIED_RICE));

    private ModItems() {
    }
}
