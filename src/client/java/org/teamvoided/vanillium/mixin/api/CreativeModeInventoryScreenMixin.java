package org.teamvoided.vanillium.mixin.api;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeInventoryScreenMixin extends Screen {

    protected CreativeModeInventoryScreenMixin(Component component) {
        super(component);
    }

    @ModifyExpressionValue(method = "getTooltipFromContainerItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$TooltipContext;of(Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/item/Item$TooltipContext;"))
    Item.TooltipContext addExtraContext(Item.TooltipContext original){
        original.vanillium_setScreen(this);
        return original;
    }
}
