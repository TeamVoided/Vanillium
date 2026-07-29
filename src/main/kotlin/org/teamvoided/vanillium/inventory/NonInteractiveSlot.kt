package org.teamvoided.vanillium.inventory

import net.minecraft.resources.Identifier
import net.minecraft.world.Container
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import java.util.*


class NonInteractiveSlot(container: Container, slot: Int, x: Int, y: Int) : Slot(container, slot, x, y),
    CustomSlotBackground {
    override fun mayPickup(player: Player): Boolean = false
    override fun tryRemove(i: Int, j: Int, player: Player): Optional<ItemStack> = Optional.empty<ItemStack>()
    override fun safeTake(i: Int, j: Int, player: Player): ItemStack = ItemStack.EMPTY
    override fun safeInsert(itemStack: ItemStack): ItemStack = itemStack
    override fun safeInsert(itemStack: ItemStack, i: Int): ItemStack = this.safeInsert(itemStack)
    override fun allowModification(player: Player): Boolean = false
    override fun mayPlace(itemStack: ItemStack): Boolean = false
    override fun remove(i: Int): ItemStack = ItemStack.EMPTY
    override fun isHighlightable(): Boolean = false
    override fun isFake(): Boolean = true
    override fun onQuickCraft(itemStack: ItemStack, itemStack2: ItemStack) {
    }

    override fun onTake(player: Player, itemStack: ItemStack) {
    }

    override fun getBackground(): Identifier = CustomSlotBackground.EMPTY_SLOT

}
