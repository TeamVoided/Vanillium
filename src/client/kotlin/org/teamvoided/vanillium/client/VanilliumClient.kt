package org.teamvoided.vanillium.client

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import me.fzzyhmstrs.fzzy_config.api.RegisterType
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.MenuScreens
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Component.translatable
import net.minecraft.network.chat.contents.objects.AtlasSprite
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import org.teamvoided.vanillium.Vanillium.id
import org.teamvoided.vanillium.Vanillium.mc
import org.teamvoided.vanillium.client.config.FuelDisplayType
import org.teamvoided.vanillium.client.config.TooltipRenderType
import org.teamvoided.vanillium.client.config.VanilliumClientCfg
import org.teamvoided.vanillium.client.init.VnlClientNet
import org.teamvoided.vanillium.client.screen.OpenShulkerBoxScreen
import org.teamvoided.vanillium.init.VnlMenus
import java.text.DecimalFormat

@Suppress("unused")
object VanilliumClient {

    val CLEAN_DECIMAL = DecimalFormat("#.##")

    val clientConfig = ConfigApi.registerAndLoadConfig(::VanilliumClientCfg, RegisterType.CLIENT)

    fun init() {
        MenuScreens.register(VnlMenus.QUICK_SHULKER, ::OpenShulkerBoxScreen)
        VnlClientNet.init()

        ItemTooltipCallback.EVENT.register { stack, ctx, flags, tooltips ->
            val level = Minecraft.getInstance().level
            appendFuelValues(level, stack, ctx, tooltips)
        }
    }

    fun appendFuelValues(
        level: ClientLevel?, stack: ItemStack, ctx: Item.TooltipContext, tooltips: MutableList<Component>,
    ) {
        when (clientConfig.fuelTooltip.whenToRenderTooltip) {
            TooltipRenderType.TAG_OR_CLASS_CHECK -> if (ctx.vanillium_currentScreen() !is AbstractFurnaceScreen<*>) return
            TooltipRenderType.TAG -> if (ctx.vanillium_currentScreen() !is AbstractFurnaceScreen<*>) return
            TooltipRenderType.CLASS_CHECK -> if (ctx.vanillium_currentScreen() !is AbstractFurnaceScreen<*>) return
            TooltipRenderType.ALWAYS -> Unit
            TooltipRenderType.NEVER -> return;
        }


        val fuelValues = level?.fuelValues()?.burnDuration(stack)

        if (fuelValues != null && fuelValues > 0) {
            tooltips.add(
                Component.`object`(AtlasSprite(mc("gui"), id("container/burning_amount")))
                    .append(translatable(getFuelText(fuelValues)).withColor(0xf7830b))
            )
        }
    }

    fun getFuelText(fuelValues: Int): String {
        return when (clientConfig.fuelTooltip.displayType) {
            FuelDisplayType.ITEMS -> " x ${CLEAN_DECIMAL.format(fuelValues / (200.0))}"
            FuelDisplayType.TICKS -> " ${fuelValues}t"
            FuelDisplayType.ITEMS_AND_TICKS -> " x ${CLEAN_DECIMAL.format(fuelValues / (200.0))} (${fuelValues}t)"
            FuelDisplayType.ITEMS_TO_TICKS_WHEN_SHIFT ->
                if (isShift())
                    " ${fuelValues}t"
                else
                    " x ${CLEAN_DECIMAL.format(fuelValues / (200.0))}"
        }
    }

    fun isShift(): Boolean = Minecraft.getInstance().hasShiftDown()

}