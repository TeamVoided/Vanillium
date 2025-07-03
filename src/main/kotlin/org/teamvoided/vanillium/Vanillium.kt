package org.teamvoided.vanillium

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.teamvoided.vanillium.config.VanilliumCfg
import org.teamvoided.vanillium.init.VnlEvents

@Suppress("unused")
object Vanillium {
    const val MODID = "vanillium"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(Vanillium::class.simpleName)

    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::VanilliumCfg)

    fun init() {
        log.info("Vanillaing Vanilla")
        VnlEvents.init()
    }

    fun id(path: String): Identifier = Identifier.of(MODID, path)
}
