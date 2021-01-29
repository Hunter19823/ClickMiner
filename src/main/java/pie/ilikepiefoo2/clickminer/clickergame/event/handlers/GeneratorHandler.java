package pie.ilikepiefoo2.clickminer.clickergame.event.handlers;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
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
import pie.ilikepiefoo2.clickminer.common.blocks.GeneratorBlock;
import pie.ilikepiefoo2.clickminer.common.lib.LibCustomBlocks;
import pie.ilikepiefoo2.clickminer.util.BigNumber;
import pie.ilikepiefoo2.clickminer.util.Resource;
import pie.ilikepiefoo2.clickminer.util.TextHelper;
import pie.ilikepiefoo2.clickminer.util.capability.CapabilityClickerGameHandler;

import java.util.Map;

// TODO Documentation
@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class GeneratorHandler
{
    public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onGeneratorTick(GeneratorEvent.Tick event)
    {
        // TODO do math for every Click Generator, instead of just one.
        LOGGER.debug("Generator Tick for "+event.getGenerator().getProduces().getItem().toString());
        if(!event.getGenerator().getProduces().getItem().equals(Items.AIR))
            event.getGame().addResourceAmount(event.getGenerator().getProduces(), event.getGenerator().getGenerationPerTick().times(event.getTickCount()));
        event.getGenerator().nextProduct();
    }

    @SubscribeEvent
    public static void onGeneratorPlaced(BlockEvent.EntityPlaceEvent event)
    {
        //LOGGER.debug("Entity placed generator with of UUID "+event.getEntity().getUniqueID());
        if(event.getEntity().getCapability(CapabilityClickerGameHandler.CLICKER_GAME_CAPABILITY).isPresent()){
            ClickerGame game = (ClickerGame) event.getEntity().getCapability(CapabilityClickerGameHandler.CLICKER_GAME_CAPABILITY).resolve().get();
            Block block = event.getPlacedBlock().getBlock();
            if(block instanceof GeneratorBlock)
            {
                GeneratorBlock generatorBlock = ((GeneratorBlock) block);
                if(game.hasGenerator(generatorBlock.getGenerator().getName())){
                    //LOGGER.debug("Player already has this generator!");
                    BlockPos genPosition = game.getGenerator(generatorBlock.getGenerator().getName()).getKey();
                    event.getEntity().sendMessage(
                            new TextHelper()
                                    .add("I'm sorry but you already have the \"")
                                    .color(Color.fromHex("#00ff00"))
                                    .add(generatorBlock.getTranslatedName())
                                    .empty()
                                    .add("\" at the coordinates ")
                                    .color(Color.fromHex("#00ff00"))
                                    .add(String.format("(X:%d,Y:%d,Z:%d)",genPosition.getX(),genPosition.getY(),genPosition.getZ()))
                            .getOutput(),
                        game.getOwner());
                    event.setCanceled(true);
                }else{
                    Generator generator = generatorBlock.getGenerator().clone();
                    generator.setOwnedBy(game.getOwner());
                    game.addGenerator(event.getPos(),generator);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onGeneratorClick(PlayerInteractEvent.RightClickBlock event)
    {
        //LOGGER.debug("Player Right Click Block");
        if(ClickerGame.getGlobalGenerator(event.getPos()) != null) {
            if (event.getEntity().getCapability(CapabilityClickerGameHandler.CLICKER_GAME_CAPABILITY).isPresent()) {
                ClickerGame game = (ClickerGame) event.getEntity().getCapability(CapabilityClickerGameHandler.CLICKER_GAME_CAPABILITY).resolve().get();
                Generator generator = game.getGenerator(event.getPos());
                if (generator != null) {
                    // TODO make generator event for code below.
                    if (generator.getGeneratorType().equals(GeneratorType.CLICK)) {
                        if (!generator.isAir()) {
                            Resource previous = generator.getProduces();
                            generator.tick(event.getEntity());
                            sendResourceGainInfo(event.getPlayer(), generator, previous);
                        }
                        event.getWorld().setBlockState(event.getPos(), generator.getProduces().getVisual().getDefaultState(), 2);
                    } else {
                        if (!generator.getGeneratorType().equals(GeneratorType.AUTOMATIC))
                            sendGeneratorInfo(event.getPlayer(), generator);
                    }
                }else{
                    event.setCanceled(true);
                }
            }else{
                event.setCanceled(true);
            }
            // TODO tell player they don't own this generator.
            // if(event.isCanceled())
        }

    }

    @SubscribeEvent
    public static void onGeneratorBreak(BlockEvent.BreakEvent event)
    {
        if(ClickerGame.getGlobalGenerator(event.getPos()) != null) {
            event.setCanceled(true);
            if (event.getPlayer().getCapability(CapabilityClickerGameHandler.CLICKER_GAME_CAPABILITY).isPresent()) {
                ClickerGame game = (ClickerGame) event.getPlayer().getCapability(CapabilityClickerGameHandler.CLICKER_GAME_CAPABILITY).resolve().get();
                Generator generator = game.getGenerator(event.getPos());
                if (generator != null) {
                    if (generator.getGeneratorType().equals(GeneratorType.BREAK)) {
                        if(!generator.isAir()) {
                            Resource previous = generator.getProduces();
                            generator.tick(event.getPlayer());
                            sendResourceGainInfo(event.getPlayer(), generator, previous);
                        }
                        event.getWorld().setBlockState(event.getPos(), generator.getProduces().getVisual().getDefaultState(), 2);
                    } else {
                        if (!generator.getGeneratorType().equals(GeneratorType.AUTOMATIC))
                            sendGeneratorInfo(event.getPlayer(), generator);
                    }
                }else{
                    // TODO tell player they don't own this generator.
                }
            }else{
                // TODO tell player they don't own this generator.
            }
        }

    }
    private static void sendGeneratorInfo(Entity entity,Generator generator)
    {
        // TODO move into generator class
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
        // TODO move into generator class
        ClickerGame game = generator.getGame(entity);
        if(!game.getResourceAmount(resource).equals(resource))
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
