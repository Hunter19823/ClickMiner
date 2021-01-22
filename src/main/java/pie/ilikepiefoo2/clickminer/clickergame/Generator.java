package pie.ilikepiefoo2.clickminer.clickergame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.clickergame.BigNumber;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;
import pie.ilikepiefoo2.clickminer.clickergame.generators.GeneratorType;

// TODO Documentation
public abstract class Generator {
    private static final Logger LOGGER = LogManager.getLogger();
    private BigNumber generationPerTick;
    private ClickerGame game;
    private GeneratorType generatorType;
    private int lastCollected = 0;

    public Generator(ClickerGame game, BigNumber generationPerTick, GeneratorType generatorType)
    {
        this.game = game;
        this.generationPerTick = generationPerTick;
        this.generatorType = generatorType;
        this.game.addGenerator(this);
    }
    public Generator(ClickerGame game, double generationPerTick, GeneratorType generatorType)
    {
        this.game = game;
        this.generationPerTick = new BigNumber(generationPerTick);
        this.generatorType = generatorType;
        this.game.addGenerator(this);
    }

    public int getLastCollected()
    {
        return lastCollected;
    }

    public void setLastCollected(int lastCollected)
    {
        this.lastCollected = lastCollected;
    }

    protected abstract boolean tick(BigNumber number);
    public boolean tick(){
        return this.tick(new BigNumber(1));
    }
    public boolean tick(double tickCount){
        return this.tick(new BigNumber(tickCount));
    }

    public BigNumber simulateTicks(BigNumber ticks)
    {
        return generationPerTick.times(ticks);
    }
    public BigNumber simulateTicks(double ticks)
    {
        return simulateTicks(new BigNumber(ticks));
    }

    public BigNumber getGenerationPerTick()
    {
        return generationPerTick;
    }
    public void setGenerationPerTick(BigNumber generationPerTick)
    {
        this.generationPerTick = generationPerTick;
    }

    public ClickerGame getGame()
    {
        return game;
    }

    public void setGame(ClickerGame game)
    {
        this.game = game;
    }

    public GeneratorType getGeneratorType()
    {
        return generatorType;
    }

    // TODO Add setGeneratorType that won't break click generator's list of generators.
    public void setGeneratorType(GeneratorType generatorType)
    {
        this.generatorType = generatorType;
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName()+"{" +
                "generationPerTick=" + generationPerTick +
                '}';
    }
}
