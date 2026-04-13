package org.teamvoided.vanillium.inventory

import net.minecraft.network.chat.Component
import net.minecraft.world.Container
import net.minecraft.world.MenuProvider
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ShulkerBoxSlot
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import org.teamvoided.vanillium.init.VnlMenus

class QuickShulkerBoxMenu(i: Int, inventory: Inventory, var container: Container, val blockedSlot: Int) :
    AbstractContainerMenu(VnlMenus.QUICK_SHULKER, i) {
    constructor(i: Int, inventory: Inventory) : this(i, inventory, SimpleContainer(CONTAINER_SIZE), -1)

    init {
        checkContainerSize(container, CONTAINER_SIZE)
        this.container = container
        container.startOpen(inventory.player)
        val j = 3
        val k = 9

        // Shulker
        for (l in 0..2) {
            for (m in 0..8) {
                this.addSlot(ShulkerBoxSlot(container, m + l * 9, 8 + m * 18, 18 + l * 18))
            }
        }

        // Player Inv
        for (l in 0..2) {
            for (m in 0..8) {
                slot(inventory, m + l * 9 + 9, 8 + m * 18, 84 + l * 18)
            }
        }


        // Hotbar
        for (slotId in 0..8) {
            slot(inventory, slotId, 8 + slotId * 18, 142)
        }
    }

    fun slot(container: Container, slotIdx: Int, x: Int, y: Int) {
        if (slotIdx == blockedSlot) this.addSlot(NonInteractiveSlot(container, slotIdx, x, y))
        else this.addSlot(Slot(container, slotIdx, x, y))
    }

    override fun stillValid(player: Player): Boolean = this.container.stillValid(player)
    override fun quickMoveStack(player: Player, i: Int): ItemStack {
        var itemStack = ItemStack.EMPTY
        val slot: Slot? = this.slots[i]
        if (slot != null && slot.hasItem()) {
            val itemStack2 = slot.item
            itemStack = itemStack2.copy()
            if (i < this.container.containerSize) {
                if (!this.moveItemStackTo(itemStack2, this.container.containerSize, this.slots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!this.moveItemStackTo(itemStack2, 0, this.container.containerSize, false)) {
                return ItemStack.EMPTY
            }

            if (itemStack2.isEmpty) {
                slot.setByPlayer(ItemStack.EMPTY)
            } else {
                slot.setChanged()
            }
        }

        return itemStack
    }

    override fun removed(player: Player) {
        super.removed(player)
        this.container.stopOpen(player)
    }

    companion object {
        private const val CONTAINER_SIZE = 27

        fun shulkerMenuProvider(stack: ItemStack, blockedSlot: Int): MenuProvider = object : MenuProvider {
            override fun getDisplayName(): Component = stack.hoverName
            override fun createMenu(i: Int, inventory: Inventory, player: Player): AbstractContainerMenu {
                return QuickShulkerBoxMenu(i, inventory, SimpleStackBasedContainer(stack, CONTAINER_SIZE), blockedSlot)
            }
        }
    }
}