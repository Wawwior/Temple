package me.wawwior.temple.neoforge;

import me.wawwior.temple.Temple;
import net.neoforged.fml.common.Mod;

@Mod(Temple.MOD_ID)
public final class TempleNeoForge {

    public TempleNeoForge() {
        // Run our common setup.
        Temple.init();
    }
}
