package org.teamvoided.vanillium.client

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import me.fzzyhmstrs.fzzy_config.api.RegisterType
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.MenuScreens
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Component.literal
import net.minecraft.network.chat.Component.translatable
import net.minecraft.network.chat.contents.objects.AtlasSprite
import net.minecraft.util.ARGB
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.material.MapColor
import org.teamvoided.vanillium.Vanillium.id
import org.teamvoided.vanillium.Vanillium.mc
import org.teamvoided.vanillium.client.config.FuelDisplayType
import org.teamvoided.vanillium.client.config.VanilliumClientCfg
import org.teamvoided.vanillium.client.init.VnlClientNet
import org.teamvoided.vanillium.client.screen.OpenShulkerBoxScreen
import org.teamvoided.vanillium.init.VnlMenus
import java.text.DecimalFormat

object VanilliumClient {

    val CLEAN_DECIMAL = DecimalFormat("#.##")

    val clientConfig = ConfigApi.registerAndLoadConfig(::VanilliumClientCfg, RegisterType.CLIENT)

    @JvmField
    val GUI_ATLAS = mc("gui")

    fun init() {
        MenuScreens.register(VnlMenus.QUICK_SHULKER, ::OpenShulkerBoxScreen)
        VnlClientNet.init()

        ItemTooltipCallback.EVENT.register { stack, ctx, flags, tooltips ->
            val level = Minecraft.getInstance().level
            appendFuelValues(level, stack, ctx, tooltips)
            appendMapColors(stack, ctx, tooltips)
        }
    }

    fun appendMapColors(stack: ItemStack, ctx: Item.TooltipContext, tooltips: MutableList<Component>) {
        if (!clientConfig.mapColorTooltips.shouldRender(ctx.vnl_currentScreen())) {
            return
        }
        val blockItem = stack.item as? BlockItem ?: return
        val block = blockItem.block

        if (block != Blocks.AIR) {
            val mapColor = block.defaultMapColor()

            val colorText = if (mapColor == MapColor.NONE)
                translatable("None").withStyle(ChatFormatting.DARK_GRAY)
            else {
                val color = ARGB.opaque(mapColor.col)
                val srgb = ARGB.vector3fFromRGB24(color)
                val gray = 1 - ((srgb.x + srgb.y + srgb.z) / 3)
                literal("\u2588").withStyle {
                    it
                        .withColor(color)
                        .withShadowColor(ARGB.colorFromFloat(1f, gray, gray, gray))
                }
            }

            tooltips.add(translatable("Map color: %s", colorText).withStyle(ChatFormatting.GRAY))
        }
    }

    fun appendFuelValues(
        level: ClientLevel?, stack: ItemStack, ctx: Item.TooltipContext, tooltips: MutableList<Component>,
    ) {
        if (!clientConfig.fuelTooltip.shouldRender(ctx.vnl_currentScreen())) {
            return
        }

        val fuelValues = level?.fuelValues()?.burnDuration(stack)

        if (fuelValues != null && fuelValues > 0) {
            tooltips.add(
                Component.`object`(AtlasSprite(GUI_ATLAS, id("container/burning_amount")))
                    .append(translatable(getFuelText(getMultipliedValue(fuelValues, stack))).withColor(0xf7830b))
            )
        }
    }

    fun getMultipliedValue(fuelValues: Int, stack: ItemStack): Int {
        return if (clientConfig.fuelTooltip.countTimeForWholeStack)
            fuelValues * stack.count
        else
            fuelValues
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