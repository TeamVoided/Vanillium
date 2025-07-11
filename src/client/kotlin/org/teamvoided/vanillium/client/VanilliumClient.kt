package org.teamvoided.vanillium.client

import net.minecraft.client.gui.screens.MenuScreens
import org.teamvoided.vanillium.client.init.VnlClientNet
import org.teamvoided.vanillium.client.screen.OpenShulkerBoxScreen
import org.teamvoided.vanillium.init.VnlMenus

@Suppress("unused")
object VanilliumClient {
    fun init() {
        MenuScreens.register(VnlMenus.QUICK_SHULKER, ::OpenShulkerBoxScreen)
        VnlClientNet.init()
    }
}