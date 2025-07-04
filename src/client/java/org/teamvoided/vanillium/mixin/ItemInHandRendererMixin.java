package org.teamvoided.vanillium.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.teamvoided.vanillium.client.VanilliumClient.customUpdateAnim;


@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {
    @Shadow
    private ItemStack mainHandItem;

    @Shadow
    private ItemStack offHandItem;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "tick", at = @At("HEAD"))
    private void run(CallbackInfo info) {
        // Modify main hand
        ItemStack newMainStack = minecraft.player.getMainHandItem();

        if (mainHandItem.getItem() == newMainStack.getItem()) {
            if (!customUpdateAnim(minecraft.player, InteractionHand.MAIN_HAND, mainHandItem, newMainStack)) {
                mainHandItem = newMainStack;
            }
        }

        // Modify offhand
        ItemStack newOffStack = minecraft.player.getOffhandItem();

        if (offHandItem.getItem() == newOffStack.getItem()) {
            if (!customUpdateAnim(minecraft.player, InteractionHand.OFF_HAND, offHandItem, newOffStack)) {
                offHandItem = newOffStack;
            }
        }
    }
}
