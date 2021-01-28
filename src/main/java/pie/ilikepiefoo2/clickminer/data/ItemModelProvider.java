package pie.ilikepiefoo2.clickminer.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import pie.ilikepiefoo2.clickminer.ClickMiner;
import pie.ilikepiefoo2.clickminer.common.blocks.GeneratorBlock;
import pie.ilikepiefoo2.clickminer.common.lib.LibCustomBlocks;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, ClickMiner.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        for(GeneratorBlock block : LibCustomBlocks.BLOCKS.get())
        {
            switch(block.getType()){
                case CLICK:
                    this.withExistingParent(block.getBlockItem().getRegistryName().getPath(),new ResourceLocation(ClickMiner.MOD_ID,("item/clicker_block")));
                    break;
                case BREAK:
                    this.withExistingParent(block.getBlockItem().getRegistryName().getPath(),new ResourceLocation(ClickMiner.MOD_ID,("item/miner_block")));
                    break;
                case AUTOMATIC:
                    this.withExistingParent(block.getBlockItem().getRegistryName().getPath(),new ResourceLocation(ClickMiner.MOD_ID,("item/automatic_block")));
                    break;
            }
        }
    }
}
