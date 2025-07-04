package org.teamvoided.vanillium.init

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import org.teamvoided.vanillium.Vanillium.id
import org.teamvoided.vanillium.inventory.QuickShulkerBoxMenu


object VnlMenus {
    val QUICK_SHULKER = register("quick_shulker", ::QuickShulkerBoxMenu)
    fun init() = Unit

    internal fun <T : AbstractContainerMenu> register(id: String, menuSupplier: MenuType.MenuSupplier<T>): MenuType<T> =
        Registry.register(BuiltInRegistries.MENU, id(id), MenuType<T>(menuSupplier, FeatureFlags.VANILLA_SET))
}