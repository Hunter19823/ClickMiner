package pie.ilikepiefoo2.clickminer.clickergame.event;


import net.minecraft.entity.Entity;
import net.minecraftforge.eventbus.api.Cancelable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;
import pie.ilikepiefoo2.clickminer.util.BigNumber;
import pie.ilikepiefoo2.clickminer.clickergame.generators.Generator;

// TODO Documentation
public abstract class GeneratorEvent<GENERATOR extends Generator> extends ClickerEvent {
    private static final Logger LOGGER = LogManager.getLogger();
    protected GENERATOR generator;
    protected Entity entity;

    public GeneratorEvent(GENERATOR generator, Entity entity)
    {
        super(generator.getGame(entity));
        this.entity = entity;
        this.generator = generator;
    }

    public abstract GENERATOR getGenerator();

    public Entity getEntity()
    {
        return entity;
    }

    public ClickerGame getGame()
    {
        return this.generator.getGame(this.entity);
    }

    @Cancelable
    public static class Tick extends GeneratorEvent
    {
        public BigNumber tickCount;

        public Tick(Generator generator, Entity entity, BigNumber tickCount)
        {
            super(generator, entity);
            this.tickCount = tickCount;
        }
        public Tick(Generator generator, Entity entity, double tickCount)
        {
            this(generator,entity,new BigNumber(tickCount));
        }

        public BigNumber getTickCount()
        {
            return tickCount;
        }
        public void setTickCount(BigNumber tickCount)
        {
            this.tickCount = tickCount;
        }

        @Override
        public Generator getGenerator()
        {
            return this.generator;
        }
    }

    /*
    public static class AfterGeneratorTick<GENERATOR extends Generator> extends GeneratorEvent
    {
        private BigNumber tickCount;
        private BigNumber amountGenerated;

        public AfterGeneratorTick(GENERATOR generator, BigNumber tickCount, BigNumber amountGenerated)
        {
            super(generator);
            this.tickCount = tickCount;
            this.amountGenerated = amountGenerated;
        }
        public AfterGeneratorTick(GENERATOR generator, double tickCount, BigNumber amountGenerated)
        {
            super(generator);
            this.tickCount = new BigNumber(tickCount);
            this.amountGenerated = amountGenerated;
        }

        public BigNumber getTickCount()
        {
            return tickCount;
        }

        public BigNumber getAmountGenerated()
        {
            return amountGenerated;
        }
        private void setAmountGenerated(BigNumber amountGenerated)
        {
            this.amountGenerated = amountGenerated;
        }

        @Override
        public GENERATOR getGenerator()
        {
            return (GENERATOR) this.generator;
        }
    }

     */
}