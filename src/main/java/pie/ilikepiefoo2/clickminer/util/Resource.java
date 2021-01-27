package pie.ilikepiefoo2.clickminer.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

import static net.minecraft.nbt.JsonToNBT.getTagFromJson;

public class Resource {
    private ResourceLocation item;
    private ResourceLocation visual;
    public static Resource OAK_PLANKS = new Resource(Items.OAK_PLANKS, Blocks.OAK_PLANKS);
    public Resource(Item resourceType, Block visual)
    {
        this.item = resourceType.getRegistryName();
        this.visual = visual.getRegistryName();
    }
    public Resource(ResourceLocation item, ResourceLocation visual)
    {
        this.item = item;
        this.visual = visual;
    }

    public ResourceLocation getItem()
    {
        return item;
    }

    public ResourceLocation getVisual()
    {
        return visual;
    }

    public CompoundNBT toNBT()
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("item",this.item.toString());
        nbt.putString("visual",this.visual.toString());
        return nbt;
    }

    public static Resource fromNBT(CompoundNBT nbt)
    {
        return new Resource(ResourceLocation.tryCreate(nbt.getString("item")),ResourceLocation.tryCreate(nbt.getString("visual")));
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
