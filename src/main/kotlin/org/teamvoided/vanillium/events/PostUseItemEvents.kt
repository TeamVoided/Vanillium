package org.teamvoided.vanillium.events

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory.createArrayBacked
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

object PostUseItemEvents {
    @JvmField
    val POST_USE: Event<PostUseItemCallback> = createArrayBacked(PostUseItemCallback::class.java) { listeners ->
        PostUseItemCallback { returned, world, player, hand ->
            listeners.forEach { it.interact(returned, world, player, hand) }
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
    val POST_USING: Event<PostUsingItemCallback> = createArrayBacked(PostUsingItemCallback::class.java) { listeners ->
        PostUsingItemCallback { returned, usedSTack, world, player ->
            listeners.forEach { it.interact(returned, usedSTack, world, player) }
        }
    }

    @JvmField
    val POST_STOP_USING: Event<PostStopUsingItemCallback> =
        createArrayBacked(PostStopUsingItemCallback::class.java) { listeners ->
            PostStopUsingItemCallback { stack, world, user, remainingUseTicks ->
                listeners.forEach { it.interact(stack, world, user, remainingUseTicks) }
            }
        }

    fun interface PostUseItemCallback {
        fun interact(returned: TypedActionResult<ItemStack>, world: World, player: PlayerEntity, hand: Hand)
    }

    fun interface PostUseOnBlockItemCallback {
        fun interact(returned: ActionResult, context: ItemUsageContext)
    }

    fun interface PostUseOnEntityItemCallback {
        fun interact(returned: ActionResult, world: PlayerEntity, player: LivingEntity, hand: Hand)
    }

    fun interface PostUsingItemCallback {
        fun interact(returned: ItemStack, usedStack: ItemStack, world: World, player: LivingEntity)
    }

    fun interface PostStopUsingItemCallback {
        fun interact(usedStack: ItemStack, world: World, player: LivingEntity, remainingUseTicks: Int)
    }
}

