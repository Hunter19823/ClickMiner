package pie.ilikepiefoo2.clickminer.clickergame;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.clickergame.event.ClickerEvent;
import pie.ilikepiefoo2.clickminer.clickergame.generators.ClickGenerator;
import pie.ilikepiefoo2.clickminer.clickergame.generators.GeneratorType;

import java.util.ArrayList;
import java.util.HashMap;

// TODO Documentation
public class ClickerGame {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BigNumber money = new BigNumber(0,0);
    private final HashMap<GeneratorType,ArrayList<Generator>> generators = new HashMap<>();
    private final HashMap<BlockPos,Generator> generatorByPosition = new HashMap<>();

    /**
     * Returns an immutable copy of the clicker game's balance.
     *
     * @return money
     */
    public BigNumber getMoney()
    {
        return new BigNumber(money);
    }


    public void add(BigNumber number)
    {
        BigNumber previous = new BigNumber(this.money);
        this.money.add(number);
        if(!this.money.equals(previous))
            MinecraftForge.EVENT_BUS.post(new ClickerEvent.MoneyChanged(this,previous,new BigNumber(this.money)));
    }
    public boolean subtract(BigNumber number)
    {
        BigNumber previous = new BigNumber(this.money);
        if(this.money.subtract(number)!=null)
            if(!this.money.equals(previous))
                MinecraftForge.EVENT_BUS.post(new ClickerEvent.MoneyChanged(this,previous,new BigNumber(this.money)));

        return false;
    }
    public void multiply(BigNumber number)
    {
        BigNumber previous = new BigNumber(this.money);
        this.money.multiply(number);
        if(!this.money.equals(previous))
            MinecraftForge.EVENT_BUS.post(new ClickerEvent.MoneyChanged(this,previous,new BigNumber(this.money)));
    }
    public void divide(BigNumber number)
    {
        BigNumber previous = new BigNumber(this.money);
        this.money.divide(number);
        if(!this.money.equals(previous))
            MinecraftForge.EVENT_BUS.post(new ClickerEvent.MoneyChanged(this,previous,new BigNumber(this.money)));
    }

    void addGenerator(Generator generator)
    {
        GeneratorType type = generator.getGeneratorType();
        generators.computeIfAbsent(type, k -> new ArrayList<>());
        generators.get(type).add(generator);
    }

    public Generator anchor(BlockPos pos, Generator generator)
    {
        return generatorByPosition.put(pos,generator);
    }

    public Generator getGenerator(BlockPos pos)
    {
        return generatorByPosition.get(pos);
    }

    public static void main(String[] args)
    {
        ClickerGame testGame = new ClickerGame();
        ClickGenerator clickGenerator = new ClickGenerator(testGame,0.01);
        for(int i = 0; i<1000; i++) {
            int ticks = (int) (Math.random()*i);
            LOGGER.info("$" + testGame.getMoney().toString());
            LOGGER.info("Now activating all generators for "+ticks+" ticks.");
            /*
            for (GeneratorType type : GeneratorType.values())

                if(testGame.getGenerators(type) != null)
                    for (Generator generator : testGame.getGenerators(type)) {
                        BigNumber sim = generator.simulateTicks(ticks);
                        LOGGER.info("Simulated output of "+generator.toString()+" = "+sim);
                        testGame.add(generator.simulateTicks(ticks));
                    }

            */
        }
    }

}
