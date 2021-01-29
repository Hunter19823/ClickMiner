package pie.ilikepiefoo2.clickminer.common.lib;

import pie.ilikepiefoo2.clickminer.clickergame.generators.Generator;
import pie.ilikepiefoo2.clickminer.common.blocks.ExplorationBlock;
import pie.ilikepiefoo2.clickminer.common.blocks.GeneratorBlock;
import pie.ilikepiefoo2.clickminer.util.BigNumber;
import pie.ilikepiefoo2.clickminer.util.Resource;

import java.util.HashMap;
import java.util.Map;

import static pie.ilikepiefoo2.clickminer.clickergame.generators.GeneratorType.*;

public class LibCustomBlocks {
    public enum BLOCKS{
        CLICKER_BLOCK(new ExplorationBlock()),
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
        public final GeneratorBlock GENERATOR_BLOCK;
        private static GeneratorBlock[] BLOCK_ARRAY = null;
        private static final Map<String,BLOCKS> BLOCK_BY_NAME = new HashMap<>();
        BLOCKS(GeneratorBlock GENERATOR_BLOCK)
        {
            this.GENERATOR_BLOCK = GENERATOR_BLOCK;
        }
        public static GeneratorBlock[] get()
        {
            if(BLOCKS.BLOCK_ARRAY != null)
                return BLOCKS.BLOCK_ARRAY;
            BLOCKS[] blocks = BLOCKS.values();
            BLOCKS.BLOCK_ARRAY = new GeneratorBlock[blocks.length];
            for(int i=0; i<blocks.length; i++) {
                BLOCKS.BLOCK_ARRAY[i] = blocks[i].GENERATOR_BLOCK;
                BLOCK_BY_NAME.put(blocks[i].GENERATOR_BLOCK.getName(),blocks[i]);
            }
            return BLOCKS.BLOCK_ARRAY;
        }
        public static Map<String,BLOCKS> getBlockByName()
        {
            if(BLOCKS.BLOCK_ARRAY == null)
                get();
            return BLOCK_BY_NAME;
        }
    }
}
