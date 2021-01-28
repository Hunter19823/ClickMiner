package pie.ilikepiefoo2.clickminer.common.lib;

import net.minecraft.block.Block;
import pie.ilikepiefoo2.clickminer.clickergame.generators.Generator;
import pie.ilikepiefoo2.clickminer.clickergame.generators.GeneratorType;
import pie.ilikepiefoo2.clickminer.common.blocks.GeneratorBlock;
import pie.ilikepiefoo2.clickminer.util.BigNumber;
import pie.ilikepiefoo2.clickminer.util.Resource;

import static pie.ilikepiefoo2.clickminer.clickergame.generators.GeneratorType.*;

public class LibCustomBlocks {
    public enum BLOCKS{
        CLICKER_BLOCK(new GeneratorBlock(
                new Generator(
                        "beginner",
                        Resource.OAK_TREE,
                        new BigNumber(1),
                        CLICK))),
        FOREST(new GeneratorBlock(
                new Generator(
                        "forest",
                        Resource.FOREST,
                        new BigNumber(1),
                        BREAK))),
        OAK_TREE(new GeneratorBlock(
                new Generator(
                        "oak_tree",
                        Resource.OAK_TREE,
                        new BigNumber(1),
                        BREAK))),
        DARK_OAK_TREE(new GeneratorBlock(
                new Generator(
                        "dark_oak_tree",
                        Resource.DARK_OAK_TREE,
                        new BigNumber(1),
                        BREAK))),
        DRILL(new GeneratorBlock(
                new Generator(
                        "drill",
                        Resource.BLANK,
                        new BigNumber(1),
                        AUTOMATIC)))
        ;
        public final GeneratorBlock block;
        private static GeneratorBlock[] blocks = null;
        BLOCKS(GeneratorBlock block)
        {
            this.block = block;
        }
        public static GeneratorBlock[] get()
        {
            if(BLOCKS.blocks != null)
                return BLOCKS.blocks;
            BLOCKS[] blocks = BLOCKS.values();
            BLOCKS.blocks = new GeneratorBlock[blocks.length];
            for(int i=0; i<blocks.length; i++) {
                BLOCKS.blocks[i] = blocks[i].block;
            }
            return BLOCKS.blocks;
        }
    }
}
