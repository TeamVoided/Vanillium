package org.teamvoided.vanillium.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.teamvoided.vanillium.Vanillium;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Redirect(method = "closeContainer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V"))
    void x(ClientPacketListener instance, Packet packet) {
        Vanillium.log.info("Closing Screen: {}", ((ServerboundContainerClosePacket) packet).getContainerId());
        instance.send(packet);
    }
}
