package org.teamvoided.vanillium.events

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory.createArrayBacked
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

object PostUseItemEvents {
    fun interface PostUseItemCallback {
        fun interact(original: TypedActionResult<ItemStack>, world: World, player: PlayerEntity, hand: Hand)
    }

    @JvmField
    val EVENT: Event<PostUseItemCallback> = createArrayBacked(PostUseItemCallback::class.java) { listeners ->
        PostUseItemCallback { original: TypedActionResult<ItemStack>, world: World, player: PlayerEntity, hand: Hand ->
            listeners.forEach { it.interact(original, world, player, hand) }
        }
    }
}

