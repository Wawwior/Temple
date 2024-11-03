package me.wawwior.temple;

import me.wawwior.temple.registry.Registrant;
import me.wawwior.temple.registry.RegistryHelper;
import me.wawwior.temple.registry.blocks.TempleBlocks;
import me.wawwior.temple.registry.items.TempleItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;

public final class Temple {

    public static final String MOD_ID = "temple";

    private static RegistryHelper registryHelper = new RegistryHelper(MOD_ID);

    public static void init() {
        // Write common init code here.
        registryHelper.register(TempleItems.class, Registrant.simple(Item.class, Registries.ITEM));
        registryHelper.register(TempleBlocks.class, Registrant.simple(Block.class, Registries.BLOCK));
        registryHelper.register(TempleBlocks.class, Registrant.of(Block.class, Registries.ITEM, block -> new BlockItem(block, new Item.Properties().rarity(Rarity.EPIC))));
    }
}
