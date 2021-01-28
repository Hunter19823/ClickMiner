package pie.ilikepiefoo2.clickminer;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.clickergame.IClickerGame;
import pie.ilikepiefoo2.clickminer.clickergame.event.handlers.GeneratorHandler;
import pie.ilikepiefoo2.clickminer.clickergame.event.handlers.ClickerGameHandler;
import pie.ilikepiefoo2.clickminer.data.DataGenerators;
import pie.ilikepiefoo2.clickminer.util.capability.CapabilityClickerGameHandler;

import java.util.HashMap;
import java.util.UUID;

@Mod("clickminer")
public class ClickMiner
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "clickminer";
    public static final HashMap<UUID,Capability<IClickerGame>> PLAYER_CAPABILITIES = new HashMap<>();


    public ClickMiner()
    {
        LOGGER.debug("Click Miner Initialization");
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(ClickMiner::onCommonSetup);
        modBus.addListener(DataGenerators::gatherData);
        MinecraftForge.EVENT_BUS.register(GeneratorHandler.class);
        MinecraftForge.EVENT_BUS.register(ClickerGameHandler.class);
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class,CapabilityClickerGameHandler::attachCapability);
        /*
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class,CapabilityClickerGameHandler::attachCapability);
        MinecraftForge.EVENT_BUS.addListener(ClickGeneratorHandler::onPreGeneratorTick);
        MinecraftForge.EVENT_BUS.addListener(ClickGeneratorHandler::onPostGeneratorTick);
        MinecraftForge.EVENT_BUS.addListener(ClickGeneratorHandler::onGeneratorPlaced);
        MinecraftForge.EVENT_BUS.addListener(ClickGeneratorHandler::onGeneratorClick);
        MinecraftForge.EVENT_BUS.addListener(ClickerGameHandler::onMoneyChange);
        MinecraftForge.EVENT_BUS.addListener(ClickerGameHandler::onPlayerClone);

         */

        modBus.addGenericListener(Block.class, Register::registerBlocks);
        modBus.addGenericListener(Item.class, Register::registerItemBlocks);
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event){
        LOGGER.debug("Click Miner Common Setup Event.");
        CapabilityClickerGameHandler.register();
    }






}
