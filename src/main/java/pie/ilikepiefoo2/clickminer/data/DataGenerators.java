package pie.ilikepiefoo2.clickminer.data;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DataGenerators {
    private static final Logger LOGGER = LogManager.getLogger();


    public static void gatherData(GatherDataEvent event)
    {
        LOGGER.info("Gathering Data for Data Generator...");
        if(event.includeServer())
        {

        }
        if(event.includeClient())
        {
            event.getGenerator().addProvider(new BlockstateProvider(event.getGenerator(),event.getExistingFileHelper()));
            event.getGenerator().addProvider(new LanguageProvider(event.getGenerator(),"en_us"));
            event.getGenerator().addProvider(new ItemModelProvider(event.getGenerator(),event.getExistingFileHelper()));
        }
    }
}
