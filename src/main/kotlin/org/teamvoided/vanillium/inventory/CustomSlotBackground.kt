package org.teamvoided.vanillium.inventory

import net.minecraft.resources.Identifier
import org.teamvoided.vanillium.Vanillium.id

interface CustomSlotBackground {

    fun getBackground(): Identifier

    @Suppress("unused")
    companion object {

        val LOCKED_SLOT = id("container/slot/locked_slot")
        val EMPTY_SLOT = id("container/slot/empty_slot")

    }
}
