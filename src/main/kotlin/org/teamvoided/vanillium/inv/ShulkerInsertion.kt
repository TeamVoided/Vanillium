package org.teamvoided.vanillium.inv

import net.minecraft.block.ShulkerBoxBlock
import net.minecraft.block.entity.ShulkerBoxBlockEntity.CONTAINER_SIZE
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.ContainerContentsComponent
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.StackReference
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ClickType
import kotlin.math.min

fun playInsertSound(entity: Entity) =
    entity.playSound(SoundEvents.BLOCK_SHULKER_BOX_OPEN, 0.4f, 0.9f + entity.world.getRandom().nextFloat() * 0.4f)


fun itemOnShulker(
    stack: ItemStack, otherStack: ItemStack, thisSlot: Slot,
    clickType: ClickType, player: PlayerEntity, reference: StackReference,
): Boolean? {
    if (clickType != ClickType.RIGHT) return null

    val inputStack = reference.get()
    if (inputStack.isEmpty || !inputStack.item.canBeNested()) return null

    val item = stack.item
    if (item !is BlockItem || item.block !is ShulkerBoxBlock) return null

    val contentsData = stack.get(DataComponentTypes.CONTAINER) ?: return null
    val originInventory = contentsData.stream().toList()
    val inventory = tryToAdd(originInventory.toMutableList(), reference, player)
    if (inventory != originInventory) {
        stack.set(DataComponentTypes.CONTAINER, ContainerContentsComponent.fromStacks(inventory))
        playInsertSound(player)
        return true
    }
    return false
}

fun tryToAdd(inventory: MutableList<ItemStack>, ref: StackReference, player: PlayerEntity): MutableList<ItemStack> {
    for (idx in 0 until CONTAINER_SIZE) {
        val slotItem = ref.get()
        if (slotItem.isEmpty) break
        if (idx >= inventory.size) {
            inventory.add(ref.get())
            ref.set(ItemStack.EMPTY)
            break
        }

        val invStack = inventory[idx]
        if (invStack.isEmpty) {
            inventory[idx] = ref.get()
            ref.set(ItemStack.EMPTY)
            break
        }

        if (invStack.count != invStack.maxCount && ItemStack.itemsAndComponentsMatch(invStack, ref.get())) {
            val stack = ref.get()
            val count = min(stack.count, invStack.maxCount - invStack.count)
            inventory[idx] = invStack.copyWithCount(invStack.count + count)
            ref.set(stack.copyWithCount(stack.count - count))
            if (ref.get().isEmpty) break
        }
    }

    return inventory
}

fun shulkerOnItem(stack: ItemStack, otherSlot: Slot, clickType: ClickType, player: PlayerEntity): Boolean? {
    if (clickType != ClickType.RIGHT) return null

    if (otherSlot.stack.isEmpty || !otherSlot.stack.item.canBeNested()) return null

    val item = stack.item
    if (item !is BlockItem || item.block !is ShulkerBoxBlock) return null

    val contentsData = stack.get(DataComponentTypes.CONTAINER) ?: return null
    val originInventory = contentsData.stream().toList()
    val inventory = tryToAdd(originInventory.toMutableList(), otherSlot, player)
    if (inventory != originInventory) {
        stack.set(DataComponentTypes.CONTAINER, ContainerContentsComponent.fromStacks(inventory))
        playInsertSound(player)
        return true
    }
    return false
}

fun tryToAdd(
    inventory: MutableList<ItemStack>,
    otherSlot: Slot,
    player: PlayerEntity,
): MutableList<ItemStack> {
    for (idx in 0 until CONTAINER_SIZE) {
        val slotItem = otherSlot.stack
        if (slotItem.isEmpty) break
        if (idx >= inventory.size) {
            inventory.add(otherSlot.takeStackRange(slotItem.count, slotItem.count, player))
            if (otherSlot.stack.isEmpty) break
        }

        if (inventory[idx].isEmpty) {
            inventory[idx] = otherSlot.takeStackRange(slotItem.count, slotItem.count, player)
            break
        }

        val invStack = inventory[idx]
        if (invStack.count != invStack.maxCount && ItemStack.itemsAndComponentsMatch(invStack, otherSlot.stack)) {
            val count = invStack.maxCount - invStack.count
            val leftover = otherSlot.takeStackRange(otherSlot.stack.count, count, player).count
            inventory[idx] = invStack.copyWithCount(invStack.count + leftover)
            if (otherSlot.stack.isEmpty) break
        }
    }

    return inventory
}