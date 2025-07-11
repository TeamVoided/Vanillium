package org.teamvoided.vanillium.net.s2c

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import org.teamvoided.vanillium.Vanillium.id

class OpenShulkerScreenPacket(val containerId: Int, val lockedSlot: Int) : CustomPacketPayload {
    constructor(friendlyByteBuf: FriendlyByteBuf) : this(
        friendlyByteBuf.readUnsignedByte().toInt(), friendlyByteBuf.readInt()
    )

    fun write(friendlyByteBuf: FriendlyByteBuf) {
        friendlyByteBuf.writeByte(this.containerId)
        friendlyByteBuf.writeInt(this.lockedSlot)
    }

    override fun type(): CustomPacketPayload.Type<OpenShulkerScreenPacket> = ID

    companion object {
        val ID = CustomPacketPayload.Type<OpenShulkerScreenPacket>(id("open_shulker_screen"))
        val STREAM_CODEC = CustomPacketPayload.codec<FriendlyByteBuf, OpenShulkerScreenPacket>(
            OpenShulkerScreenPacket::write, ::OpenShulkerScreenPacket
        )
    }
}