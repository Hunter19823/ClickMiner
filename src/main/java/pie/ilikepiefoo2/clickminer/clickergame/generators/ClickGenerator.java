package pie.ilikepiefoo2.clickminer.clickergame.generators;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.clickergame.BigNumber;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;
import pie.ilikepiefoo2.clickminer.clickergame.Generator;
import pie.ilikepiefoo2.clickminer.clickergame.event.GeneratorEvent;

// TODO Documentation
public class ClickGenerator extends Generator {
    private static final Logger LOGGER = LogManager.getLogger();

    public ClickGenerator(ClickerGame game, BigNumber generationPerTick)
    {
        super(game, generationPerTick, GeneratorType.CLICK);
    }

    public ClickGenerator(ClickerGame game, double generationPerTick)
    {
        super(game, generationPerTick, GeneratorType.CLICK);
    }

    @Override
    public boolean tick(BigNumber tickCount)
    {
        return MinecraftForge.EVENT_BUS.post(new GeneratorEvent.BeforeGeneratorTick<ClickGenerator>(this,tickCount));
    }
}
