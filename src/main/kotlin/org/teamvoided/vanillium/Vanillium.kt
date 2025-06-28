package org.teamvoided.vanillium

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents
import net.minecraft.component.DataComponentTypes
import net.minecraft.item.Items
import net.minecraft.item.PotionItem
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.teamvoided.vanillium.config.VanilliumCfg

@Suppress("unused")
object Vanillium {
    const val MODID = "vanillium"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(Vanillium::class.simpleName)

    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::VanilliumCfg)

    fun init() {
        log.info("Hello from Common")

        DefaultItemComponentEvents.MODIFY.register(::modifyDefaultItemComponent)

    }

    private fun modifyDefaultItemComponent(c: DefaultItemComponentEvents.ModifyContext) {
        Registries.ITEM.filter { it is PotionItem }.forEach { item ->
            c.modify(item) { it.put(DataComponentTypes.MAX_STACK_SIZE, 16) }
        }

        c.modify(Items.SADDLE) { it.put(DataComponentTypes.MAX_STACK_SIZE, 16) }

        listOf(
            Items.LEATHER_HORSE_ARMOR, Items.IRON_HORSE_ARMOR, Items.GOLDEN_HORSE_ARMOR, Items.DIAMOND_HORSE_ARMOR
        ).forEach { item ->
            c.modify(item) { it.put(DataComponentTypes.MAX_STACK_SIZE, 16) }
        }

        Registries.ITEM.filter { it.components.get(DataComponentTypes.JUKEBOX_PLAYABLE) != null }.forEach { item ->
            c.modify(item) { it.put(DataComponentTypes.MAX_STACK_SIZE, 16) }
        }

        c.modify(Items.MINECART) { it.put(DataComponentTypes.MAX_STACK_SIZE, 4) }
    }

    fun id(path: String) = Identifier.of(MODID, path)
}
