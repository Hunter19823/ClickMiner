package pie.ilikepiefoo2.clickminer;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import pie.ilikepiefoo2.clickminer.clickergame.generators.GeneratorType.*;
import pie.ilikepiefoo2.clickminer.common.blocks.GeneratorBlock;
import pie.ilikepiefoo2.clickminer.common.ClickMinerCreativeTab;
import pie.ilikepiefoo2.clickminer.common.lib.LibCustomBlocks;

import static pie.ilikepiefoo2.clickminer.clickergame.generators.GeneratorType.*;


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
    }

    public static void registerItemBlocks(RegistryEvent.Register<Item> evt)
    {
        IForgeRegistry<Item> r = evt.getRegistry();
        for(GeneratorBlock block : LibCustomBlocks.BLOCKS.get()){
            r.register(block.getBlockItem());
        }
    }
}
