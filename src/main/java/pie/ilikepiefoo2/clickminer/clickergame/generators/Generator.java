package pie.ilikepiefoo2.clickminer.clickergame.generators;

import net.minecraft.entity.Entity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;
import pie.ilikepiefoo2.clickminer.clickergame.event.GeneratorEvent;
import pie.ilikepiefoo2.clickminer.util.BigNumber;
import pie.ilikepiefoo2.clickminer.util.Resource;
import pie.ilikepiefoo2.clickminer.util.WeightedRandom;
import pie.ilikepiefoo2.clickminer.util.capability.CapabilityClickerGameHandler;

import java.util.UUID;

// TODO Documentation
public class Generator {
    private static final Logger LOGGER = LogManager.getLogger();
    private String name;
    private BigNumber generationPerTick;
    private GeneratorType generatorType;
    private Resource produces;
    private UUID ownedBy;
    private WeightedRandom<Resource> resourceWeightedRandom;
    // TODO Add Upgrades to Generators.

    public Generator(String name, WeightedRandom<Resource> produces, BigNumber generationPerTick, GeneratorType generatorType)
    {
        this.name = name;
        this.generationPerTick = generationPerTick;
        this.generatorType = generatorType;
        this.resourceWeightedRandom = produces;
        this.produces = produces.next();
    }

    public Generator(String name, UUID ownedBy, WeightedRandom<Resource> produces, BigNumber generationPerTick, GeneratorType generatorType){
        // TODO !fix logic!
        this.name = name;
        this.ownedBy = ownedBy;
        this.generationPerTick = generationPerTick;
        this.generatorType = generatorType;
        this.resourceWeightedRandom = produces;
        this.produces = produces.next();
    }

    @Override
    public Generator clone()
    {
        return new Generator(this.name, this.resourceWeightedRandom,this.generationPerTick.clone(),this.generatorType);
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName()+"{" +
                "generationPerTick=" + generationPerTick +
                '}';
    }

    public void setOwnedBy(UUID ownedBy)
    {
        this.ownedBy = ownedBy;
    }

    public WeightedRandom<Resource> getResourceWeightedRandom()
    {
        return resourceWeightedRandom;
    }

    public String getName()
    {
        return name;
    }

    public ClickerGame getGame(Entity entity)
    {
        if(entity.getCapability(CapabilityClickerGameHandler.CLICKER_GAME_CAPABILITY).isPresent()){
            return (ClickerGame) entity.getCapability(CapabilityClickerGameHandler.CLICKER_GAME_CAPABILITY).resolve().get();
        }else{
            return null;
        }
    }

    public boolean tick(Entity owner)
    {
        return this.tick(owner,new BigNumber(1));
    }

    public boolean tick(Entity owner, BigNumber tickCount)
    {
        return MinecraftForge.EVENT_BUS.post(new GeneratorEvent.Tick(this,owner,tickCount));
    }

    public BigNumber getGenerationPerTick()
    {
        return generationPerTick;
    }

    public GeneratorType getGeneratorType()
    {
        return generatorType;
    }

    public Resource getProduces()
    {
        return produces;
    }

    public boolean isAir()
    {
        if(!produces.getItem().equals(Items.AIR))
            return false;
        nextProduct();
        return true;
    }

    public Resource nextProduct()
    {
        this.produces = this.resourceWeightedRandom.next();
        return this.produces;
    }

    public UUID getOwnedBy()
    {
        return ownedBy;
    }

    public CompoundNBT toNBT()
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putUniqueId("ownedBy",this.ownedBy);
        nbt.put("resourceWeightedRandom",this.resourceWeightedRandom.toNBT());
        nbt.put("generationRate",this.generationPerTick.toNBT());
        nbt.putString("type",this.generatorType.name());
        nbt.putString("name",this.name);
        return nbt;
    }
    public static Generator fromNBT(CompoundNBT nbt)
    {
        return new Generator(
                nbt.getString("name"),
                nbt.getUniqueId("ownedBy"),
                WeightedRandom.fromNBT((CompoundNBT) nbt.get("resourceWeightedRandom")),
                BigNumber.fromNBT((CompoundNBT) nbt.get("generationRate")),
                GeneratorType.valueOf(nbt.getString("type"))
        );
    }
}
