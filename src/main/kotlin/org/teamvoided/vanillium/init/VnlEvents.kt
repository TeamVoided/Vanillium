package org.teamvoided.vanillium.init

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.DataComponentTypes.MAX_STACK_SIZE
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.StackReference
import net.minecraft.item.ItemStack
import net.minecraft.item.ThrowablePotionItem
import net.minecraft.screen.slot.Slot
import net.minecraft.util.ActionResult
import net.minecraft.util.ClickType
import net.minecraft.util.UseAction
import org.teamvoided.vanillium.Vanillium.config
import org.teamvoided.vanillium.events.InventoryItemEvents
import org.teamvoided.vanillium.events.PostUseItemEvents
import org.teamvoided.vanillium.inv.shulkerOnItem
import org.teamvoided.vanillium.inv.itemOnShulker

object VnlEvents {
    fun init() {
        DefaultItemComponentEvents.MODIFY.register { ctx ->
            for ((item, count) in config.customStackSizes) {
                ctx.modify(item) { it.put(MAX_STACK_SIZE, count) }
            }
        }
        cooldownEvents()
        InventoryItemEvents.ON_CLICKED_ON_OTHER.register(::onClickedOnOther)
        InventoryItemEvents.ON_CLICKED.register(::onClicked)
    }

    fun onClickedOnOther(stack: ItemStack, otherSlot: Slot, clickType: ClickType, player: PlayerEntity): Boolean? {
        if (config.shulkerInventoryInsert) {
            val value = shulkerOnItem(stack, otherSlot, clickType, player)
            if (value != null) return value
        }

        return null
    }

    fun onClicked(
        stack: ItemStack, otherStack: ItemStack, thisSlot: Slot,
        clickType: ClickType, player: PlayerEntity, reference: StackReference,
    ): Boolean? {
        if (config.shulkerInventoryInsert) {
            val value = itemOnShulker(stack, otherStack, thisSlot, clickType, player, reference)
            if (value != null) return value
        }

        return null
    }

    fun cooldownEvents() {
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