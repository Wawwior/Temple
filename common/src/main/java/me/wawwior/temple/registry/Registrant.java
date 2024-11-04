package me.wawwior.temple.registry;

import java.util.function.Function;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * Registrant
 */
public interface Registrant<T, U> {

    static Registrant<Item, Item> ITEM = simple(Item.class, Registries.ITEM);

    static Registrant<Block, Block> BLOCK = simple(Block.class, Registries.BLOCK);

    static Registrant<Block, Item> BLOCK_ITEM = of(Block.class, Registries.ITEM, block -> new BlockItem(block, new Item.Properties()));

    void register(ResourceLocation id, T value);

    Function<T, U> getMapper();

    Class<T> getType();

    default <V> Registrant<T, U> with(Registrant<T, V> next) {
        Registrant<T, U> current = this;
        return new Registrant<T, U>() {

            @Override
            public void register(ResourceLocation id, T value) {
                current.register(id, value);
                next.register(id, value);
            }

            @Override
            public Function<T, U> getMapper() {
                return current.getMapper();
            }

            @Override
            public Class<T> getType() {
                return current.getType();
            }

        };
    }

    static <T, U> Registrant<T, U> of(Class<T> type, ResourceKey<Registry<U>> key, Function<T, U> mapper) {
        return new Registrant<T, U>() {

            @Override
            public void register(ResourceLocation id, T value) {
                RegistryHelper.register(key, id, mapper.apply(value));
            }

            @Override
            public Class<T> getType() {
                return type;
            }

            @Override
            public Function<T, U> getMapper() {
                return mapper;
            }

        };
    }

    static <T> Registrant<T, T> simple(Class<T> type, ResourceKey<Registry<T>> key) {
        return of(type, key, Function.identity());
    }

}
