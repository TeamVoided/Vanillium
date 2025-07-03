package org.teamvoided.vanillium.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.teamvoided.vanillium.events.InventoryItemEvents;
import org.teamvoided.vanillium.events.PostUseItemEvents;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @ModifyReturnValue(method = "use", at = @At("RETURN"))
    private TypedActionResult<ItemStack> postUseHook(TypedActionResult<ItemStack> original, World world, PlayerEntity player, Hand hand) {
        PostUseItemEvents.POST_USE.invoker().interact(original, world, player, hand);
        return original;
    }

    @ModifyReturnValue(method = "useOnBlock", at = @At("RETURN"))
    private ActionResult postUseOnBlockHook(ActionResult original, ItemUsageContext context) {
        PostUseItemEvents.POST_USE_ON_BLOCK.invoker().interact(original, context);
        return original;
    }

    @ModifyReturnValue(method = "useOnEntity", at = @At("RETURN"))
    private ActionResult postUseOnEntityHook(ActionResult original, PlayerEntity user, LivingEntity entity, Hand hand) {
        PostUseItemEvents.POST_USE_ON_ENTITY.invoker().interact(original, user, entity, hand);
        return original;
    }

    @ModifyReturnValue(method = "finishUsing", at = @At("RETURN"))
    private ItemStack postFinishUsingHook(ItemStack original, World world, LivingEntity user) {
        PostUseItemEvents.POST_USING.invoker().interact(original, (ItemStack) (Object) this, world, user);
        return original;
    }

    @Inject(method = "onStoppedUsing", at = @At("TAIL"))
    private void postOnStoppedUsingHook(World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
        PostUseItemEvents.POST_STOP_USING.invoker().interact((ItemStack) (Object) this, world, user, remainingUseTicks);
    }

    @WrapOperation(method = "onClickedOnOther", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;onClickedOnOther(Lnet/minecraft/item/ItemStack;Lnet/minecraft/screen/slot/Slot;Lnet/minecraft/util/ClickType;Lnet/minecraft/entity/player/PlayerEntity;)Z"))
    boolean onClickedOnOtherHook(Item instance, ItemStack thisStack, Slot otherSlot, ClickType clickType, PlayerEntity player, Operation<Boolean> original) {
        var bool = InventoryItemEvents.ON_CLICKED_ON_OTHER.invoker().interact(thisStack, otherSlot, clickType, player);
        if (bool != null) return bool;
        return original.call(instance, thisStack, otherSlot, clickType, player);
    }

    @WrapOperation(method = "onClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;onClicked(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Lnet/minecraft/screen/slot/Slot;Lnet/minecraft/util/ClickType;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/inventory/StackReference;)Z"))
    boolean onClickedOnOtherHook(Item instance, ItemStack thisStack, ItemStack otherStack, Slot thisSlot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference, Operation<Boolean> original) {
        var bool = InventoryItemEvents.ON_CLICKED.invoker().interact(thisStack, otherStack, thisSlot, clickType, player, cursorStackReference);
        if (bool != null) return bool;
        return original.call(instance, thisStack, otherStack, thisSlot, clickType, player, cursorStackReference);
    }
}
