package org.teamvoided.vanillium.init

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import org.teamvoided.vanillium.Vanillium.id
import org.teamvoided.vanillium.inventory.QuickShulkerBoxMenu
import org.teamvoided.vanillium.inventory.QuickShulkerBoxMenu.Companion.QuickShulkerData


object VnlMenus {
    val QUICK_SHULKER = register("quick_shulker", QuickShulkerData.PACKET_CODEC, ::QuickShulkerBoxMenu)
    fun init() = Unit

    internal fun <T : AbstractContainerMenu> register(id: String, menu: MenuType.MenuSupplier<T>): MenuType<T> =
        Registry.register(BuiltInRegistries.MENU, id(id), MenuType(menu, FeatureFlags.VANILLA_SET))


    internal fun <T : AbstractContainerMenu, D : Any> register(
        id: String,
        codec: StreamCodec<in RegistryFriendlyByteBuf, D>,
        menu: ExtendedScreenHandlerType.ExtendedFactory<T, D>,
    ): ExtendedScreenHandlerType<T, D> {
        return Registry.register(BuiltInRegistries.MENU, id(id), ExtendedScreenHandlerType(menu, codec))
    }

}