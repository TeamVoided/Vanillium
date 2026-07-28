package org.teamvoided.vanillium.mixin.stackbles.furnacefix;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin extends BlockEntity {

    @Shadow
    protected NonNullList<ItemStack> items;

    public AbstractFurnaceBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @ModifyExpressionValue(method = "canTakeItemThroughFace", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 0))
    boolean extractNonFuel(boolean original, int i, ItemStack itemStack) {
        return original || !level.fuelValues().isFuel(itemStack);
    }

    @ModifyExpressionValue(method = "canPlaceItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/FuelValues;isFuel(Lnet/minecraft/world/item/ItemStack;)Z", ordinal = 0))
    boolean insertMaxStackFuel(boolean original, int i, ItemStack itemStack) {
        ItemStack currentFuel = items.get(1);
        if (currentFuel.isEmpty()) return original;
        else return original && (currentFuel.getItem().getRecipeRemainder(currentFuel).isEmpty());
    }

}