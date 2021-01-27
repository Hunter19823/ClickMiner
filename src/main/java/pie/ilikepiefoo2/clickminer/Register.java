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
import pie.ilikepiefoo2.clickminer.common.blocks.GeneratorBlock;
import pie.ilikepiefoo2.clickminer.common.ClickMinerCreativeTab;


public final class Register {
    public static final Block CLICKER_BLOCK = new GeneratorBlock(AbstractBlock.Properties.from(Blocks.GLASS));
    public static final Block FOREST = new GeneratorBlock(AbstractBlock.Properties.from(Blocks.GLASS));
    public static final Block OAK_TREE = new GeneratorBlock(AbstractBlock.Properties.from(Blocks.GLASS));
    public static final Block DARK_OAK_TREE = new GeneratorBlock(AbstractBlock.Properties.from(Blocks.GLASS));

    public static Item.Properties defaultBuilder()
    {
        return new Item.Properties().group(ClickMinerCreativeTab.INSTANCE);
    }

    public static void registerBlocks(RegistryEvent.Register<Block> evt)
    {
        IForgeRegistry<Block> r = evt.getRegistry();
        ResourceLibrary.DEFAULT_CLICKER.registerBlock(r,CLICKER_BLOCK);
        ResourceLibrary.FOREST.registerBlock(r,FOREST);
        ResourceLibrary.OAK_TREE.registerBlock(r,OAK_TREE);
        ResourceLibrary.DARK_OAK_TREE.registerBlock(r,DARK_OAK_TREE);
    }

    public static void registerItemBlocks(RegistryEvent.Register<Item> evt)
    {
        IForgeRegistry<Item> r = evt.getRegistry();
        Item.Properties props = defaultBuilder();
        ResourceLibrary.DEFAULT_CLICKER.registerItemBlock(r, CLICKER_BLOCK,props);
        ResourceLibrary.FOREST.registerItemBlock(r, FOREST,props);
        ResourceLibrary.OAK_TREE.registerItemBlock(r, OAK_TREE,props);
        ResourceLibrary.DARK_OAK_TREE.registerItemBlock(r, DARK_OAK_TREE,props);
    }

    public enum ResourceLibrary {
        DEFAULT_CLICKER("clicker"),FOREST("forest"),OAK_TREE("oak_tree"),DARK_OAK_TREE("dark_oak_tree");
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
