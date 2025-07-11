package org.teamvoided.vanillium.init

import net.fabricmc.fabric.api.event.player.UseItemCallback
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents
import net.minecraft.core.component.DataComponents
import net.minecraft.core.component.DataComponents.CHARGED_PROJECTILES
import net.minecraft.core.component.DataComponents.MAX_STACK_SIZE
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.SlotAccess
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ClickAction
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.ThrowablePotionItem
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.ShulkerBoxBlock
import org.teamvoided.vanillium.Vanillium.config
import org.teamvoided.vanillium.events.InventoryItemEvents
import org.teamvoided.vanillium.events.PostUseItemEvents
import org.teamvoided.vanillium.inventory.itemOnShulker
import org.teamvoided.vanillium.inventory.playInsertSound
import org.teamvoided.vanillium.inventory.shulkerOnItem
import org.teamvoided.vanillium.util.openShulker

object VnlEvents {
    fun init() {
        DefaultItemComponentEvents.MODIFY.register { ctx ->
            for ((item, count) in config.customStackSizes) {
                ctx.modify(item) { it.set(MAX_STACK_SIZE, count) }
            }
            for ((item, damage) in config.customMaxDamage) {
                ctx.modify(item) { it.set(DataComponents.MAX_DAMAGE, damage) }
            }
        }

        UseItemCallback.EVENT.register(::onUseItem)
        cooldownEvents()
        InventoryItemEvents.ON_CLICKED_ON_OTHER.register(::onClickedOnOther)
        InventoryItemEvents.ON_CLICKED.register(::onClicked)
    }

    fun onUseItem(player: Player, world: Level, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        if (!player.isSpectator && config.canOpenSkulkersWhenInHand) {
            val stack = player.getItemInHand(hand)
            val item = stack.item
            if (item is BlockItem && item.block is ShulkerBoxBlock) {
                if (player is ServerPlayer) player.openShulker(stack, player.inventory.selected)
                playInsertSound(player)
                InteractionResultHolder.success(stack)
            }
        }

        return InteractionResultHolder.pass(ItemStack.EMPTY)
    }

    fun onClickedOnOther(stack: ItemStack, otherSlot: Slot, clickAction: ClickAction, player: Player): Boolean? {
        if (config.shulkerInventoryInsert) {
            val value = shulkerOnItem(stack, otherSlot, clickAction, player)
            if (value != null) return value
        }

        return null
    }

    fun onClicked(
        stack: ItemStack, otherStack: ItemStack, thisSlot: Slot,
        clickAction: ClickAction, player: Player, cursorSlot: SlotAccess,
    ): Boolean? {
        val value = itemOnShulker(stack, otherStack, thisSlot, clickAction, player, cursorSlot)
        if (value != null) return value
        return null
    }

    fun cooldownEvents() {
        PostUseItemEvents.POST_USE.register { result, world, player, hand ->
            if (!result.result.noAction() && shouldCauseCooldown(player)) {
                val stack = result.getObject()
                if (
                    (stack.item !is ThrowablePotionItem && stack.useAnimation == UseAnim.NONE) ||
                    (stack.get(CHARGED_PROJECTILES)?.isEmpty == false && stack.useOnRelease())
                ) {
                    cooldown(stack, player)
                }
            }
        }
        PostUseItemEvents.POST_USE_ON_BLOCK.register { result, ctx ->
            val player = ctx.player
            if (player != null && shouldCauseCooldown(player) && !result.noAction() && result != InteractionResult.CONSUME_PARTIAL) {
                cooldown(ctx.itemInHand, player)
            }
        }

        PostUseItemEvents.POST_USE_ON_ENTITY.register { returned, player, entity, hand ->
            if (shouldCauseCooldown(player) && !returned.noAction()) {
                cooldown(player.getItemInHand(hand), player)
            }
        }

        PostUseItemEvents.POST_FINISH_USING.register { transformedStack, stack, world, player ->
            if (player is Player && shouldCauseCooldown(player)) {
                cooldown(stack, player)
            }
        }

        PostUseItemEvents.POST_RELEASE_USING.register { stack, world, player, remainingUseTicks ->
            if (player is Player && shouldCauseCooldown(player) &&
                (stack.useOnRelease() || (stack.useAnimation != UseAnim.EAT && stack.useAnimation != UseAnim.DRINK))
            ) {
                cooldown(stack, player)
            }
        }
    }

    fun InteractionResult.noAction() = this == InteractionResult.FAIL || this == InteractionResult.PASS
    fun shouldCauseCooldown(player: Player) = config.enableCooldownsInCreative || !player.isCreative
    fun cooldown(stack: ItemStack, player: Player) {
        if (stack.isEmpty) return
        val item = stack.item
        val cooldown = config.customCooldowns[item]
        if (cooldown != null) player.cooldowns.let {
            if (it.isOnCooldown(item)) {
                it.removeCooldown(item)
            }
            it.addCooldown(item, cooldown)
        }
    }
}