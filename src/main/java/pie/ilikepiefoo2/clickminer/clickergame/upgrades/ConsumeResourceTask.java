package pie.ilikepiefoo2.clickminer.clickergame.upgrades;

import net.minecraft.nbt.CompoundNBT;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;
import pie.ilikepiefoo2.clickminer.util.BigNumber;
import pie.ilikepiefoo2.clickminer.util.Resource;

public class ConsumeResourceTask extends ResourceTask implements ITask {
    public static final String TASK_TYPE = "ConsumeResource";

    public ConsumeResourceTask(CompoundNBT nbt){
        super();
        fromNBT(nbt);
    }

    public ConsumeResourceTask(Resource resource, BigNumber quantity){
        super(resource,quantity);
    }

    @Override
    public void complete(ClickerGame game)
    {
        game.getResourceAmount(this.resource).subtract(quantity);
    }

    @Override
    public boolean changesData()
    {
        return true;
    }

    @Override
    public String getTaskType()
    {
        return TASK_TYPE;
    }

    @Override
    public CompoundNBT toNBT()
    {
        CompoundNBT nbt = super.toNBT();
        nbt.putString("Type",getTaskType());
        return nbt;
    }
}
