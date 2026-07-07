package org.teamvoided.vanillium.util

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import org.teamvoided.vanillium.inventory.QuickShulkerBoxMenu.Companion.shulkerMenuProvider


fun ServerPlayer.openShulker(stack: ItemStack, lockedSlot: Int) {
    closeContainer()
    openMenu(shulkerMenuProvider(stack, lockedSlot))
}