package pie.ilikepiefoo2.clickminer.clickergame.event.handlers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.state.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.Register;
import pie.ilikepiefoo2.clickminer.clickergame.BigNumber;
import pie.ilikepiefoo2.clickminer.clickergame.Generator;
import pie.ilikepiefoo2.clickminer.clickergame.event.GeneratorEvent;
import pie.ilikepiefoo2.clickminer.clickergame.generators.ClickGenerator;
import pie.ilikepiefoo2.clickminer.clickergame.generators.GeneratorType;

import static pie.ilikepiefoo2.clickminer.ClickMiner.GLOBAL_GAME;

// TODO Documentation
@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class ClickGeneratorHandler
{
    public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onPreGeneratorTick(GeneratorEvent.BeforeGeneratorTick<ClickGenerator> event)
    {
        // TODO do math for every Click Generator, instead of just one.
        BigNumber total = new BigNumber(0);
        for(Generator generator : event.getGame().getGenerators(GeneratorType.CLICK)) {
            total.add(generator.simulateTicks(event.getTickCount()));
        }
        event.getGenerator().getGame().add(total);
    }

    @SubscribeEvent
    public static void onPostGeneratorTick(GeneratorEvent.AfterGeneratorTick<ClickGenerator> event)
    {
        // TODO do math for every Click Generator, instead of just one.
        event.getGame().add(event.getAmountGenerated());
    }



    @SubscribeEvent
    public static void onGeneratorPlaced(BlockEvent.EntityPlaceEvent event)
    {
        if(event.getPlacedBlock().getBlock().matchesBlock(Register.CLICKER_BLOCK)){
            LOGGER.info("A new Clicker has been anchored.");
            ClickGenerator generator = new ClickGenerator(GLOBAL_GAME,1);
            GLOBAL_GAME.anchor(event.getPos(),generator);
        }
    }

    @SubscribeEvent
    public static void onGeneratorClick(PlayerInteractEvent.RightClickBlock event)
    {
        Generator generator = GLOBAL_GAME.getGenerator(event.getPos());
        if(generator!=null){
            if(generator.getGeneratorType() == GeneratorType.CLICK){
                if(event.getWorld() != null && event.getWorld().getServer() != null && event.getWorld().getServer().getTickCounter()-generator.getLastCollected()>20) {
                    generator.setLastCollected(event.getWorld().getServer().getTickCounter());
                    generator.tick();
                }
            }
        }
    }
}
