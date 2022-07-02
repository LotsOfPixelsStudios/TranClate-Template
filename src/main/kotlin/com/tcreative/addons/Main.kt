package com.tcreative.addons

import com.tcreative.devtools.stdlib.cameratrack.Coordinate
import com.tcreative.devtools.stdlib.cameratrack.cameraTracks
import com.tcreative.devtools.stdlib.statesys.stateSystem
import com.tcreative.devtools.tranclate.builder.getResource
import com.tcreative.devtools.tranclate.builder.zipper.zipProject
import com.tcreative.devtools.tranclate.systemaddon.addon

fun main() {
    //todo
    //---------------------------------------------------------------
    // [ ] make sure to modify addon() to loadAddon() if some json code already exist
    // [ ] replace ProjectName, in addon & settings.gradle.kts
    // [ ] replace ProjectShort
    // [ ] replace or remove description
    // [ ] replace or remove world
    // [ ] replace or remove pack icon
    // [ ] replace or remove sample entity
    // [ ] replace or remove sample item
    // [ ] replace or remove sample system
    // [ ] modify or remove zipProject
    // [ ] modify packageAddon()
    //---------------------------------------------------------------

    addon(
        projectName = "Template",
        projectShort = "tp",
        description = "",
        packIcon = getResource("general/pack.png"),
        world = getResource("world/template-world"),
        version = arrayListOf(1, 0, 0)
    ) {
        packageAddon = false

        entity {
            name("sample_entity", "Sample Entity")
            resource {
                texture(getResource("entities/default_texture.png"))
                geometry(getResource("entities/default_model.geo.json"))
            }
            behaviour {
                components {
                    physics { }
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
        cameraTracks(this) {
            addCameraTrack(
                "a_to_b",
                arrayListOf(
                    Pair(Coordinate(0f, -50f, 0f, 0f, 0f), 0f),
                    Pair(Coordinate(10f, -50f, 0f, 20f, 0f), 5f)
                ),
                selector = "@a",
                exitOnJumpOrCrouch = true,
                onExit = arrayListOf("/function exit_fun")
            )
        }
    }

    zipProject("1.0-SNAPSHOT", getResource("world/template-world"))
}