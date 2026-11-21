package com.kaleidoscope.backportedcookery.registry;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEffects;
import com.kaleidoscope.backportedcookery.BackportedConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

/**
 * Food values copied 1:1 from Kaleidoscope Cookery Refabricated 26.3
 * ({@code ModFoods} + {@code ModConsumables}).
 *
 * <p>26.3 separates the eating animation/effects into a {@code Consumable}; on 1.21.1
 * the same effects are attached directly to the {@link FoodProperties}. The parent mod's
 * custom effects (flatulence / warmth / preservation / vigor / satiated shield) are used
 * through its public {@link ModEffects} holders.</p>
 */
public final class BackportedFoods {
    /** Read once at registration; config may not be loaded yet, then the default is used. */
    private static final boolean EFFECTS = BackportedConfig.safe(BackportedConfig.FOOD_EFFECTS);

    private BackportedFoods() {
    }

    private static FoodProperties build(int nutrition, float saturation, float chance,
                                        MobEffectInstance... effects) {
        FoodProperties.Builder builder = new FoodProperties.Builder()
                .nutrition(nutrition)
                .saturationModifier(saturation)
                .alwaysEdible();
        if (EFFECTS) {
            for (MobEffectInstance effect : effects) {
                builder.effect(effect, chance);
            }
        }
        return builder.build();
    }

    // ---- 爆炒牛杂 / 牛杂盖浇饭 -------------------------------------------------
    public static final FoodProperties STIR_FRIED_BEEF_OFFAL = build(7, 0.3F, 1.0F,
            new MobEffectInstance(ModEffects.FLATULENCE, 600),
            new MobEffectInstance(ModEffects.WARMTH, 1200));

    public static final FoodProperties STIR_FRIED_BEEF_OFFAL_RICE_BOWL = build(14, 0.4F, 1.0F,
            new MobEffectInstance(ModEffects.FLATULENCE, 600),
            new MobEffectInstance(ModEffects.WARMTH, 1200));

    // ---- 农家杂蔬 --------------------------------------------------------------
    public static final FoodProperties COUNTRY_STYLE_MIXED_VEGETABLES = build(4, 0.4F, 1.0F,
            new MobEffectInstance(ModEffects.PRESERVATION, 4400),
            new MobEffectInstance(ModEffects.VIGOR, 900));

    // ---- 红烧鱼盖浇饭 ----------------------------------------------------------
    public static final FoodProperties BRAISED_FISH_RICE_BOWL = build(14, 0.4F, 1.0F,
            new MobEffectInstance(MobEffects.WATER_BREATHING, 3600),
            new MobEffectInstance(ModEffects.SATIATED_SHIELD, 1600));

    // ---- 辣子鸡盖浇饭 ----------------------------------------------------------
    public static final FoodProperties SPICY_CHICKEN_RICE_BOWL = build(14, 0.4F, 1.0F,
            new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1600),
            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2000));

    // ---- 诡异炒饭盖饭 ----------------------------------------------------------
    public static final FoodProperties SUSPICIOUS_STIR_FRY_RICE_BOWL = build(8, 0.4F, 0.15F,
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200),
            new MobEffectInstance(MobEffects.DIG_SPEED, 1200),
            new MobEffectInstance(MobEffects.LUCK, 1200),
            new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 1200),
            new MobEffectInstance(MobEffects.CONFUSION, 1200),
            new MobEffectInstance(MobEffects.JUMP, 1200));

    // ---- 番茄牛腩汤 ------------------------------------------------------------
    public static final FoodProperties TOMATO_BEEF_BRISKET_SOUP = build(5, 0.4F, 1.0F,
            new MobEffectInstance(ModEffects.SATIATED_SHIELD, 3600));

    // ---- 驴肉汤 ----------------------------------------------------------------
    public static final FoodProperties DONKEY_SOUP = build(6, 0.667F, 1.0F,
            new MobEffectInstance(ModEffects.WARMTH, 8 * 60 * 20));

    // ---- 生 / 熟驴肉 -----------------------------------------------------------
    public static final FoodProperties RAW_DONKEY_MEAT = build(2, 0.3F, 0F);
    public static final FoodProperties COOKED_DONKEY_MEAT = build(6, 0.8F, 0F);

    // ---- 红烧鱼（拼盘方块 + 直接食用物品） -------------------------------------
    public static final FoodProperties BRAISED_FISH_BLOCK = build(2, 0.8F, 1.0F,
            new MobEffectInstance(ModEffects.SATIATED_SHIELD, 1600),
            new MobEffectInstance(MobEffects.WATER_BREATHING, 3600));

    public static final FoodProperties BRAISED_FISH_ITEM = build(8, 0.8F, 1.0F,
            new MobEffectInstance(ModEffects.SATIATED_SHIELD, 1600),
            new MobEffectInstance(MobEffects.WATER_BREATHING, 3600));

    // ---- 日式烤串 --------------------------------------------------------------
    public static final FoodProperties YAKITORI_BLOCK = build(2, 0.6F, 1.0F,
            new MobEffectInstance(ModEffects.SATIATED_SHIELD, 1600),
            new MobEffectInstance(ModEffects.WARMTH, 800));

    public static final FoodProperties YAKITORI_ITEM = build(10, 0.6F, 1.0F,
            new MobEffectInstance(ModEffects.SATIATED_SHIELD, 1600),
            new MobEffectInstance(ModEffects.WARMTH, 800));

    // ---- 水果拼盘 --------------------------------------------------------------
    public static final FoodProperties FRUIT_PLATTER_BLOCK = build(3, 0.667F, 0F);
    public static final FoodProperties FRUIT_PLATTER_ITEM = build(13, 0.615F, 0F);

    // ---- 层孔菇瓦罐汤 ----------------------------------------------------------
    // 26.3: block 4 hunger / item 13 hunger, both 0.667 saturation and always edible,
    // both apply 5:00 of the parent mod's warmth effect with a 100% chance.
    public static final FoodProperties SHELF_MUSHROOM_POT_SOUP_BLOCK = build(4, 0.667F, 1.0F,
            new MobEffectInstance(ModEffects.WARMTH, 5 * 60 * 20));

    public static final FoodProperties SHELF_MUSHROOM_POT_SOUP_ITEM = build(13, 0.667F, 1.0F,
            new MobEffectInstance(ModEffects.WARMTH, 5 * 60 * 20));

    // ---- 极品蛋炒饭 ------------------------------------------------------------
    // 26.3 ModFoods: 12 hunger / 0.4 saturation, always edible;
    // ModConsumables: warmth for 900 ticks (45 s) at 100%.
    public static final FoodProperties DELICIOUS_EGG_FRIED_RICE = build(12, 0.4F, 1.0F,
            new MobEffectInstance(ModEffects.WARMTH, 900));
}
