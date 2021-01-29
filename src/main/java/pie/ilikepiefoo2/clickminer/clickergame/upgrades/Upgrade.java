package pie.ilikepiefoo2.clickminer.clickergame.upgrades;

import net.minecraft.nbt.CompoundNBT;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;

import java.util.ArrayList;

public abstract class Upgrade {
    private final ArrayList<ITask> requirements = new ArrayList<ITask>();
    private String name;

    protected Upgrade()
    {

    }

    public Upgrade(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public Upgrade addRequirement(ITask task)
    {
        requirements.add(task);
        return this;
    }


    public boolean meetsRequirements(ClickerGame game)
    {
        for(ITask requirement : requirements)
            if(!requirement.isFullFilled(game))
                return false;
        return true;
    }


    public void applyUpgrade(ClickerGame game)
    {
        for(ITask requirement : requirements)
            requirement.complete(game);
    }


    public boolean hasUpgrade(ClickerGame game)
    {
        // TODO add known upgrades to player.
        return false;
    }


    public CompoundNBT toNBT()
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("name",this.name);
        CompoundNBT requirements = new CompoundNBT();
        for(int i=0; i<this.requirements.size(); i++)
        {
            ITask task = this.requirements.get(i);
            requirements.put(i+"",task.toNBT());
        }
        nbt.put("requirements",requirements);
        return null;
    }

    public void fromNBT(CompoundNBT nbt)
    {
        this.name = nbt.getString("name");
        CompoundNBT requirements = nbt.getCompound("requirements");
        for(int i=0; i<requirements.size();i++)
        {
            CompoundNBT requirement = requirements.getCompound(i+"");
            switch(requirement.getString("Type")){
                case CollectResourceTask.TASK_TYPE:
                    addRequirement(new CollectResourceTask(requirement));
                    break;
                case ConsumeResourceTask.TASK_TYPE:
                    addRequirement(new ConsumeResourceTask(requirement));
                    break;
            }
        }
    }

    protected abstract void applyReward(ClickerGame game);
}