package pie.ilikepiefoo2.clickminer.util.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.ClickMiner;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;
import pie.ilikepiefoo2.clickminer.clickergame.IClickerGame;

import java.util.UUID;

@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class CapabilityClickerGameHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    @CapabilityInject(IClickerGame.class)
    public static Capability<IClickerGame> CLICKER_GAME_CAPABILITY = null;


    public static void register()
    {
        //LOGGER.debug("Registering Capability Manager...");
        CapabilityManager.INSTANCE.register(IClickerGame.class, new ClickerGameStorage(), ClickerGame::new);
    }

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> attachCapabilitiesEvent)
    {
        if (attachCapabilitiesEvent.getObject() instanceof ServerPlayerEntity) {
            // The entity is a player.
            if(!attachCapabilitiesEvent.getCapabilities().containsKey(ResourceLocation.tryCreate("clickminer:clickergame"))) {
                //LOGGER.debug("Creating new capability...");
                // Entity now has a capability handler attached to them.
                attachCapabilitiesEvent.addCapability(ResourceLocation.tryCreate("clickminer:clickergame"), new ClickerGameProvider());
            }else{
                //LOGGER.debug("Using existing capability...");
            }
        }
    }
}
