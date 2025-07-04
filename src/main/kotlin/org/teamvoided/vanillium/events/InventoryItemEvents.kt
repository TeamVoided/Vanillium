package org.teamvoided.vanillium.events

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory.createArrayBacked
import net.minecraft.world.entity.SlotAccess
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ClickAction
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

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
            OnClickedOnOtherCallback { stack, otherSlot, clickAction, player ->
                for (callback in listeners) {
                    val returnValue = callback.interact(stack, otherSlot, clickAction, player)
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
            OnClickedCallback { stack, otherStack, thisSlot, clickAction, player, cursorSlotAccess ->
                for (callback in listeners) {
                    val returnValue =
                        callback.interact(stack, otherStack, thisSlot, clickAction, player, cursorSlotAccess)
                    if (returnValue != null) {
                        return@OnClickedCallback returnValue
                    }
                }
                null
            }
        }


    fun interface OnClickedOnOtherCallback {
        fun interact(stack: ItemStack, otherSlot: Slot, clickAction: ClickAction, player: Player): Boolean?
    }

    fun interface OnClickedCallback {
        fun interact(
            stack: ItemStack,
            otherStack: ItemStack,
            thisSlot: Slot,
            clickAction: ClickAction,
            player: Player,
            cursorSlotAccess: SlotAccess,
        ): Boolean?
    }
}

