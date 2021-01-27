package pie.ilikepiefoo2.clickminer.clickergame;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.clickergame.event.ClickerEvent;
import pie.ilikepiefoo2.clickminer.clickergame.generators.Generator;
import pie.ilikepiefoo2.clickminer.util.BigNumber;
import pie.ilikepiefoo2.clickminer.util.Resource;
import pie.ilikepiefoo2.clickminer.util.capability.ClickerGameProvider;

import java.util.HashMap;
import java.util.UUID;

// TODO Documentation
public class ClickerGame implements IClickerGame{
    private static final Logger LOGGER = LogManager.getLogger();
    private final HashMap<String, BigNumber> resourceList = new HashMap<>();
    private final HashMap<BlockPos, Generator> generatorMap = new HashMap<>();
    private static final HashMap<BlockPos, Generator> globalGeneratorMap = new HashMap<>();
    private boolean isValid = false;
    private ClickerGameProvider provider;


    private UUID owner;

    /**
     * Returns an immutable copy of the clicker game's balance.
     *
     * @return money
     */

    public ClickerGame()
    {
        // Random UUID used for testing:
        // 894363ee-3210-4cb4-bb60-10512f1ac0c5
        this.owner = UUID.fromString("894363ee-3210-4cb4-bb60-10512f1ac0c5");
    }
    public ClickerGame(ClickerGameProvider provider)
    {
        // Random UUID used for testing:
        // 894363ee-3210-4cb4-bb60-10512f1ac0c5
        this.owner = UUID.fromString("894363ee-3210-4cb4-bb60-10512f1ac0c5");
        this.provider = provider;
    }

    public ClickerGame(UUID player)
    {
        this.owner = player;
        //LOGGER.debug("A new Clicker Game has been made for "+player);
        this.isValid = true;
    }

    public static void main(String[] args)
    {
        System.out.println(UUID.randomUUID());
        /*
        ClickerGame testGame = new ClickerGame();
        Generator clickGenerator = new Generator(testGame.getOwner(),new Resource(Items.OAK_LOG, Blocks.OAK_LOG),new BigNumber(0.01),GeneratorType.CLICK);
        for(int i = 0; i<1000; i++) {
            int ticks = (int) (Math.random()*i);
            //LOGGER.info("$" + testGame.getMoney().toString());
            LOGGER.info("Now activating all generators for "+ticks+" ticks.");
            /*
            for (GeneratorType type : GeneratorType.values())

                if(testGame.getGenerators(type) != null)
                    for (Generator generator : testGame.getGenerators(type)) {
                        BigNumber sim = generator.simulateTicks(ticks);
                        LOGGER.info("Simulated output of "+generator.toString()+" = "+sim);
                        testGame.add(generator.simulateTicks(ticks));
                    }

        }

         */
    }

    @Override
    public UUID getOwner()
    {
        return this.owner;
    }

    @Override
    public HashMap<String, BigNumber> getResourceList()
    {
        return this.resourceList;
    }

    @Override
    public BigNumber getResourceAmount(Resource resource)
    {
        return resourceList.get(resource.toString());
    }

    @Override
    public void addResourceAmount(Resource resource, BigNumber amount)
    {
        String key = resource.toString();
        if(!resourceList.containsKey(key))
            resourceList.put(key,new BigNumber(0));
        BigNumber previous = new BigNumber(
                resourceList.get(key)
        );
        resourceList.get(key).add(amount);
        MinecraftForge.EVENT_BUS.post(new ClickerEvent.MoneyChanged(this,resource,previous,resourceList.get(key)));
    }

    @Override
    public HashMap<BlockPos, Generator> getGeneratorMap()
    {
        return generatorMap;
    }

    @Override
    public Generator getGenerator(BlockPos pos)
    {
        return generatorMap.get(pos);
    }

    public static Generator getGlobalGenerator(BlockPos pos)
    {
        return globalGeneratorMap.get(pos);
    }

    public void addGenerator(BlockPos pos,Generator generator)
    {
        globalGeneratorMap.put(pos,generator);
        generatorMap.put(pos,generator);
    }

    @Override
    public void setOwner(UUID owner)
    {
        //LOGGER.debug("Clicker game now has new owner: ("+this.owner+") -> ("+owner+")");
        this.owner = owner;
    }

    public void save()
    {
        this.provider.save(this);
    }

    @Override
    public void loadFromNBT(INBT compoundNBT)
    {
        CompoundNBT nbt = (CompoundNBT) compoundNBT;
        this.owner = nbt.getUniqueId("owner");
        CompoundNBT listNBT = nbt.getCompound( "resourceList");
        for(int i=0; i<listNBT.size(); i++){
            CompoundNBT resourceStorage  = listNBT.getCompound(i+"");
            addResourceAmount(
                    Resource.fromNBT(resourceStorage.getCompound("Resource")),
                    BigNumber.fromNBT(resourceStorage.getCompound("Number")));
        }
        listNBT = nbt.getCompound( "generatorList");
        for(int i=0; i<listNBT.size(); i++){
            CompoundNBT generatorData  = listNBT.getCompound(i+"");
            addGenerator(
                    BlockPos.fromLong(generatorData.getLong("pos")),
                    Generator.fromNBT(generatorData.getCompound("generator")));
        }
        this.isValid = true;
        //LOGGER.debug("Clicker Game being loaded from NBT. Now printing: ");
        //LOGGER.debug(this.owner);
        //LOGGER.debug(compoundNBT.toString());
    }

    @Override
    public CompoundNBT toNBT()
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putUniqueId("owner",this.getOwner());
        CompoundNBT listNBT = new CompoundNBT();
        HashMap<String,BigNumber> resourceList = this.getResourceList();
        int i = 0;
        for(String resourceNBT : resourceList.keySet()) {
            BigNumber number = resourceList.get(resourceNBT);
            Resource resource = Resource.fromString(resourceNBT);
            CompoundNBT nbt = new CompoundNBT();
            nbt.put("Resource",resource.toNBT());
            nbt.put("Number",number.toNBT());
            listNBT.put(i+"",nbt);
            i++;
        }
        compoundNBT.put("resourceList",listNBT);

        CompoundNBT listNBT2 = new CompoundNBT();

        HashMap<BlockPos, Generator> generatorHashMap = this.getGeneratorMap();
        i = 0;
        for(BlockPos pos : generatorHashMap.keySet()) {
            CompoundNBT nbt = new CompoundNBT();
            Generator generator = generatorHashMap.get(pos);
            nbt.put("generator",generator.toNBT());
            nbt.putLong("pos",pos.toLong());
            listNBT2.put(i+"",nbt);
            i++;
        }
        compoundNBT.put("generatorList",listNBT2);
        return compoundNBT;
    }
}
