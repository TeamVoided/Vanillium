package org.teamvoided.vanillium.client.item

import net.minecraft.client.gui.screens.Screen
import org.jetbrains.annotations.ApiStatus

@Suppress("FunctionName")
interface ExtendedTooltipContext {
    fun vanillium_currentScreen(): Screen?

    @ApiStatus.Internal
    fun vanillium_setScreen(screen: Screen?)
}