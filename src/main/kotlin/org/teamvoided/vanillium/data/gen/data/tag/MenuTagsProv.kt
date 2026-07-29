package org.teamvoided.vanillium.data.gen.data.tag

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.FabricValueLookupTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.world.inventory.MenuType
import org.teamvoided.vanillium.data.tags.VnlMenuTags
import org.teamvoided.vanillium.init.VnlMenus
import java.util.concurrent.CompletableFuture

class MenuTagsProv(o: FabricDataOutput, p: CompletableFuture<HolderLookup.Provider>) :
    FabricValueLookupTagProvider<MenuType<*>>(
        o, Registries.MENU, p, { BuiltInRegistries.MENU.getResourceKey(it).get() }) {

    override fun addTags(wrapperLookup: HolderLookup.Provider) {
        valueLookupBuilder(VnlMenuTags.FORCE_SYNC_AFTER_CLOSE).add(VnlMenus.QUICK_SHULKER)
        valueLookupBuilder(VnlMenuTags.IS_FURNACE)
            .add(
                MenuType.FURNACE,
                MenuType.BLAST_FURNACE,
                MenuType.SMOKER,
            )
    }

}