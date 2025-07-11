package org.teamvoided.vanillium.config

import me.fzzyhmstrs.fzzy_config.annotations.Action
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber.Companion.withIncrement
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber.WidgetType
import net.minecraft.world.item.Items.*
import org.teamvoided.vanillium.Vanillium.MODID
import org.teamvoided.vanillium.Vanillium.id

@Suppress("unused")
class VanilliumCfg : Config(id(MODID)) {
    var items = ConfigGroup("items", false)

    @RequiresAction(Action.RESTART)
    var customMaxDamage = mutableMapOf(SHEARS to 256, BRUSH to 256)

    @RequiresAction(Action.RESTART)
    var customStackSizes = mutableMapOf(
        // Potion
        POTION to 16,
        SPLASH_POTION to 16,
        LINGERING_POTION to 16,
        // Transport
        SADDLE to 16,
        LEATHER_HORSE_ARMOR to 16,
        IRON_HORSE_ARMOR to 16,
        GOLDEN_HORSE_ARMOR to 16,
        DIAMOND_HORSE_ARMOR to 16,
        MINECART to 4,
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
    )

    var enableCooldownsInCreative = true
    var customCooldowns = mutableMapOf(SPLASH_POTION to 30, LINGERING_POTION to 60)

    @ConfigGroup.Pop
    var shulkerInventoryInsert = true

    var extendedPotionDuration = ValidatedFloat(1.5f, 10f, 0.1f, WidgetType.TEXTBOX_WITH_BUTTONS).withIncrement(0.1f)
}