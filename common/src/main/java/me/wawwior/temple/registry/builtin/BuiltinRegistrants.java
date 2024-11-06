package me.wawwior.temple.registry.builtin;

import me.wawwior.temple.registry.Registrant;
import me.wawwior.temple.registry.builtin.annotations.NoItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * BuiltinRegistrants
 */
public class BuiltinRegistrants {

    private BuiltinRegistrants() {};

    public static final Registrant<Item, Item> ITEM = Registrant.simple(Item.class, Registries.ITEM);

    public static final Registrant<Block, Block> BLOCK = Registrant.simple(Block.class, Registries.BLOCK);

    public static final Registrant<Block, Item> BLOCK_ITEM = Registrant.of(Block.class, Registries.ITEM, block -> new BlockItem(block, new Item.Properties()), a -> !a.hasAnnotation(NoItem.class));

}
