package org.teamvoided.vanillium.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.teamvoided.vanillium.events.PostUseItemEvents;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @ModifyReturnValue(method = "use", at = @At("RETURN"))
    private TypedActionResult<ItemStack> run(TypedActionResult<ItemStack> original, World world, PlayerEntity player, Hand hand) {
        PostUseItemEvents.EVENT.invoker().interact(original, world, player, hand);
        return original;
    }
}
