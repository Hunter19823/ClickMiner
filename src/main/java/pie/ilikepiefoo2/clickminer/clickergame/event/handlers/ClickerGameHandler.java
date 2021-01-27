package pie.ilikepiefoo2.clickminer.clickergame.event.handlers;


import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.clickergame.IClickerGame;
import pie.ilikepiefoo2.clickminer.clickergame.event.ClickerEvent;

import java.util.UUID;

import static pie.ilikepiefoo2.clickminer.util.capability.CapabilityClickerGameHandler.CLICKER_GAME_CAPABILITY;

// TODO Documentation

@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class ClickerGameHandler {
    public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onMoneyChange(ClickerEvent.MoneyChanged event)
    {
        LOGGER.debug(String.format("Resource %s has changed: %s -> %s (%s)",event.getResource().getItem().toString(),event.getPreviousBalance(),event.getNewBalance(),event.getDifference()));
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        INBT inbt = CLICKER_GAME_CAPABILITY.writeNBT(event.getOriginal().getCapability(CLICKER_GAME_CAPABILITY).resolve().get(), Direction.DOWN);
        CLICKER_GAME_CAPABILITY.readNBT(event.getPlayer().getCapability(CLICKER_GAME_CAPABILITY).resolve().get(),Direction.DOWN,inbt);
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        //LOGGER.debug("Player joined...");
        if(event.getPlayer().getGameProfile() != null) {
            if(event.getEntity().getCapability(CLICKER_GAME_CAPABILITY).isPresent()){
                IClickerGame instance = event.getEntity().getCapability(CLICKER_GAME_CAPABILITY).resolve().get();
                UUID expected = event.getPlayer().getGameProfile().getId();
                if(!expected.equals(instance.getOwner())) {
                    //LOGGER.debug("Player joined with gameprofile uuid of: "+event.getPlayer().getGameProfile().getId());
                    //LOGGER.debug("Player's instance ID is different with a uuid of: "+instance.getOwner());
                    event.getEntity().getCapability(CLICKER_GAME_CAPABILITY).resolve().get().setOwner(expected);
                }
            }else{
                //LOGGER.debug("Capability not present.");
            }
        }else{
            //LOGGER.debug("GameProfile not present.");
        }
    }
}
