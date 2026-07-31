package org.teamvoided.vanillium.data.tags

import net.minecraft.core.registries.Registries
import org.teamvoided.vanillium.Vanillium.id
import org.teamvoided.vanillium.util.tag

object VnlMenuTags {

    val IS_FURNACE = create("is_furnace")
    val IS_CARTOGRAPHY_TABLE = create("is_cartography_table")

    fun create(id: String) = Registries.MENU.tag(id(id))

}