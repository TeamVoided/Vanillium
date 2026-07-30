package org.teamvoided.vanillium.client.item

import net.minecraft.client.gui.screens.Screen
import org.jetbrains.annotations.ApiStatus

@Suppress("FunctionName")
interface ExtendedTooltipContext {
    fun vnl_currentScreen(): Screen?

    @ApiStatus.Internal
    fun vnl_setScreen(screen: Screen?)
}