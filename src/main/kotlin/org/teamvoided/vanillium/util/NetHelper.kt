package org.teamvoided.vanillium.util

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import org.teamvoided.vanillium.inventory.QuickShulkerBoxMenu.Companion.shulkerMenuProvider
import org.teamvoided.vanillium.mixin.ServerPlayerAccessor
import org.teamvoided.vanillium.net.s2c.OpenShulkerScreenPacket


fun ServerPlayer.openShulker(stack: ItemStack, lockedSlot: Int) {
    this.closeContainer()

    (this as ServerPlayerAccessor).vnl_invokeNextContainerCounter()
    val containerCounter = (this as ServerPlayerAccessor).nvl_getContainerCounter()
    ServerPlayNetworking.send(this, OpenShulkerScreenPacket(containerCounter, lockedSlot))
    this.containerMenu = shulkerMenuProvider(stack, lockedSlot).createMenu(containerCounter, this.getInventory(), this)!!
    (this as ServerPlayerAccessor).vnl_invokeInitMenu(this.containerMenu)
}