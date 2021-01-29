package pie.ilikepiefoo2.clickminer.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import pie.ilikepiefoo2.clickminer.ClickMiner;
import pie.ilikepiefoo2.clickminer.common.blocks.GeneratorBlock;
import pie.ilikepiefoo2.clickminer.common.lib.LibCustomBlocks;

public class LanguageProvider extends net.minecraftforge.common.data.LanguageProvider {
    public LanguageProvider(DataGenerator gen, String locale)
    {
        super(gen, ClickMiner.MOD_ID, locale);
    }

    @Override
    protected void addTranslations()
    {
        this.add("itemGroup.clickminer","Pie's Click Miner");
        for(GeneratorBlock block : LibCustomBlocks.BLOCKS.get())
        {
            this.add(block,block.getName()+" generator block ("+block.getType().name()+")");
            this.add(String.format("item.%s.%s_block",ClickMiner.MOD_ID,block.getName()),block.getName()+" generator block ("+block.getType().name()+")");
        }
        this.add(GeneratorBlock.UNTEXTURED_RESOURCE_BLOCK,"Unknown Resource Block");
    }
}
