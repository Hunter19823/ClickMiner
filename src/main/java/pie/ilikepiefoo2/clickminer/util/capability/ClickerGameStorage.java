package pie.ilikepiefoo2.clickminer.util.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;
import pie.ilikepiefoo2.clickminer.clickergame.IClickerGame;

public class ClickerGameStorage implements Capability.IStorage<IClickerGame> {
    @Override
    public INBT writeNBT(Capability<IClickerGame> capability, IClickerGame instance, Direction side)
    {
        return instance.toNBT();
    }

    @Override
    public void readNBT(Capability<IClickerGame> capability, IClickerGame instance, Direction side, INBT nbt)
    {
        if (!(instance instanceof ClickerGame))
            throw new RuntimeException("IClickerGame instance does not implement IClickerGame");
        ClickerGame clickerGame = (ClickerGame) instance;
        clickerGame.loadFromNBT(nbt);
    }
}
