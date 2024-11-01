package me.wawwior.temple.neoforge;

import net.neoforged.fml.common.Mod;

import me.wawwior.temple.ExampleMod;

@Mod(ExampleMod.MOD_ID)
public final class ExampleModNeoForge {
    public ExampleModNeoForge() {
        // Run our common setup.
        ExampleMod.init();
    }
}
