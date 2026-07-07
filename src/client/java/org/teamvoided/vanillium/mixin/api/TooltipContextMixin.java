package org.teamvoided.vanillium.mixin.api;

import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.teamvoided.vanillium.client.item.ExtendedTooltipContext;

@Mixin(Item.TooltipContext.class)
public interface TooltipContextMixin extends ExtendedTooltipContext {
}