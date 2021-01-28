package pie.ilikepiefoo2.clickminer.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import pie.ilikepiefoo2.clickminer.ClickMiner;
import pie.ilikepiefoo2.clickminer.Register;
import pie.ilikepiefoo2.clickminer.clickergame.generators.Generator;
import pie.ilikepiefoo2.clickminer.clickergame.generators.GeneratorType;

import java.util.ArrayList;
import java.util.List;

public class GeneratorBlock extends Block {
    private final BlockItem item;
    private Generator generator;
    private String name;
    public GeneratorBlock(Generator generator)
    {
        super(AbstractBlock.Properties.from(Blocks.GLASS));
        this.name = generator.getName();
        this.generator = generator;
        this.setRegistryName(new ResourceLocation(ClickMiner.MOD_ID,name+"_block"));
        item = (BlockItem) new BlockItem(this, Register.defaultBuilder()).setRegistryName(new ResourceLocation(ClickMiner.MOD_ID,name+"_item"));
    }

    public Generator getGenerator()
    {
        return generator;
    }

    public GeneratorType getType()
    {
        return generator.getGeneratorType();
    }

    public String getName()
    {
        return name;
    }

    public BlockItem getBlockItem()
    {
        return item;
    }
}
