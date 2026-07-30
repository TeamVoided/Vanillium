package org.teamvoided.vanillium.client.config

import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigSection
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen
import net.minecraft.client.gui.screens.inventory.CartographyTableScreen
import org.teamvoided.vanillium.Vanillium.MODID
import org.teamvoided.vanillium.Vanillium.id
import org.teamvoided.vanillium.data.tags.VnlMenuTags

@Suppress("unused")
class VanilliumClientCfg : Config(id("${MODID}_client")) {
    // region Tooltips
    var fuelTooltip = FuelTooltipSection()

    class FuelTooltipSection : ConfigSection() {

        var whereToRenderTooltip = TooltipRenderType.IN_MENU_TAG_OR_SCREEN

        var displayType = FuelDisplayType.ITEMS

        var countTimeForWholeStack = true

        fun shouldRender(screen: Screen?) =
            whereToRenderTooltip.shouldRender<AbstractFurnaceScreen<*>>(screen, VnlMenuTags.IS_FURNACE)
    }

    var mapColorTooltips = MapColorTooltipSection()

    class MapColorTooltipSection : ConfigSection() {

        var whereToRenderTooltip = TooltipRenderType.IN_MENU_TAG_OR_SCREEN

        fun shouldRender(screen: Screen?) =
            whereToRenderTooltip.shouldRender<CartographyTableScreen>(screen, VnlMenuTags.IS_CARTOGRAPHY_TABLE)
    }

    // endregion

}