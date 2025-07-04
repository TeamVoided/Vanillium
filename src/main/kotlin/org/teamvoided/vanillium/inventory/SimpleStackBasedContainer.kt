package org.teamvoided.vanillium.inventory

import net.minecraft.core.component.DataComponents.CONTAINER
import net.minecraft.world.SimpleContainer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemContainerContents

class SimpleStackBasedContainer(val stack: ItemStack, size: Int) : SimpleContainer(size) {

    init {
        val container = stack.get(CONTAINER)
        if (container != null) {
            for ((idx, stack) in container.stream().toList().withIndex()) {
                setItem(idx, stack)
            }
        }
    }

    override fun setChanged() {
        super.setChanged()
        stack.set(CONTAINER, ItemContainerContents.fromItems(items))
    }
}
