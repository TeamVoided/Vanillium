package org.teamvoided.vanillium.data.gen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraft.core.RegistrySetBuilder
import org.teamvoided.vanillium.Vanillium.log
import org.teamvoided.vanillium.data.gen.data.tag.BlockTagsProv
import org.teamvoided.vanillium.data.gen.data.tag.MenuTagsProv

@Suppress("unused")
object VanilliumData : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        log.info("Hello from DataGen")
        val pack = gen.createPack()

        pack.addProvider(::BlockTagsProv)
        pack.addProvider(::MenuTagsProv)
    }

    override fun buildRegistry(gen: RegistrySetBuilder) {
//        gen.add(RegistryKeys.BIOME, TemplateBiomes::boostrap)
    }
}
