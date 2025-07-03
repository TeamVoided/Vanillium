package org.teamvoided.vanillium.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.teamvoided.vanillium.events.PostUseItemEvents;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @ModifyReturnValue(method = "use", at = @At("RETURN"))
    private TypedActionResult<ItemStack> run(TypedActionResult<ItemStack> original, World world, PlayerEntity player, Hand hand) {
        PostUseItemEvents.POST_USE.invoker().interact(original, world, player, hand);
        return original;
    }

    @ModifyReturnValue(method = "useOnBlock", at = @At("RETURN"))
    private ActionResult run(ActionResult original, ItemUsageContext context) {
        PostUseItemEvents.POST_USE_ON_BLOCK.invoker().interact(original, context);
        return original;
    }

    @ModifyReturnValue(method = "useOnEntity", at = @At("RETURN"))
    private ActionResult run(ActionResult original, PlayerEntity user, LivingEntity entity, Hand hand) {
        PostUseItemEvents.POST_USE_ON_ENTITY.invoker().interact(original, user, entity, hand);
        return original;
    }

    @ModifyReturnValue(method = "finishUsing", at = @At("RETURN"))
    private ItemStack run(ItemStack original, World world, LivingEntity user) {
        PostUseItemEvents.POST_USING.invoker().interact(original, (ItemStack) (Object) this, world, user);
        return original;
    }

    @Inject(method = "onStoppedUsing", at = @At("TAIL"))
    private void run(World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
        PostUseItemEvents.POST_STOP_USING.invoker().interact((ItemStack) (Object) this, world, user, remainingUseTicks);
    }
}
