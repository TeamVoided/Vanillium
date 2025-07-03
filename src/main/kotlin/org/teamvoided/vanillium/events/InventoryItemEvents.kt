package org.teamvoided.vanillium.events

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory.createArrayBacked
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.StackReference
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot
import net.minecraft.util.ClickType

object InventoryItemEvents {

    /**
     * Callback for right-clicking on item from cursor.
     *
     *  Returning null will pass to vanilla behaviour
     *
     */
    @JvmField
    val ON_CLICKED_ON_OTHER: Event<OnClickedOnOtherCallback> =
        createArrayBacked(OnClickedOnOtherCallback::class.java) { listeners ->
            OnClickedOnOtherCallback { stack, otherSlot, clickType, player ->
                for (callback in listeners) {
                    val returnValue = callback.interact(stack, otherSlot, clickType, player)
                    if (returnValue != null) {
                        return@OnClickedOnOtherCallback returnValue
                    }
                }
                null
            }
        }

    /**
     * Callback for right-clicking on item in slot.
     *
     *  Returning null will pass to vanilla behaviour
     *
     */
    @JvmField
    val ON_CLICKED: Event<OnClickedCallback> =
        createArrayBacked(OnClickedCallback::class.java) { listeners ->
            OnClickedCallback { stack, otherStack, thisSlot, clickType, player, cursorStackReference ->
                for (callback in listeners) {
                    val returnValue =
                        callback.interact(stack, otherStack, thisSlot, clickType, player, cursorStackReference)
                    if (returnValue != null) {
                        return@OnClickedCallback returnValue
                    }
                }
                null
            }
        }


    fun interface OnClickedOnOtherCallback {
        fun interact(stack: ItemStack, otherSlot: Slot, clickType: ClickType, player: PlayerEntity): Boolean?
    }

    fun interface OnClickedCallback {
        fun interact(
            stack: ItemStack,
            otherStack: ItemStack,
            thisSlot: Slot,
            clickType: ClickType,
            player: PlayerEntity,
            cursorStackReference: StackReference,
        ): Boolean?
    }
}

