package pie.ilikepiefoo2.clickminer.clickergame.event.handlers;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.clickergame.event.ClickerEvent;

// TODO Documentation
@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class ClickerGameHandler {
    public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onMoneyChange(ClickerEvent.MoneyChanged event)
    {
        LOGGER.info(String.format("Money Changed: %s -> %s (%s)",event.getPreviousBalance(),event.getNewBalance(),event.getDifference()));
    }
}
