package pie.ilikepiefoo2.clickminer.clickergame.event;

import net.minecraftforge.eventbus.api.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.clickminer.util.BigNumber;
import pie.ilikepiefoo2.clickminer.clickergame.ClickerGame;
import pie.ilikepiefoo2.clickminer.util.Resource;

// TODO Documentation
public class ClickerEvent extends Event {
    private static final Logger LOGGER = LogManager.getLogger();
    private ClickerGame game;

    public ClickerEvent(ClickerGame game)
    {
        this.game = game;
    }

    public ClickerGame getGame()
    {
        return game;
    }


    public static class MoneyChanged extends ClickerEvent{
        private BigNumber previousBalance, newBalance, difference;
        private Resource resource;

        public MoneyChanged(ClickerGame game, Resource resource, BigNumber previousBalance, BigNumber newBalance)
        {
            super(game);
            this.resource = resource;
            this.previousBalance = previousBalance;
            this.newBalance = newBalance;
            this.difference = newBalance.minus(previousBalance);
        }

        public BigNumber getPreviousBalance()
        {
            return previousBalance;
        }

        public void setPreviousBalance(BigNumber previousBalance)
        {
            this.previousBalance = previousBalance;
        }

        public BigNumber getNewBalance()
        {
            return newBalance;
        }

        public void setNewBalance(BigNumber newBalance)
        {
            this.newBalance = newBalance;
        }

        public BigNumber getDifference()
        {
            return difference;
        }

        public void setDifference(BigNumber difference)
        {
            this.difference = difference;
        }

        public Resource getResource()
        {
            return resource;
        }
    }


}
