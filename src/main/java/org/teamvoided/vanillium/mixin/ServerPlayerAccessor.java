package org.teamvoided.vanillium.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ServerPlayer.class)
public interface ServerPlayerAccessor {
    @Accessor("containerCounter")
    int nvl_getContainerCounter();

    @Invoker("nextContainerCounter")
    void vnl_invokeNextContainerCounter();

    @Invoker("initMenu")
    void vnl_invokeInitMenu(AbstractContainerMenu abstractContainerMenu);
}
