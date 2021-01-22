package pie.ilikepiefoo2.clickminer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.clickergame.event.handlers.ClickGeneratorHandler;

@Mod("clickminer")
public class ClickMiner
{
    private static final Logger LOGGER = LogManager.getLogger();

    public ClickMiner()
    {
        LOGGER.info("This is the start of something beautiful...");
        MinecraftForge.EVENT_BUS.register(new ClickGeneratorHandler());
    }

}
