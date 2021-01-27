package pie.ilikepiefoo2.clickminer.clickergame.generators;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.ClickMiner;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;
import pie.ilikepiefoo2.clickminer.clickergame.event.GeneratorEvent;
import pie.ilikepiefoo2.clickminer.util.BigNumber;
import pie.ilikepiefoo2.clickminer.util.Resource;
import pie.ilikepiefoo2.clickminer.util.capability.CapabilityClickerGameHandler;
import pie.ilikepiefoo2.clickminer.util.capability.ClickerGameProvider;

import java.util.UUID;

// TODO Documentation
public class Generator {
    private static final Logger LOGGER = LogManager.getLogger();
    private BigNumber generationPerTick;
    private ClickerGame game;
    private GeneratorType generatorType;
    private Resource produces;
    private UUID ownedBy;

    public Generator(UUID ownedBy, Resource produces, BigNumber generationPerTick, GeneratorType generatorType){
        // TODO !fix logic!
        this.ownedBy = ownedBy;
        this.produces = produces;
        this.generationPerTick = generationPerTick;
        this.generatorType = generatorType;
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName()+"{" +
                "generationPerTick=" + generationPerTick +
                '}';
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
        return MinecraftForge.EVENT_BUS.post(new GeneratorEvent.BeforeGeneratorTick(this,owner,tickCount));
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

    public UUID getOwnedBy()
    {
        return ownedBy;
    }

    public CompoundNBT toNBT()
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putUniqueId("ownedBy",this.ownedBy);
        nbt.put("produces",this.produces.toNBT());
        nbt.put("generationRate",this.generationPerTick.toNBT());
        nbt.putString("type",this.generatorType.name());
        return nbt;
    }
    public static Generator fromNBT(CompoundNBT nbt)
    {
        GeneratorType type = GeneratorType.valueOf(nbt.getString("type"));

        return new Generator(
                nbt.getUniqueId("ownedBy"),
                Resource.fromNBT((CompoundNBT) nbt.get("produces")),
                BigNumber.fromNBT((CompoundNBT) nbt.get("generationRate")),
                GeneratorType.valueOf(nbt.getString("type"))
        );
    }
}
