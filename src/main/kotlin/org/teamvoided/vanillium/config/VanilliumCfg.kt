package org.teamvoided.vanillium.config

import me.fzzyhmstrs.fzzy_config.annotations.Action
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedMap
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedRegistryType
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedEnum
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber.Companion.withIncrement
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber.WidgetType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items.*
import net.minecraft.world.item.alchemy.Potions
import org.teamvoided.vanillium.Vanillium.MODID
import org.teamvoided.vanillium.Vanillium.id

@Suppress("unused")
class VanilliumCfg : Config(id(MODID)) {
    // region Items
    var items = ConfigGroup("items", false)

    @RequiresAction(Action.RESTART)
    var customMaxDamage = ValidatedMap.Builder<Item, Int>()
        .keyHandler(ValidatedRegistryType.of(BuiltInRegistries.ITEM))
        .valueHandler(ValidatedInt(256, Int.MAX_VALUE, 1))
        .defaults(
            SHEARS to 256,
            BRUSH to 256,
            FLINT_AND_STEEL to 256,
            FISHING_ROD to 128,
            CARROT_ON_A_STICK to 128,
            WARPED_FUNGUS_ON_A_STICK to 128,
        ).build()


    @RequiresAction(Action.RESTART)
    var customStackSizes = ValidatedMap.Builder<Item, Int>()
        .keyHandler(ValidatedRegistryType.of(BuiltInRegistries.ITEM))
        .valueHandler(ValidatedInt(16, Int.MAX_VALUE, 1))
        .defaults(
            // Potion
            POTION to 16,
            SPLASH_POTION to 16,
            LINGERING_POTION to 16,
            // Buckets
            WATER_BUCKET to 16,
            COD_BUCKET to 16,
            SALMON_BUCKET to 16,
            TROPICAL_FISH to 16,
            PUFFERFISH_BUCKET to 16,
            AXOLOTL_BUCKET to 16,
            TADPOLE_BUCKET to 16,
            LAVA_BUCKET to 16,
            POWDER_SNOW_BUCKET to 16,
            MILK_BUCKET to 16,
            // Horse Armor
            LEATHER_HORSE_ARMOR to 16,
            COPPER_HORSE_ARMOR to 16,
            IRON_HORSE_ARMOR to 16,
            GOLDEN_HORSE_ARMOR to 16,
            DIAMOND_HORSE_ARMOR to 16,
            NETHERITE_HORSE_ARMOR to 16,
            // Nautilus Armor
            COPPER_NAUTILUS_ARMOR to 16,
            IRON_NAUTILUS_ARMOR to 16,
            GOLDEN_NAUTILUS_ARMOR to 16,
            DIAMOND_NAUTILUS_ARMOR to 16,
            NETHERITE_NAUTILUS_ARMOR to 16,
            // Animal Equipment
            SADDLE to 16,
            WHITE_HARNESS to 16,
            ORANGE_HARNESS to 16,
            MAGENTA_HARNESS to 16,
            LIGHT_BLUE_HARNESS to 16,
            YELLOW_HARNESS to 16,
            LIME_HARNESS to 16,
            PINK_HARNESS to 16,
            GRAY_HARNESS to 16,
            LIGHT_GRAY_HARNESS to 16,
            CYAN_HARNESS to 16,
            PURPLE_HARNESS to 16,
            BLUE_HARNESS to 16,
            BROWN_HARNESS to 16,
            GREEN_HARNESS to 16,
            RED_HARNESS to 16,
            BLACK_HARNESS to 16,
            // Transport
            MINECART to 4,
            OAK_BOAT to 4,
            SPRUCE_BOAT to 4,
            BIRCH_BOAT to 4,
            JUNGLE_BOAT to 4,
            ACACIA_BOAT to 4,
            CHERRY_BOAT to 4,
            DARK_OAK_BOAT to 4,
            PALE_OAK_BOAT to 4,
            MANGROVE_BOAT to 4,
            // Misc
            SNOWBALL to 64,
            EGG to 64,
            BROWN_EGG to 64,
            BLUE_EGG to 64,
            HONEY_BOTTLE to 64,
            ARMOR_STAND to 64,
            CAKE to 64,
            WRITABLE_BOOK to 64,
            WRITTEN_BOOK to 64,
            // Pattern
            FLOWER_BANNER_PATTERN to 64,
            CREEPER_BANNER_PATTERN to 64,
            SKULL_BANNER_PATTERN to 64,
            MOJANG_BANNER_PATTERN to 64,
            GLOBE_BANNER_PATTERN to 64,
            PIGLIN_BANNER_PATTERN to 64,
            FLOW_BANNER_PATTERN to 64,
            GUSTER_BANNER_PATTERN to 64,
            FIELD_MASONED_BANNER_PATTERN to 64,
            BORDURE_INDENTED_BANNER_PATTERN to 64,
            // Banner
            WHITE_BANNER to 64,
            ORANGE_BANNER to 64,
            MAGENTA_BANNER to 64,
            LIGHT_BLUE_BANNER to 64,
            YELLOW_BANNER to 64,
            LIME_BANNER to 64,
            PINK_BANNER to 64,
            GRAY_BANNER to 64,
            LIGHT_GRAY_BANNER to 64,
            CYAN_BANNER to 64,
            PURPLE_BANNER to 64,
            BLUE_BANNER to 64,
            BROWN_BANNER to 64,
            GREEN_BANNER to 64,
            RED_BANNER to 64,
            BLACK_BANNER to 64,
            // Bed
            WHITE_BED to 16,
            ORANGE_BED to 16,
            MAGENTA_BED to 16,
            LIGHT_BLUE_BED to 16,
            YELLOW_BED to 16,
            LIME_BED to 16,
            PINK_BED to 16,
            GRAY_BED to 16,
            LIGHT_GRAY_BED to 16,
            CYAN_BED to 16,
            PURPLE_BED to 16,
            BLUE_BED to 16,
            BROWN_BED to 16,
            GREEN_BED to 16,
            RED_BED to 16,
            BLACK_BED to 16,
            // Soup / Stew
            MUSHROOM_STEW to 16,
            BEETROOT_SOUP to 16,
            RABBIT_STEW to 16,
            SUSPICIOUS_STEW to 16,
            // Signs
            OAK_SIGN to 64,
            SPRUCE_SIGN to 64,
            BIRCH_SIGN to 64,
            JUNGLE_SIGN to 64,
            ACACIA_SIGN to 64,
            CHERRY_SIGN to 64,
            DARK_OAK_SIGN to 64,
            PALE_OAK_SIGN to 64,
            MANGROVE_SIGN to 64,
            BAMBOO_SIGN to 64,
            CRIMSON_SIGN to 64,
            WARPED_SIGN to 64,
            // Hanging Signs
            OAK_HANGING_SIGN to 64,
            SPRUCE_HANGING_SIGN to 64,
            BIRCH_HANGING_SIGN to 64,
            JUNGLE_HANGING_SIGN to 64,
            ACACIA_HANGING_SIGN to 64,
            CHERRY_HANGING_SIGN to 64,
            DARK_OAK_HANGING_SIGN to 64,
            PALE_OAK_HANGING_SIGN to 64,
            MANGROVE_HANGING_SIGN to 64,
            BAMBOO_HANGING_SIGN to 64,
            CRIMSON_HANGING_SIGN to 64,
            WARPED_HANGING_SIGN to 64,
            // Discs
            MUSIC_DISC_13 to 16,
            MUSIC_DISC_CAT to 16,
            MUSIC_DISC_BLOCKS to 16,
            MUSIC_DISC_CHIRP to 16,
            MUSIC_DISC_CREATOR to 16,
            MUSIC_DISC_CREATOR_MUSIC_BOX to 16,
            MUSIC_DISC_FAR to 16,
            MUSIC_DISC_MALL to 16,
            MUSIC_DISC_MELLOHI to 16,
            MUSIC_DISC_STAL to 16,
            MUSIC_DISC_STRAD to 16,
            MUSIC_DISC_WARD to 16,
            MUSIC_DISC_11 to 16,
            MUSIC_DISC_WAIT to 16,
            MUSIC_DISC_OTHERSIDE to 16,
            MUSIC_DISC_RELIC to 16,
            MUSIC_DISC_5 to 16,
            MUSIC_DISC_PIGSTEP to 16,
            MUSIC_DISC_PRECIPICE to 16,
            MUSIC_DISC_TEARS to 16,
            MUSIC_DISC_LAVA_CHICKEN to 16
        ).build()

    var enableCooldownsInCreative = true
    var customCooldowns = ValidatedMap.Builder<Item, Int>()
        .keyHandler(ValidatedRegistryType.of(BuiltInRegistries.ITEM))
        .valueHandler(ValidatedInt(16, Int.MAX_VALUE, 1))
        .defaults(
            SPLASH_POTION to 30,
            LINGERING_POTION to 60
        ).build()

    var extendedPotionDuration = ConfigGroup("extended_potion_duration", false)

    var duration = ValidatedFloat(2f, 10f, .1f, WidgetType.TEXTBOX_WITH_BUTTONS).withIncrement(0.1f)
    var durationMode = ValidatedEnum(ListType.DENY_LIST)

    enum class ListType {
        ALLOW_LIST, DENY_LIST;

        fun state() = this == ALLOW_LIST
    }

    @ConfigGroup.Pop
    @ConfigGroup.Pop
    var durationsList = ValidatedRegistryType.of(Potions.STRENGTH.value(), BuiltInRegistries.POTION)
        .toSet(
            Potions.POISON.value(),
            Potions.LONG_POISON.value(),
            Potions.STRONG_POISON.value(),

            Potions.TURTLE_MASTER.value(),
            Potions.LONG_TURTLE_MASTER.value(),
            Potions.STRONG_TURTLE_MASTER.value(),

            Potions.REGENERATION.value(),
            Potions.LONG_REGENERATION.value(),
            Potions.STRONG_REGENERATION.value(),

            Potions.STRENGTH.value(),
            Potions.LONG_STRENGTH.value(),
            Potions.STRONG_STRENGTH.value(),

            Potions.WEAKNESS.value(),
            Potions.LONG_WEAKNESS.value(),
        )

    var shulkerTweaks = ConfigGroup("shulker_tweaks", true)
    var shulkerInventoryInsert = true
    var canOpenSkulkersWhenInHand = true
    @ConfigGroup.Pop
    var canOpenSkulkersInInventory = true
    // endregion

    // region Experimental
//    var experimental = ConfigGroup("experimental", true)
//    @ConfigGroup.Pop
    // endregion
}