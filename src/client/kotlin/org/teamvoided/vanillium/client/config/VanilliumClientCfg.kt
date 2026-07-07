package org.teamvoided.vanillium.client.config

import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigSection
import org.teamvoided.vanillium.Vanillium.MODID
import org.teamvoided.vanillium.Vanillium.id

@Suppress("unused")
class VanilliumClientCfg : Config(id("${MODID}_client")) {
    // region Tooltips
    var fuelTooltip = FuelTooltipSection()

    class FuelTooltipSection : ConfigSection() {

        var whenToRenderTooltip = TooltipRenderType.TAG_OR_CLASS_CHECK

        var displayType = FuelDisplayType.ITEMS

    }

    // endregion

}