package pie.ilikepiefoo2.clickminer;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import pie.ilikepiefoo2.clickminer.clickergame.blocks.GeneratorBlock;
import pie.ilikepiefoo2.clickminer.core.ClickMinerCreativeTab;


public final class Register {
    public static final Block CLICKER_BLOCK = new GeneratorBlock(AbstractBlock.Properties.from(Blocks.BEDROCK));

    public static Item.Properties defaultBuilder()
    {
        return new Item.Properties().group(ClickMinerCreativeTab.INSTANCE);
    }

    public static void registerBlocks(RegistryEvent.Register<Block> evt)
    {
        IForgeRegistry<Block> r = evt.getRegistry();
        ResourceLibrary.DEFAULT_CLICKER.registerBlock(r,CLICKER_BLOCK);
    }



    public static void registerItemBlocks(RegistryEvent.Register<Item> evt)
    {
        IForgeRegistry<Item> r = evt.getRegistry();
        Item.Properties props = defaultBuilder();
        ResourceLibrary.DEFAULT_CLICKER.registerItemBlock(r, CLICKER_BLOCK,props);
    }

    public enum ResourceLibrary {
        DEFAULT_CLICKER("clicker");
        public String path;
        ResourceLibrary(String path)
        {
            this.path = path;
        }
        public ResourceLocation getResourceLocation(String type)
        {
            return new ResourceLocation(ClickMiner.MOD_ID,this.path+"_"+type);
        }
        public void registerBlock(IForgeRegistry<Block> registry, IForgeRegistryEntry<Block> entry)
        {
            registry.register(entry.setRegistryName(this.getResourceLocation("block")));
        }
        public void registerItemBlock(IForgeRegistry<Item> registry, Block entry, Item.Properties props)
        {
            registry.register(new BlockItem(entry, props).setRegistryName(getResourceLocation("block")));
        }
    }
}
