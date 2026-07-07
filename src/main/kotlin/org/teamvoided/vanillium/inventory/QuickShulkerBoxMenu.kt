package org.teamvoided.vanillium.inventory

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.Container
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
    constructor(i: Int, inventory: Inventory, data: QuickShulkerData)
            : this(i, inventory, SimpleContainer(CONTAINER_SIZE), data.lockedSlot)

    init {
        checkContainerSize(container, CONTAINER_SIZE)
        container = container
        container.startOpen(inventory.player)
        val j = 3
        val k = 9

        // Shulker
        for (l in 0..2) {
            for (m in 0..8) {
                addSlot(ShulkerBoxSlot(container, m + l * 9, 8 + m * 18, 18 + l * 18))
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
        if (slotIdx == blockedSlot) addSlot(NonInteractiveSlot(container, slotIdx, x, y))
        else addSlot(Slot(container, slotIdx, x, y))
    }

    override fun stillValid(player: Player): Boolean = container.stillValid(player)

    override fun quickMoveStack(player: Player, i: Int): ItemStack {
        var itemStack = ItemStack.EMPTY
        val slot: Slot? = slots[i]
        if (slot != null && slot.hasItem()) {
            val itemStack2 = slot.item
            itemStack = itemStack2.copy()
            if (i < container.containerSize) {
                if (!moveItemStackTo(itemStack2, container.containerSize, slots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!moveItemStackTo(itemStack2, 0, container.containerSize, false)) {
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
        container.stopOpen(player)
    }

    companion object {
        private const val CONTAINER_SIZE = 27


        @JvmRecord
        data class QuickShulkerData(val lockedSlot: Int) {
            companion object {
                val PACKET_CODEC: StreamCodec<FriendlyByteBuf, QuickShulkerData> =
                    StreamCodec.composite(ByteBufCodecs.INT, QuickShulkerData::lockedSlot, ::QuickShulkerData)
            }
        }

        fun shulkerMenuProvider(stack: ItemStack, blockedSlot: Int): ExtendedScreenHandlerFactory<QuickShulkerData> =
            object : ExtendedScreenHandlerFactory<QuickShulkerData> {
                override fun getDisplayName(): Component = stack.hoverName
                override fun createMenu(i: Int, inventory: Inventory, player: Player): AbstractContainerMenu {
                    return QuickShulkerBoxMenu(i, inventory, SimpleStackBasedContainer(stack, CONTAINER_SIZE), blockedSlot)
                }

                override fun getScreenOpeningData(player: ServerPlayer): QuickShulkerData {
                    return QuickShulkerData(blockedSlot)
                }

                override fun shouldCloseCurrentScreen(): Boolean {
                    return stack.isEmpty
                }
            }
    }
}