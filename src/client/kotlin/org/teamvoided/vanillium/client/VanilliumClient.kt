package org.teamvoided.vanillium.client

import net.minecraft.client.gui.screens.MenuScreens
import net.minecraft.core.component.DataComponents.CONTAINER
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.ShulkerBoxBlock
import org.teamvoided.vanillium.client.init.VnlClientNet
import org.teamvoided.vanillium.client.screen.OpenShulkerBoxScreen
import org.teamvoided.vanillium.init.VnlMenus

@Suppress("unused")
object VanilliumClient {
    fun init() {
        MenuScreens.register(VnlMenus.QUICK_SHULKER, ::OpenShulkerBoxScreen)
        VnlClientNet.init()
    }

    @JvmStatic
    fun customUpdateAnim(
        player: Player,
        hand: InteractionHand,
        oldStack: ItemStack,
        newStack: ItemStack,
    ): Boolean {
        if (!ItemStack.isSameItem(oldStack, newStack)) return true
        val item = oldStack.item
        if (item !is BlockItem) return true
        if (item.block !is ShulkerBoxBlock) return true

        val modOld = oldStack.copy()
        modOld.set(CONTAINER, null)
        val modNew = newStack.copy()
        modNew.set(CONTAINER, null)

        return !ItemStack.isSameItemSameComponents(modOld, modNew)
    }
}