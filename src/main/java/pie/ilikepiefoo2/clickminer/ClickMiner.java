package pie.ilikepiefoo2.clickminer;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;
import pie.ilikepiefoo2.clickminer.clickergame.Generator;
import pie.ilikepiefoo2.clickminer.clickergame.event.GeneratorEvent;
import pie.ilikepiefoo2.clickminer.clickergame.event.handlers.ClickGeneratorHandler;
import pie.ilikepiefoo2.clickminer.clickergame.event.handlers.ClickerGameHandler;
import pie.ilikepiefoo2.clickminer.clickergame.generators.ClickGenerator;

@Mod("clickminer")
public class ClickMiner
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "clickminer";
    public static final ClickerGame GLOBAL_GAME = new ClickerGame();
    public ClickMiner()
    {
        LOGGER.info("This is the start of something beautiful...");
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.addListener(ClickGeneratorHandler::onPreGeneratorTick);
        MinecraftForge.EVENT_BUS.addListener(ClickGeneratorHandler::onPostGeneratorTick);
        MinecraftForge.EVENT_BUS.addListener(ClickGeneratorHandler::onGeneratorPlaced);
        MinecraftForge.EVENT_BUS.addListener(ClickGeneratorHandler::onGeneratorClick);
        MinecraftForge.EVENT_BUS.addListener(ClickerGameHandler::onMoneyChange);
        modBus.addGenericListener(Block.class, Register::registerBlocks);
        modBus.addGenericListener(Item.class, Register::registerItemBlocks);
    }

}
