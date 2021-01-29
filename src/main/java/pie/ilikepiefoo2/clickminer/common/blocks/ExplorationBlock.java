package pie.ilikepiefoo2.clickminer.common.blocks;

import net.minecraft.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;
import pie.ilikepiefoo2.clickminer.clickergame.generators.Generator;
import pie.ilikepiefoo2.clickminer.clickergame.upgrades.ConsumeResourceTask;
import pie.ilikepiefoo2.clickminer.clickergame.upgrades.Upgrade;
import pie.ilikepiefoo2.clickminer.util.BigNumber;
import pie.ilikepiefoo2.clickminer.util.Resource;
import pie.ilikepiefoo2.clickminer.util.WeightedRandom;

import static pie.ilikepiefoo2.clickminer.clickergame.generators.GeneratorType.CLICK;

public class ExplorationBlock extends GeneratorBlock {
    private static final Logger LOGGER = LogManager.getLogger();

    public ExplorationBlock()
    {
        super(new Generator(
                "beginner",
                new WeightedRandom<Resource>()
                .add(99,Items.AIR)
                .add(1,Items.OAK_SAPLING)
                ,
                new BigNumber(1),
                CLICK)
        );
        addUpgrade(new Upgrade("cobblestone") {
            @Override
            public void applyReward(ClickerGame game)
            {
                game.getGenerator("beginner").getValue()
                        .getResourceWeightedRandom()
                        .add(1,Items.COBBLESTONE);
                LOGGER.debug(game.getOwner()+" has redeemed the reward for the "+this.getName()+" upgrade.");
            }
        }.addRequirement(
                new ConsumeResourceTask(
                    new Resource(Items.OAK_SAPLING),new BigNumber(1)
                )
            )
        );
        addUpgrade(new Upgrade("coal") {
                @Override
                public void applyReward(ClickerGame game)
                {
                    game.getGenerator("beginner").getValue()
                            .getResourceWeightedRandom()
                            .add(1,Items.COAL_ORE);
                    LOGGER.debug(game.getOwner()+" has redeemed the reward for the "+this.getName()+" upgrade.");
                }
            }.addRequirement(
                new ConsumeResourceTask(
                    new Resource(Items.COBBLESTONE),new BigNumber(1)
                )
            )
        );
        addUpgrade(new Upgrade("coal") {
                @Override
                public void applyReward(ClickerGame game)
                {
                    game.getGenerator("beginner").getValue()
                            .getResourceWeightedRandom()
                            .add(1,Items.COAL_ORE);
                    LOGGER.debug(game.getOwner()+" has redeemed the reward for the "+this.getName()+" upgrade.");
                }
            }.addRequirement(
                new ConsumeResourceTask(
                        new Resource(Items.COAL),new BigNumber(1)
                )
            )
        );
    }
}
