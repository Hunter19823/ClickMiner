package pie.ilikepiefoo2.clickminer.data;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.ClickMiner;
import pie.ilikepiefoo2.clickminer.Register;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;
import pie.ilikepiefoo2.clickminer.clickergame.generators.GeneratorType;
import pie.ilikepiefoo2.clickminer.common.blocks.GeneratorBlock;
import pie.ilikepiefoo2.clickminer.common.lib.LibCustomBlocks;

import java.util.Set;
import java.util.stream.Collectors;

public class BlockstateProvider extends BlockStateProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    public BlockstateProvider(DataGenerator gen, ExistingFileHelper exFileHelper)
    {
        super(gen, ClickMiner.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        LOGGER.info("Registering Block States for "+LibCustomBlocks.BLOCKS.values().length+" blocks");
        for(GeneratorBlock block : LibCustomBlocks.BLOCKS.get())
        {
            switch(block.getType()){
                case CLICK:
                    simpleBlock(block,models().getExistingFile(new ResourceLocation(ClickMiner.MOD_ID,("block/clicker_block"))));
                    simpleBlockItem(block,models().getExistingFile(new ResourceLocation(ClickMiner.MOD_ID,("item/clicker_block"))));
                    break;
                case BREAK:
                    simpleBlock(block,models().getExistingFile(new ResourceLocation(ClickMiner.MOD_ID,("block/miner_block"))));
                    simpleBlockItem(block,models().getExistingFile(new ResourceLocation(ClickMiner.MOD_ID,("item/miner_block"))));
                    break;
                case AUTOMATIC:
                    simpleBlock(block,models().getExistingFile(new ResourceLocation(ClickMiner.MOD_ID,("block/automatic_block"))));
                    simpleBlockItem(block,models().getExistingFile(new ResourceLocation(ClickMiner.MOD_ID,("item/automatic_block"))));
                    break;
            }
        }
        simpleBlock(GeneratorBlock.UNTEXTURED_RESOURCE_BLOCK,models().getExistingFile(new ResourceLocation(ClickMiner.MOD_ID,"block/unknown_texture_block")));
    }
}
