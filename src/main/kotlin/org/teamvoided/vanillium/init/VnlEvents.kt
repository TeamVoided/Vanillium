package org.teamvoided.vanillium.init

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.DataComponentTypes.MAX_STACK_SIZE
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ThrowablePotionItem
import net.minecraft.util.ActionResult
import net.minecraft.util.UseAction
import org.teamvoided.vanillium.Vanillium.config
import org.teamvoided.vanillium.events.PostUseItemEvents

object VnlEvents {
    fun init() {
        DefaultItemComponentEvents.MODIFY.register { ctx ->
            for ((item, count) in config.customStackSizes) {
                ctx.modify(item) { it.put(MAX_STACK_SIZE, count) }
            }
        }
        postUse()
    }

    fun postUse() {
        PostUseItemEvents.POST_USE.register { result, world, player, hand ->
            if (!result.result.noAction() && shouldCauseCooldown(player)) {
                val stack = result.value
                if (
                    (stack.item !is ThrowablePotionItem && stack.useAction == UseAction.NONE) ||
                    (stack.get(DataComponentTypes.CHARGED_PROJECTILES)?.isEmpty == false && stack.isUsedOnRelease)
                ) {
                    cooldown(result.value, player)
                }
            }
        }
        PostUseItemEvents.POST_USE_ON_BLOCK.register { result, ctx ->
            val player = ctx.player
            if (player != null && shouldCauseCooldown(player) && !result.noAction()) {
                cooldown(ctx.stack, player)
            }
        }

        PostUseItemEvents.POST_USE_ON_ENTITY.register { returned, player, entity, hand ->
            if (shouldCauseCooldown(player) && !returned.noAction()) {
                cooldown(player.getStackInHand(hand), player)
            }
        }

        PostUseItemEvents.POST_USING.register { transformedStack, stack, world, player ->
            if (player is PlayerEntity && shouldCauseCooldown(player)) {
                cooldown(stack, player)
            }
        }

        PostUseItemEvents.POST_STOP_USING.register { stack, world, player, remainingUseTicks ->
            if (player is PlayerEntity && shouldCauseCooldown(player) &&
                (stack.isUsedOnRelease || (stack.useAction != UseAction.EAT && stack.useAction != UseAction.DRINK))
            ) {
                cooldown(stack, player)
            }
        }
    }

    fun ActionResult.noAction() = this == ActionResult.FAIL || this == ActionResult.PASS
    fun shouldCauseCooldown(player: PlayerEntity) = config.enableCooldownsInCreative || !player.isCreative
    fun cooldown(stack: ItemStack, player: PlayerEntity) {
        if (stack.isEmpty) return
        val cooldown = config.customCooldowns[stack.item]
        if (cooldown != null) {
            player.itemCooldownManager.set(stack.item, cooldown)
        }
    }
}