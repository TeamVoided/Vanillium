package org.teamvoided.vanillium.mixin.api;

import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.teamvoided.vanillium.client.item.ExtendedTooltipContext;

@Mixin(targets = {
        "net.minecraft.world.item.Item$TooltipContext$1",
        "net.minecraft.world.item.Item$TooltipContext$2",
        "net.minecraft.world.item.Item$TooltipContext$3"
})
public abstract class TooltipContextImplMixin implements ExtendedTooltipContext {

    @Unique
    private @Nullable Screen vanillium$currentScreen = null;

    @Override
    public @Nullable Screen vnl_currentScreen() {
        return vanillium$currentScreen;
    }

    @Override
    public void vnl_setScreen(@Nullable Screen screen) {
        vanillium$currentScreen = screen;
    }
}