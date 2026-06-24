package org.teamvoided.vanillium.data.tags

import net.minecraft.core.registries.Registries
import org.teamvoided.vanillium.Vanillium.id
import org.teamvoided.vanillium.util.tag

object VnlBlockTags {

    @JvmField
    val INCORRECT_FOR_GOLD_TOOL = create("incorrect_for_gold_tool")

    fun create(id: String) = Registries.BLOCK.tag(id(id))

}