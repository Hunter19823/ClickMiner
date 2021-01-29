package pie.ilikepiefoo2.clickminer.clickergame.upgrades;

import net.minecraft.nbt.CompoundNBT;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;
import pie.ilikepiefoo2.clickminer.util.BigNumber;
import pie.ilikepiefoo2.clickminer.util.Resource;

public abstract class ResourceTask implements ITask {
    protected Resource resource;
    protected BigNumber quantity;

    protected ResourceTask(Resource resource, BigNumber quantity){
        this.resource = resource;
        this.quantity = quantity;
    }

    protected ResourceTask(){}


    public boolean isFullFilled(ClickerGame game)
    {
        return game.getResourceAmount(this.resource).greaterThanEqual(this.quantity);
    }


    public Resource getResource()
    {
        return this.resource;
    }


    public BigNumber getQuantity()
    {
        return this.quantity;
    }

    public CompoundNBT toNBT()
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("Resource",this.resource.toNBT());
        compoundNBT.put("Quantity",this.quantity.toNBT());
        return  compoundNBT;
    }

    protected void fromNBT(CompoundNBT NBT)
    {
        this.resource =
                Resource.fromNBT(NBT.getCompound("Resource"));
        this.quantity =
                BigNumber.fromNBT(NBT.getCompound("Quantity"));
    }

}
