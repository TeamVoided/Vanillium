package org.teamvoided.vanillium

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents
import net.minecraft.component.DataComponentTypes.MAX_STACK_SIZE
import net.minecraft.util.ActionResult
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.teamvoided.vanillium.config.VanilliumCfg
import org.teamvoided.vanillium.events.PostUseItemEvents

@Suppress("unused")
object Vanillium {
    const val MODID = "vanillium"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(Vanillium::class.simpleName)

    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::VanilliumCfg)

    fun init() {
        log.info("Hello from Common")

        DefaultItemComponentEvents.MODIFY.register { c ->
            for ((item, count) in config.customStackSizes) {
                c.modify(item) { it.put(MAX_STACK_SIZE, count) }
            }
        }

        PostUseItemEvents.EVENT.register { original, world, player, hand ->
            if (original.result != ActionResult.PASS) {
                val stack = original.value
                if (!stack.isEmpty) {
                    val cooldown = config.customCooldowns[stack.item]
                    if (cooldown != null) {
                        player.itemCooldownManager.set(stack.item, cooldown)
                    }
                }
            }
        }
    }

    fun id(path: String): Identifier = Identifier.of(MODID, path)
}
