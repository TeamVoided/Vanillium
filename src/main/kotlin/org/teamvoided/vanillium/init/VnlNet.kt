package org.teamvoided.vanillium.init

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import org.teamvoided.vanillium.net.s2c.OpenShulkerScreenPacket


object VnlNet {
    fun init() {
        PayloadTypeRegistry.playS2C().register(OpenShulkerScreenPacket.ID, OpenShulkerScreenPacket.STREAM_CODEC)
    }
}
