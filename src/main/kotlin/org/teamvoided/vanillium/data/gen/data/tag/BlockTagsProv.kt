package org.teamvoided.vanillium.data.gen.data.tag

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.tags.BlockTags
import org.teamvoided.vanillium.data.tags.VnlBlockTags
import java.util.concurrent.CompletableFuture

class BlockTagsProv(o: FabricDataOutput, p: CompletableFuture<HolderLookup.Provider>) :
    FabricTagProvider.BlockTagProvider(o, p) {

    override fun addTags(wrapperLookup: HolderLookup.Provider) {
        valueLookupBuilder(VnlBlockTags.INCORRECT_FOR_GOLD_TOOL).forceAddTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
    }

}