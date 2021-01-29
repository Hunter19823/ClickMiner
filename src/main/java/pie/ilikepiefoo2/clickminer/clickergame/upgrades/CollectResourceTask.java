package pie.ilikepiefoo2.clickminer.clickergame.upgrades;

import net.minecraft.nbt.CompoundNBT;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;
import pie.ilikepiefoo2.clickminer.util.BigNumber;
import pie.ilikepiefoo2.clickminer.util.Resource;

public class CollectResourceTask extends ResourceTask implements ITask {
    public static final String TASK_TYPE = "CollectResource";

    public CollectResourceTask(CompoundNBT nbt){
        super();
        fromNBT(nbt);
    }

    public CollectResourceTask(Resource resource, BigNumber quantity){
        super(resource, quantity);
    }

    @Override
    public void complete(ClickerGame game)
    { }

    @Override
    public String getTaskType()
    {
        return TASK_TYPE;
    }

    @Override
    public boolean changesData()
    {
        return false;
    }

    @Override
    public CompoundNBT toNBT()
    {
        CompoundNBT nbt = super.toNBT();
        nbt.putString("Type",getTaskType());
        return nbt;
    }
}
