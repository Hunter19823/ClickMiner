package pie.ilikepiefoo2.clickminer.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.Register;

import static net.minecraft.nbt.JsonToNBT.getTagFromJson;

public class Resource {
    private static final Logger LOGGER = LogManager.getLogger();
    private Item item;
    public static final WeightedRandom<Resource> FOREST = new WeightedRandom<Resource>();
    public static final WeightedRandom<Resource> OAK_TREE = new WeightedRandom<Resource>();
    public static final WeightedRandom<Resource> DARK_OAK_TREE = new WeightedRandom<Resource>();

    static{
        Item[] items = {
            Items.OAK_LOG,
            Items.SPRUCE_LOG,
            Items.BIRCH_LOG,
            Items.ACACIA_LOG,
            Items.DARK_OAK_LOG,
            Items.JUNGLE_LOG
        };
        for(Item item : items)
        {
            FOREST.add(items.length,item);
        }
        OAK_TREE.add(25,Items.APPLE);
        OAK_TREE.add(75,Items.OAK_LOG);
        DARK_OAK_TREE.add(60,Items.APPLE);
        DARK_OAK_TREE.add(75,Items.DARK_OAK_LOG);
    }

    public Resource(Item resourceType)
    {
        this.item = resourceType;
    }

    public Item getItem()
    {
        return item;
    }

    public Block getVisual()
    {
        Block visual = Block.getBlockFromItem(this.item);
        return visual.equals(Blocks.AIR) ? Register.CLICKER_BLOCK : visual;
    }

    public CompoundNBT toNBT()
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("item",Item.getIdFromItem(this.item));
        return nbt;
    }

    public static Resource fromNBT(CompoundNBT nbt)
    {
        return new Resource(Item.getItemById(nbt.getInt("item")));
    }

    public static Resource fromString(String resource)
    {
        try {
            CompoundNBT compoundNBT = JsonToNBT.getTagFromJson(resource);
            return fromNBT(compoundNBT);
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString()
    {
        return toNBT().toString();
    }
}
