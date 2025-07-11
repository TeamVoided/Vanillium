package org.teamvoided.vanillium.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

import static org.teamvoided.vanillium.util.MixinImplKt.modifyPotionEffectDuration;

@Mixin(Potion.class)
public class PotionMixin {
    @ModifyReturnValue(method = "getEffects", at = @At("RETURN"))
    List<MobEffectInstance> multiplyEffectDuration(List<MobEffectInstance> original) {
        return modifyPotionEffectDuration(original);
    }
}
