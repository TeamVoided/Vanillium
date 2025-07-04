package org.teamvoided.vanillium.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.teamvoided.vanillium.events.InventoryItemEvents;
import org.teamvoided.vanillium.events.PostUseItemEvents;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @ModifyReturnValue(method = "use", at = @At("RETURN"))
    private InteractionResultHolder<ItemStack> postUseHook(InteractionResultHolder<ItemStack> original, Level world, Player player, InteractionHand hand) {
        PostUseItemEvents.POST_USE.invoker().interact(original, world, player, hand);
        return original;
    }

    @ModifyReturnValue(method = "useOn", at = @At("RETURN"))
    private InteractionResult postUseOnBlockHook(InteractionResult original, UseOnContext context) {
        PostUseItemEvents.POST_USE_ON_BLOCK.invoker().interact(original, context);
        return original;
    }

    @ModifyReturnValue(method = "interactLivingEntity", at = @At("RETURN"))
    private InteractionResult postUseOnEntityHook(InteractionResult original, Player user, LivingEntity entity, InteractionHand hand) {
        PostUseItemEvents.POST_USE_ON_ENTITY.invoker().interact(original, user, entity, hand);
        return original;
    }

    @ModifyReturnValue(method = "finishUsingItem", at = @At("RETURN"))
    private ItemStack postFinishUsingHook(ItemStack original, Level world, LivingEntity user) {
        PostUseItemEvents.POST_FINISH_USING.invoker().interact(original, (ItemStack) (Object) this, world, user);
        return original;
    }

    @Inject(method = "releaseUsing", at = @At("TAIL"))
    private void postOnStoppedUsingHook(Level world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
        PostUseItemEvents.POST_RELEASE_USING.invoker().interact((ItemStack) (Object) this, world, user, remainingUseTicks);
    }

    @WrapOperation(method = "overrideStackedOnOther", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;overrideStackedOnOther(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/inventory/Slot;Lnet/minecraft/world/inventory/ClickAction;Lnet/minecraft/world/entity/player/Player;)Z"))
    boolean onClickedOnOtherHook(Item instance, ItemStack thisStack, Slot otherSlot, ClickAction clickType, Player player, Operation<Boolean> original) {
        var bool = InventoryItemEvents.ON_CLICKED_ON_OTHER.invoker().interact(thisStack, otherSlot, clickType, player);
        if (bool != null) return bool;
        return original.call(instance, thisStack, otherSlot, clickType, player);
    }

    @WrapOperation(method = "overrideOtherStackedOnMe", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;overrideOtherStackedOnMe(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/inventory/Slot;Lnet/minecraft/world/inventory/ClickAction;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/SlotAccess;)Z"))
    boolean onClickedOnOtherHook(Item instance, ItemStack thisStack, ItemStack otherStack, Slot thisSlot, ClickAction clickType, Player player, SlotAccess cursorStackReference, Operation<Boolean> original) {
        var bool = InventoryItemEvents.ON_CLICKED.invoker().interact(thisStack, otherStack, thisSlot, clickType, player, cursorStackReference);
        if (bool != null) return bool;
        return original.call(instance, thisStack, otherStack, thisSlot, clickType, player, cursorStackReference);
    }
}
