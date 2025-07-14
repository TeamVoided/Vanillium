package org.teamvoided.vanillium.client.screen

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import org.teamvoided.vanillium.Vanillium.mc


@Environment(EnvType.CLIENT)
class OpenShulkerBoxScreen<T : AbstractContainerMenu>(menu: T, inventory: Inventory, component: Component) :
    AbstractContainerScreen<T>(menu, inventory, component) {

    override fun render(guiGraphics: GuiGraphics, i: Int, j: Int, f: Float) {
        super.render(guiGraphics, i, j, f)
        this.renderTooltip(guiGraphics, i, j)
    }

    override fun renderBg(guiGraphics: GuiGraphics, f: Float, i: Int, j: Int) {
        val k = (this.width - this.imageWidth) / 2
        val l = (this.height - this.imageHeight) / 2
        guiGraphics.blit(
            RenderPipelines.GUI_TEXTURED, CONTAINER_TEXTURE,
            k, l, 0.0f, 0.0f, this.imageWidth, this.imageHeight, 256, 256
        )
    }

    companion object {
        val CONTAINER_TEXTURE = mc("textures/gui/container/shulker_box.png")
    }
}
