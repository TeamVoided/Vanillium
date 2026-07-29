package org.teamvoided.vanillium.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.teamvoided.vanillium.data.tags.VnlMenuTags;
import org.teamvoided.vanillium.mixin.accessors.AbstractContainerMenuAccessor;

import static org.teamvoided.vanillium.util.HelpersKt.getTypeHolder;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {


    @WrapOperation(method = "handleContainerClose", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;doCloseContainer()V"))
    void forceUpdatePlayerInv(ServerPlayer player, Operation<Void> original) {
        var holder = getTypeHolder(player.containerMenu);
        original.call(player);
        if (holder != null && holder.is(VnlMenuTags.FORCE_SYNC_AFTER_CLOSE)) {
            var menu = player.containerMenu;
            var usedToSuppressUpdates = ((AbstractContainerMenuAccessor) menu).vnl_suppressRemoteUpdates();
            menu.resumeRemoteUpdates();
            menu.broadcastChanges();
            if (usedToSuppressUpdates) {
                menu.suppressRemoteUpdates();
            }
        }
    }

}