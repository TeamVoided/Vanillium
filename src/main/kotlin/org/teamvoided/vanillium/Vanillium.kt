package org.teamvoided.vanillium

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import net.minecraft.resources.Identifier
import net.minecraft.world.item.Item
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.teamvoided.vanillium.config.VanilliumCfg
import org.teamvoided.vanillium.init.VnlEvents
import org.teamvoided.vanillium.init.VnlMenus
import org.teamvoided.vanillium.init.VnlNet

@Suppress("unused")
object Vanillium {
    const val MODID = "vanillium"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(Vanillium::class.simpleName)

    val config by lazy { ConfigApi.registerAndLoadConfig(::VanilliumCfg) }

    fun init() {
        log.info("Vanillaing Vanilla")
        VnlMenus.init()
        VnlEvents.init()
        VnlNet.init()
    }

    fun id(path: String): Identifier = Identifier.fromNamespaceAndPath(MODID, path)
    fun mc(path: String): Identifier = Identifier.withDefaultNamespace(path)
}
