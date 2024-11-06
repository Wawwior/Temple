package me.wawwior.temple.registry.items;

import java.util.function.Supplier;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

/**
 * Items
 */
public class TempleItems {

    public static final Supplier<Item> TEST_ITEM = () -> new Item(new Item.Properties().rarity(Rarity.EPIC));

}
