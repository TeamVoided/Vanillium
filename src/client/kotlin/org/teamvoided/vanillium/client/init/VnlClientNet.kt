package org.teamvoided.vanillium.client.init

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import org.teamvoided.vanillium.client.screen.OpenShulkerBoxScreen
import org.teamvoided.vanillium.inventory.QuickShulkerBoxMenu.Companion.shulkerMenuProvider
import org.teamvoided.vanillium.net.s2c.OpenShulkerScreenPacket

object VnlClientNet {
    fun init() {
        ClientPlayNetworking.registerGlobalReceiver(OpenShulkerScreenPacket.ID, ::openShulkerScreen)
    }

    fun openShulkerScreen(packet: OpenShulkerScreenPacket, ctx: ClientPlayNetworking.Context) {
        val client = ctx.client() ?: return
        val player = client.player ?: return
        val inv = player.getInventory()
        val menu = shulkerMenuProvider(inv.getItem(packet.lockedSlot), packet.lockedSlot)
        player.containerMenu = menu.createMenu(packet.containerId, inv, player)
        client.setScreen(null)
        client.setScreen(OpenShulkerBoxScreen(player.containerMenu, inv, menu.displayName))
    }
}