package com.tcreative.addons

import com.tcreative.devtools.stdlib.cameratrack.Coordinate
import com.tcreative.devtools.stdlib.cameratrack.cameraTracks
import com.tcreative.devtools.stdlib.commands.Selector
import com.tcreative.devtools.stdlib.furnitures.furniture
import com.tcreative.devtools.stdlib.furnitures.shared.FurnitureDropBehaviour
import com.tcreative.devtools.stdlib.npcs.npc
import com.tcreative.devtools.stdlib.packaging.packaging
import com.tcreative.devtools.stdlib.player.Player
import com.tcreative.devtools.stdlib.statesys.stateSystem
import com.tcreative.devtools.stdlib.templateworld.modifyTemplateWorldName
import com.tcreative.devtools.tranclate.builder.getResource
import com.tcreative.devtools.tranclate.builder.zipper.zipWorld
import com.tcreative.devtools.tranclate.systemaddon.addon

fun main(args: Array<String>) {
    //todo
    //---------------------------------------------------------------
    // [ ] make sure to modify addon() to lazyAddon() if some json code already exist
    // [ ] replace ProjectName, in addon & settings.gradle.kts
    // [ ] replace ProjectShort
    // [ ] replace or remove description
    // [ ] replace or remove world
    // [ ] replace or remove pack icon
    // [ ] replace or remove sample entity
    // [ ] replace or remove sample item
    // [ ] replace or remove sample system
    //---------------------------------------------------------------

    val properties = addon({
        projectName = "Template"
        projectShort = "tp"
        description = ""
        packIcon = getResource("general/pack.png")
        world = getResource("world/template-world").modifyTemplateWorldName("Template")
        version = arrayListOf(0, 0, 1)
        targetMcVersion = arrayListOf(1, 19, 81)
    }) {
        if (args.contains("package")) {
            packaging {
                this.world = getResource("world/template-world")
                addSkinPack {
                    addSkin(getResource("skin_pack/alex_a.png"), true)
                    addSkin(getResource("skin_pack/steve_s.png"), true)
                }
                addStoreArt { }
                addMarketing { }
                addBehaviorPack { }
                addResourcePack { }
            }
        }

        entity {
            name("sample_entity", "Sample Entity")
            resource {
                textureLayer(getResource("entities/default_texture.png"))
                geometryLayer(getResource("entities/default_model.geo.json"))
            }
            behaviour {
                components {
                    physics { }
                }

                cameraTracks(this@addon) {
                    addSharedAnimation(
                        trackAsAnimation(
                            "a_to_b", values = arrayListOf(
                                Pair(Coordinate(0f, -50f, 0f, 0f, 0f), 0f),
                                Pair(Coordinate(10f, -50f, 0f, 20f, 0f), 5f)
                            ), Selector.p.toString()
                        )
                    )
                }
            }
        }
        item {
            name("sample_item", "display name")
            renderOffset("tool")
            texture(getResource("item/default_texture.png"))
        }
        stateSystem(this) {
            identifier = "sample"
            state {

            }
        }
        Player
            .modify(this)
            .modifyBehaviour { }

        furniture("vase", "Vase", this) {
            dropBehaviour = FurnitureDropBehaviour.CAN_PICKUP
        }

        npc("my_npc", "My NPC", this) {
            //...
        }
    }

    if (args.contains("zip-world"))
        zipWorld(world = getResource("world/template-world"), properties, System.getenv("CI_PROJECT_NAME") ?: "local")
}