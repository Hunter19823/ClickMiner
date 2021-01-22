package pie.ilikepiefoo2.clickminer.clickergame.event.handlers;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import pie.ilikepiefoo2.clickminer.clickergame.BigNumber;
import pie.ilikepiefoo2.clickminer.clickergame.event.GeneratorEvent;
import pie.ilikepiefoo2.clickminer.clickergame.generators.ClickGenerator;

// TODO Documentation
@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class ClickGeneratorHandler
{
    @SubscribeEvent
    public static void onGeneratorTick(GeneratorEvent.BeforeGeneratorTick<ClickGenerator> event)
    {
        // TODO do math for every Click Generator, instead of just one.
        BigNumber total = event.getGenerator().simulateTicks(event.getTickCount());
        event.getGenerator().getGame().add(total);
        MinecraftForge.EVENT_BUS.post(new GeneratorEvent.AfterGeneratorTick<ClickGenerator>(event.getGenerator(),event.getTickCount(),total));
    }
}
