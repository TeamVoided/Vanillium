package org.teamvoided.vanillium.mixin;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.teamvoided.vanillium.data.tags.VnlBlockTags;

@Mixin(ToolMaterial.class)
public class ToolMaterialMixin {

    @ModifyArg(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ToolMaterial;<init>(Lnet/minecraft/tags/TagKey;IFFILnet/minecraft/tags/TagKey;)V",
                    ordinal = 5
            ),
            index = 0
    )
    private static TagKey<Block> modifyGoldTag(TagKey<Block> incorrectBlocksForDrops) {
        return VnlBlockTags.INCORRECT_FOR_GOLD_TOOL;
    }
}
