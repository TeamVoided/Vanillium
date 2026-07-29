package org.teamvoided.vanillium.client.config

import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen
import org.teamvoided.vanillium.data.tags.VnlMenuTags
import org.teamvoided.vanillium.util.getTypeHolder

enum class TooltipRenderType {
    IN_FURNACE_MENU_TAG_OR_SCREEN,
    IN_FURNACE_MENU_TAG,
    IN_FURNACE_SCREENS,
    ALWAYS,
    NEVER;

    fun shouldRender(screen: Screen?): Boolean {
        return when (this) {
            IN_FURNACE_MENU_TAG_OR_SCREEN -> screen is AbstractFurnaceScreen<*> || isInTag(screen)
            IN_FURNACE_MENU_TAG -> isInTag(screen)
            IN_FURNACE_SCREENS -> screen is AbstractFurnaceScreen<*>
            ALWAYS -> true
            NEVER -> false
        }
    }

    fun isInTag(screen: Screen?): Boolean =
        screen is AbstractContainerScreen<*> && getTypeHolder(screen.menu)?.`is`(VnlMenuTags.IS_FURNACE) == true

}