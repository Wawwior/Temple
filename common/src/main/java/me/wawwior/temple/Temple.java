package me.wawwior.temple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.wawwior.temple.registry.Registrant;
import me.wawwior.temple.registry.RegistryHelper;
import me.wawwior.temple.registry.blocks.TempleBlocks;
import me.wawwior.temple.registry.items.TempleItems;

public final class Temple {

    public static final String MOD_ID = "temple";

    public static final Logger LOGGER = LoggerFactory.getLogger(Temple.class);

    private static RegistryHelper registryHelper = new RegistryHelper(MOD_ID);

    public static void init() {

        // Write common init code here.
        registryHelper.registerClass(TempleItems.class, Registrant.ITEM);
        registryHelper.registerClass(TempleBlocks.class, Registrant.BLOCK.with(Registrant.BLOCK_ITEM));
    }
}
