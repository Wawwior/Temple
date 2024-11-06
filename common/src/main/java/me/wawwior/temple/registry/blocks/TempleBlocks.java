package me.wawwior.temple.registry.blocks;

import java.util.function.Supplier;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Blocks
 */
public class TempleBlocks {

    public static Supplier<Block> TEST_BLOCK = () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK));

}
