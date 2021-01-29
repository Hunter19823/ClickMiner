package pie.ilikepiefoo2.clickminer;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import pie.ilikepiefoo2.clickminer.common.blocks.GeneratorBlock;
import pie.ilikepiefoo2.clickminer.common.ClickMinerCreativeTab;
import pie.ilikepiefoo2.clickminer.common.lib.LibCustomBlocks;


public final class Register {


    public static Item.Properties defaultBuilder()
    {
        return new Item.Properties().group(ClickMinerCreativeTab.INSTANCE);
    }

    public static void registerBlocks(RegistryEvent.Register<Block> evt)
    {
        IForgeRegistry<Block> r = evt.getRegistry();
        for(GeneratorBlock block : LibCustomBlocks.BLOCKS.get()){
            r.register(block);
        }
        r.register(GeneratorBlock.UNTEXTURED_RESOURCE_BLOCK);
    }

    public static void registerItemBlocks(RegistryEvent.Register<Item> evt)
    {
        IForgeRegistry<Item> r = evt.getRegistry();
        for(GeneratorBlock block : LibCustomBlocks.BLOCKS.get()){
            r.register(block.getBlockItem());
        }
    }
}
