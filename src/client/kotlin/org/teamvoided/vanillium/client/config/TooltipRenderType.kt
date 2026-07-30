package org.teamvoided.vanillium.client.config

import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.tags.TagKey
import net.minecraft.world.inventory.MenuType
import org.teamvoided.vanillium.util.getTypeHolder

enum class TooltipRenderType {
    IN_MENU_TAG_OR_SCREEN,
    IN_MENU_TAG,
    IN_SCREENS,
    ALWAYS,
    NEVER;

    inline fun <reified T : Screen> shouldRender(screen: Screen?, tag: TagKey<MenuType<*>>): Boolean {
        return when (this) {
            IN_MENU_TAG_OR_SCREEN -> screen is T || screen.inTag(tag)
            IN_MENU_TAG -> screen.inTag(tag)
            IN_SCREENS -> screen is T
            ALWAYS -> true
            NEVER -> false
        }
    }

    fun Screen?.inTag(tag: TagKey<MenuType<*>>): Boolean =
        this is AbstractContainerScreen<*> && getTypeHolder(this.menu)?.`is`(tag) == true

}