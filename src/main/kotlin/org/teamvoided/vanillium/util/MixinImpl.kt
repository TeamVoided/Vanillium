package org.teamvoided.vanillium.util

import net.minecraft.world.effect.MobEffectInstance
import org.teamvoided.vanillium.Vanillium.config
import org.teamvoided.vanillium.mixin.accessors.MobEffectInstanceAccessor

fun modifyPotionEffectDuration(original: List<MobEffectInstance>): List<MobEffectInstance> {
    val newEffects = mutableListOf<MobEffectInstance>()

    for (effect in original) {
        val effect2 = MobEffectInstance(effect)
        val duration = (effect.duration.toFloat() * config.extendedPotionDuration.get()).toInt()
        @Suppress("KotlinConstantConditions")
        (effect2 as MobEffectInstanceAccessor).vnl_setDuration(duration)
        newEffects.add(effect2)
    }
    return newEffects
}