package pie.ilikepiefoo2.clickminer.clickergame.upgrades;

import net.minecraft.nbt.CompoundNBT;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;

public interface ITask {

    boolean isFullFilled(ClickerGame game);

    boolean changesData();

    String getTaskType();

    void complete(ClickerGame game);

    CompoundNBT toNBT();
}
