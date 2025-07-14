package org.teamvoided.vanillium.events

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory.createArrayBacked
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

object PostUseItemEvents {
    @JvmField
    val POST_USE: Event<PostUseItemCallback> = createArrayBacked(PostUseItemCallback::class.java) { listeners ->
        PostUseItemCallback { returned, stack, world, player, hand ->
            listeners.forEach { it.interact(returned,stack, world, player, hand) }
        }
    }

    @JvmField
    val POST_USE_ON_BLOCK: Event<PostUseOnBlockItemCallback> =
        createArrayBacked(PostUseOnBlockItemCallback::class.java) { listeners ->
            PostUseOnBlockItemCallback { returned, context ->
                listeners.forEach { it.interact(returned, context) }
            }
        }

    @JvmField
    val POST_USE_ON_ENTITY: Event<PostUseOnEntityItemCallback> =
        createArrayBacked(PostUseOnEntityItemCallback::class.java) { listeners ->
            PostUseOnEntityItemCallback { returned, player, entity, hand ->
                listeners.forEach { it.interact(returned, player, entity, hand) }
            }
        }

    @JvmField
    val POST_FINISH_USING: Event<PostUsingItemCallback> =
        createArrayBacked(PostUsingItemCallback::class.java) { listeners ->
            PostUsingItemCallback { returned, usedSTack, world, player ->
                listeners.forEach { it.interact(returned, usedSTack, world, player) }
            }
        }

    @JvmField
    val POST_RELEASE_USING: Event<PostStopUsingItemCallback> =
        createArrayBacked(PostStopUsingItemCallback::class.java) { listeners ->
            PostStopUsingItemCallback { stack, world, user, remainingUseTicks ->
                listeners.forEach { it.interact(stack, world, user, remainingUseTicks) }
            }
        }

    fun interface PostUseItemCallback {
        fun interact(returned: InteractionResult, stack: ItemStack, world: Level, player: Player, hand: InteractionHand)
    }

    fun interface PostUseOnBlockItemCallback {
        fun interact(returned: InteractionResult, context: UseOnContext)
    }

    fun interface PostUseOnEntityItemCallback {
        fun interact(returned: InteractionResult, world: Player, player: LivingEntity, hand: InteractionHand)
    }

    fun interface PostUsingItemCallback {
        fun interact(returned: ItemStack, usedStack: ItemStack, world: Level, player: LivingEntity)
    }

    fun interface PostStopUsingItemCallback {
        fun interact(usedStack: ItemStack, world: Level, player: LivingEntity, remainingUseTicks: Int)
    }
}

