package org.teamvoided.vanillium.client.config

import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen

enum class TooltipRenderType {
    IN_FURNACE_SCREENS, ALWAYS, NEVER;

    fun shouldRender(screen: Screen?): Boolean {
        return when (this) {
            IN_FURNACE_SCREENS -> screen is AbstractFurnaceScreen<*>
            ALWAYS -> true
            NEVER -> false
        }
    }

}