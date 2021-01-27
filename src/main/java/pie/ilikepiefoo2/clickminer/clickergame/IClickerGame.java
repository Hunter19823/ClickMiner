package pie.ilikepiefoo2.clickminer.clickergame;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.math.BlockPos;
import pie.ilikepiefoo2.clickminer.clickergame.generators.Generator;
import pie.ilikepiefoo2.clickminer.util.BigNumber;
import pie.ilikepiefoo2.clickminer.util.Resource;

import java.util.HashMap;
import java.util.UUID;

public interface IClickerGame {




    UUID getOwner();
    void setOwner(UUID owner);

    HashMap<String, BigNumber> getResourceList();

    BigNumber getResourceAmount(Resource resource);
    void addResourceAmount(Resource resource, BigNumber amount);
    HashMap<BlockPos,Generator> getGeneratorMap();

    Generator getGenerator(BlockPos resource);;



    void loadFromNBT(INBT nbt);
    CompoundNBT toNBT();

}
