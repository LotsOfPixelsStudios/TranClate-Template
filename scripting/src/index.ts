/**
 * This is the entry point for the scripts, if you want to change the entry point to another file you have to
 * change the import within the tc project (/src/main/kotlin/com/tcreative/addons/Main.kt)!
 */

import { world, system } from "@minecraft/server";

function mainTick() {
    if (system.currentTick % 100 === 0) {
        world.sendMessage("say Hello starter! Tick: " + system.currentTick);
    }

    system.run(mainTick);
}

system.run(mainTick);