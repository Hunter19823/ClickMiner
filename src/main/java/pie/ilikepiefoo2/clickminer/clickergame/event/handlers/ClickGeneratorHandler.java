package pie.ilikepiefoo2.clickminer.clickergame.event.handlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.Register;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;
import pie.ilikepiefoo2.clickminer.clickergame.generators.Generator;
import pie.ilikepiefoo2.clickminer.clickergame.event.GeneratorEvent;
import pie.ilikepiefoo2.clickminer.clickergame.generators.GeneratorType;
import pie.ilikepiefoo2.clickminer.util.BigNumber;
import pie.ilikepiefoo2.clickminer.util.Resource;
import pie.ilikepiefoo2.clickminer.util.capability.CapabilityClickerGameHandler;

// TODO Documentation
@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class ClickGeneratorHandler
{
    public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onPreGeneratorTick(GeneratorEvent.BeforeGeneratorTick<Generator> event)
    {
        // TODO do math for every Click Generator, instead of just one.
        LOGGER.debug("Pre Generator Tick");
        event.getGame().addResourceAmount(event.getGenerator().getProduces(), event.getGenerator().getGenerationPerTick().times(event.getTickCount()));

        /*
        BigNumber total = new BigNumber(0);

        for(Generator generator : event.getGame().getGenerators(GeneratorType.CLICK)) {
            total.add(generator.simulateTicks(event.getTickCount()));
        }
        event.getGenerator().getGame().add(total);
         */

    }

    /*
    @SubscribeEvent
    public static void onPostGeneratorTick(GeneratorEvent.AfterGeneratorTick<Generator> event)
    {
        // TODO do math for every Click Generator, instead of just one.
        LOGGER.debug("Post Generator Tick");
        //event.getGame().add(event.getAmountGenerated());
    }

     */



    @SubscribeEvent
    public static void onGeneratorPlaced(BlockEvent.EntityPlaceEvent event)
    {
        //LOGGER.debug("Entity placed generator with of UUID "+event.getEntity().getUniqueID());
        if(event.getPlacedBlock().getBlock().matchesBlock(Register.CLICKER_BLOCK) && event.getEntity().getCapability(CapabilityClickerGameHandler.CLICKER_GAME_CAPABILITY).isPresent()){
            ClickerGame game = (ClickerGame) event.getEntity().getCapability(CapabilityClickerGameHandler.CLICKER_GAME_CAPABILITY).resolve().get();
            //LOGGER.debug("A new Clicker has been anchored for entity: "+game.getOwner());
            //LOGGER.debug("Entities's UUID "+event.getEntity().getUniqueID());
            game.addGenerator(event.getPos(), new Generator(event.getEntity().getUniqueID(), Resource.OAK_PLANKS,new BigNumber(1), GeneratorType.CLICK));
            //ClickGenerator generator = new ClickGenerator(GLOBAL_GAME,1);
            //GLOBAL_GAME.anchor(event.getPos(),generator);
        }
    }

    @SubscribeEvent
    public static void onGeneratorClick(PlayerInteractEvent.RightClickBlock event)
    {
        //LOGGER.debug("Player Right Click Block");
        if(event.getEntity().getCapability(CapabilityClickerGameHandler.CLICKER_GAME_CAPABILITY).isPresent()) {
            Generator generator = ClickerGame.getGlobalGenerator(event.getPos());
            if (generator != null) {
                if (generator.getGeneratorType() == GeneratorType.CLICK) {
                    if (event.getWorld() != null && event.getWorld().getServer() != null) {
                        generator.tick(event.getEntity());
                    }
                }
            }
        }
    }
}
