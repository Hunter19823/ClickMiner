package pie.ilikepiefoo2.clickminer.util.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.ClickMiner;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;
import pie.ilikepiefoo2.clickminer.clickergame.IClickerGame;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ClickerGameProvider implements ICapabilitySerializable<CompoundNBT> {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if(this.handler == null)
            this.handler = LazyOptional.of(this::getClickerHandler).cast();
        return (LazyOptional<T>) this.handler;
    }

    private LazyOptional<IClickerGame> handler;

    public void save(ClickerGame game)
    {
        //LOGGER.debug("Now saving...");
        CapabilityClickerGameHandler.CLICKER_GAME_CAPABILITY.writeNBT(game,Direction.DOWN);
    }

    private IClickerGame getClickerHandler()
    {
        // TODO Figure out how to use this.
        //LOGGER.debug("Creating new clicker handler");
        ClickerGame game = new ClickerGame(this);
        return game;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap)
    {
        if(this.handler == null)
            this.handler = LazyOptional.of(this::getClickerHandler).cast();
        return (LazyOptional<T>) this.handler;
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        //LOGGER.debug("Serializing NBT...");
        return getCapability(CapabilityClickerGameHandler.CLICKER_GAME_CAPABILITY).resolve().get().toNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        //LOGGER.debug("De-Serializing NBT...");
        //LOGGER.debug(nbt.toString());
        if(this.handler == null)
            this.handler = LazyOptional.of(this::getClickerHandler).cast();
        this.handler.resolve().get().loadFromNBT(nbt);
    }
}
