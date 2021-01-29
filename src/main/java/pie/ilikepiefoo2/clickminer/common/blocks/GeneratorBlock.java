package pie.ilikepiefoo2.clickminer.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.ClickMiner;
import pie.ilikepiefoo2.clickminer.Register;
import pie.ilikepiefoo2.clickminer.clickergame.generators.Generator;
import pie.ilikepiefoo2.clickminer.clickergame.generators.GeneratorType;
import pie.ilikepiefoo2.clickminer.clickergame.upgrades.Upgrade;


import java.util.HashMap;
import java.util.Map;

public class GeneratorBlock extends Block {
    private static final Logger LOGGER = LogManager.getLogger();
    private BlockItem item;
    private Generator generator;
    private String name;
    public static final Map<String, Upgrade> UPGRADES = new HashMap<>();
    public static final GeneratorBlock UNTEXTURED_RESOURCE_BLOCK = new GeneratorBlock();


    private GeneratorBlock()
    {
        super(AbstractBlock.Properties.from(Blocks.TNT));
        this.name = "unknown_resource";
        this.setRegistryName(ClickMiner.MOD_ID,name+"_block");
    }
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

    public void addUpgrade(Upgrade upgrade)
    {
        UPGRADES.put(upgrade.getName(),upgrade);
    }

    public Upgrade getUpgrade(String name)
    {
        return UPGRADES.get(name);
    }
}
