package org.teamvoided.vanillium.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.Gui;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.objects.AtlasSprite;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;

import static org.teamvoided.vanillium.Vanillium.mc;


@Mixin(PotionContents.class)
public class PotionContentsMixin {

    @WrapOperation(
            method = "addPotionTooltip",
            at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V", ordinal = 0)
    )
    private static <T> void addLittleCuteIcons(
            Consumer<T> instance, T text, Operation<Void> original, @Local Holder<MobEffect> holder) {
        original.call(instance,
                Component.object(new AtlasSprite(mc("gui"), Gui.getMobEffectSprite(holder)))
                        .append(Component.literal(" "))
                        .append((MutableComponent) text)
        );
    }

}
