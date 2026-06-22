package org.teamvoided.vanillium.mixin.furnacefix;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FurnaceFuelSlot.class)
public class FurnaceFuelSlotMixin {

    @ModifyExpressionValue(method = "getMaxStackSize", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/FurnaceFuelSlot;isBucket(Lnet/minecraft/world/item/ItemStack;)Z"))
    private static boolean getMaxStackSizeRemain(boolean original, ItemStack stack) {
        return original || !stack.getItem().getRecipeRemainder(stack).isEmpty();
    }

}
