package org.teamvoided.vanillium.inventory

import net.minecraft.core.component.DataComponents.CONTAINER
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents.SHULKER_BOX_OPEN
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.SlotAccess
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ClickAction
import net.minecraft.world.inventory.PlayerEnderChestContainer
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemContainerContents
import net.minecraft.world.level.block.ShulkerBoxBlock
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity.CONTAINER_SIZE
import org.teamvoided.vanillium.Vanillium.config
import org.teamvoided.vanillium.util.openShulker
import kotlin.math.min

fun Entity.playInsertSound() = playSound(SHULKER_BOX_OPEN, 0.4f, 0.9f + level().getRandom().nextFloat() * 0.4f)

fun itemOnShulker(
    stack: ItemStack, otherStack: ItemStack, thisSlot: Slot,
    clickAction: ClickAction, player: Player, access: SlotAccess,
): Boolean? {
    if (clickAction != ClickAction.SECONDARY) return null
    if (!thisSlot.allowModification(player)) return null

    val inputStack = access.get()

    val item = stack.item
    if (item !is BlockItem || item.block !is ShulkerBoxBlock) return null

    val contentsData = stack.get(CONTAINER) ?: return null

    val slotIdx = when (thisSlot.container) {
        is Inventory -> thisSlot.containerSlot
        is PlayerEnderChestContainer -> -1
        else -> null
    }
    if (inputStack.isEmpty && config.canOpenSkulkersInInventor && slotIdx != null) {

        if (player is ServerPlayer) {
            player.closeContainer()
            println(thisSlot.asString())
            player.openShulker(stack, slotIdx)
        }
        else {
            player.closeContainer()
            println("GaaaA!")
        }
        player.playInsertSound()
        return true
    }
    if (!config.shulkerInventoryInsert) return null
    if (!inputStack.item.canFitInsideContainerItems()) return null

    val originInventory = contentsData.stream().toList()
    val inventory = tryToAdd(originInventory.toMutableList(), access, player)
    if (inventory != originInventory) {
        stack.set(CONTAINER, ItemContainerContents.fromItems(inventory))
        player.playInsertSound()
        return true
    }
    return false
}

fun Slot.asString(): String {
    return "${javaClass.simpleName}(${containerSlot}, ${container}, ${index})"
}

fun tryToAdd(inventory: MutableList<ItemStack>, access: SlotAccess, player: Player): MutableList<ItemStack> {
    for (idx in 0 until CONTAINER_SIZE) {
        val slotItem = access.get()
        if (slotItem.isEmpty) break
        if (idx >= inventory.size) {
            inventory.add(access.get())
            access.set(ItemStack.EMPTY)
            break
        }

        val invStack = inventory[idx]
        if (invStack.isEmpty) {
            inventory[idx] = access.get()
            access.set(ItemStack.EMPTY)
            break
        }

        if (invStack.count != invStack.maxStackSize && ItemStack.isSameItemSameComponents(invStack, access.get())) {
            val stack = access.get()
            val count = min(stack.count, invStack.maxStackSize - invStack.count)
            inventory[idx] = invStack.copyWithCount(invStack.count + count)
            access.set(stack.copyWithCount(stack.count - count))
            if (access.get().isEmpty) break
        }
    }

    return inventory
}

fun shulkerOnItem(stack: ItemStack, otherSlot: Slot, clickAction: ClickAction, player: Player): Boolean? {
    if (clickAction != ClickAction.SECONDARY) return null
    if (!otherSlot.allowModification(player)) return null

    if (otherSlot.item.isEmpty || !otherSlot.item.item.canFitInsideContainerItems()) return null

    val item = stack.item
    if (item !is BlockItem || item.block !is ShulkerBoxBlock) return null

    val contentsData = stack.get(CONTAINER) ?: return null
    val originInventory = contentsData.stream().toList()
    val inventory = tryToAdd(originInventory.toMutableList(), otherSlot, player)
    if (inventory != originInventory) {
        stack.set(CONTAINER, ItemContainerContents.fromItems(inventory))
        player.playInsertSound()
        return true
    }
    return false
}

fun tryToAdd(
    inventory: MutableList<ItemStack>,
    otherSlot: Slot,
    player: Player,
): MutableList<ItemStack> {
    for (idx in 0 until CONTAINER_SIZE) {
        val slotItem = otherSlot.item
        if (slotItem.isEmpty) break
        if (idx >= inventory.size) {
            inventory.add(otherSlot.safeTake(slotItem.count, slotItem.count, player))
            if (otherSlot.item.isEmpty) break
        }

        if (inventory[idx].isEmpty) {
            inventory[idx] = otherSlot.safeTake(slotItem.count, slotItem.count, player)
            break
        }

        val invStack = inventory[idx]
        if (invStack.count != invStack.maxStackSize && ItemStack.isSameItemSameComponents(invStack, otherSlot.item)) {
            val count = invStack.maxStackSize - invStack.count
            val leftover = otherSlot.safeTake(otherSlot.item.count, count, player).count
            inventory[idx] = invStack.copyWithCount(invStack.count + leftover)
            if (otherSlot.item.isEmpty) break
        }
    }

    return inventory
}