package pie.ilikepiefoo2.clickminer.clickergame.event.handlers;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.Color;
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
import pie.ilikepiefoo2.clickminer.util.TextHelper;
import pie.ilikepiefoo2.clickminer.util.capability.CapabilityClickerGameHandler;

// TODO Documentation
@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class GeneratorHandler
{
    public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onGeneratorTick(GeneratorEvent.Tick event)
    {
        // TODO do math for every Click Generator, instead of just one.
        //LOGGER.debug("Generator Tick");
        event.getGame().addResourceAmount(event.getGenerator().getProduces(), event.getGenerator().getGenerationPerTick().times(event.getTickCount()));
        event.getGenerator().nextProduct();
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
        if(event.getEntity().getCapability(CapabilityClickerGameHandler.CLICKER_GAME_CAPABILITY).isPresent()){
            ClickerGame game = (ClickerGame) event.getEntity().getCapability(CapabilityClickerGameHandler.CLICKER_GAME_CAPABILITY).resolve().get();
            Block block = event.getPlacedBlock().getBlock();
            if(block.matchesBlock(Register.DARK_OAK_TREE)) {
                game.addGenerator(event.getPos(), new Generator(event.getEntity().getUniqueID(), Resource.DARK_OAK_TREE, new BigNumber(1), GeneratorType.BREAK));
            } else if(block.matchesBlock(Register.FOREST)) {
                game.addGenerator(event.getPos(), new Generator(event.getEntity().getUniqueID(), Resource.FOREST, new BigNumber(1), GeneratorType.BREAK));
            } else if(block.matchesBlock(Register.OAK_TREE)) {
                game.addGenerator(event.getPos(), new Generator(event.getEntity().getUniqueID(), Resource.OAK_TREE, new BigNumber(1), GeneratorType.BREAK));
            } else if(block.matchesBlock(Register.CLICKER_BLOCK)) {
                game.addGenerator(event.getPos(), new Generator(event.getEntity().getUniqueID(), Resource.OAK_TREE, new BigNumber(1), GeneratorType.CLICK));
            }
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
                        Resource previous = generator.getProduces();
                        generator.tick(event.getEntity());
                        sendResourceGainInfo(event.getPlayer(),generator, previous);
                        event.getWorld().setBlockState(event.getPos(),generator.getProduces().getVisual().getDefaultState(),2);
                    }
                }else{
                    if(generator.getGeneratorType() != GeneratorType.AUTOMATIC)
                        sendGeneratorInfo(event.getPlayer(),generator);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onGeneratorBreak(BlockEvent.BreakEvent event)
    {
        if(event.getPlayer().getCapability(CapabilityClickerGameHandler.CLICKER_GAME_CAPABILITY).isPresent()) {
            Generator generator = ClickerGame.getGlobalGenerator(event.getPos());
            if(generator != null) {
                if (generator.getGeneratorType() == GeneratorType.BREAK) {
                    Resource previous = generator.getProduces();
                    generator.tick(event.getPlayer());
                    sendResourceGainInfo(event.getPlayer(),generator, previous);
                    event.getWorld().setBlockState(event.getPos(),generator.getProduces().getVisual().getDefaultState(),2);
                }else{
                    if(generator.getGeneratorType() != GeneratorType.AUTOMATIC)
                        sendGeneratorInfo(event.getPlayer(),generator);
                }
                event.setCanceled(true);
            }
        }
    }
    private static void sendGeneratorInfo(Entity entity,Generator generator){
        entity.sendMessage(
                new TextHelper().add("This generator is a ")
                        .color(Color.fromHex("#00ff00"))
                        .add(generator.getGeneratorType().name()+"able generator")
                        .empty()
                        .add(". Whenever you "+generator.getGeneratorType().name()+" it, you will receive ")
                        .color(Color.fromHex("#00ff00"))
                        .add(generator.getGenerationPerTick().toString()+"x "+generator.getProduces().getItem())
                        .getOutput()
                ,generator.getOwnedBy());
    }
    private static void sendResourceGainInfo(Entity entity, Generator generator, Resource resource)
    {
        ClickerGame game = generator.getGame(entity);
        entity.sendMessage(
                new TextHelper()
                        .add("You now have ")
                        .color(Color.fromHex("#ff0000"))
                        .add(game.getResourceAmount(resource).toString()+"x ")
                        .color(Color.fromHex("#00ff00"))
                        .add(resource.getItem().toString())
                        .empty()
                        .add(" in storage.")
                        .getOutput()
                ,generator.getOwnedBy());
    }
}
