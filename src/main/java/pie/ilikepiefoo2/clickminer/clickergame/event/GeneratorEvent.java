package pie.ilikepiefoo2.clickminer.clickergame.event;


import net.minecraftforge.eventbus.api.Cancelable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.clickergame.BigNumber;
import pie.ilikepiefoo2.clickminer.clickergame.Generator;

// TODO Documentation
public abstract class GeneratorEvent extends ClickerEvent {
    private static final Logger LOGGER = LogManager.getLogger();
    protected Generator generator;

    public GeneratorEvent(Generator generator)
    {
        super(generator.getGame());
        this.generator = generator;
    }

    public abstract Generator getGenerator();

    @Cancelable
    public static class BeforeGeneratorTick<GENERATOR_TYPE extends Generator> extends GeneratorEvent
    {
        public BigNumber tickCount;

        public BeforeGeneratorTick(GENERATOR_TYPE generator, BigNumber tickCount)
        {
            super(generator);
            this.tickCount = tickCount;
        }
        public BeforeGeneratorTick(GENERATOR_TYPE generator, double tickCount)
        {
            super(generator);
            this.tickCount = new BigNumber(tickCount);
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
        public GENERATOR_TYPE getGenerator()
        {
            return (GENERATOR_TYPE) this.generator;
        }
    }


    public static class AfterGeneratorTick<GENERATOR_TYPE extends Generator> extends GeneratorEvent
    {
        private BigNumber tickCount;
        private BigNumber amountGenerated;

        public AfterGeneratorTick(GENERATOR_TYPE generator, BigNumber tickCount, BigNumber amountGenerated)
        {
            super(generator);
            this.tickCount = tickCount;
            this.amountGenerated = amountGenerated;
        }
        public AfterGeneratorTick(GENERATOR_TYPE generator, double tickCount, BigNumber amountGenerated)
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
        public GENERATOR_TYPE getGenerator()
        {
            return (GENERATOR_TYPE) this.generator;
        }
    }
}