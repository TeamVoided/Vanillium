package org.teamvoided.vanillium.mixin.api;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Screen.class)
public class ScreenMixin {
    @ModifyExpressionValue(method = "getTooltipFromItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$TooltipContext;of(Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/item/Item$TooltipContext;"))
   private static Item.TooltipContext addExtraContext(Item.TooltipContext original) {
        original.vnl_setScreen(Minecraft.getInstance().screen);
        return original;
    }
}
