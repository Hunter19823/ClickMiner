package pie.ilikepiefoo2.clickminer.util;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class WeightedRandom<E extends Resource> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Random rand = new Random();
    private final NavigableMap<Double,E> map = new TreeMap<Double,E>();
    private double total = 0;

    public WeightedRandom<E> add(double weight, E element)
    {
        if (weight <= 0)
            return this;
        total += weight;
        map.put(total,element);
        return this;
    }

    public WeightedRandom<E> add(double weight, Item element)
    {
        return this.add(weight,(E) new Resource(element));
    }

    public E next()
    {
        double weight = rand.nextDouble() * total;
        return map.higherEntry(weight).getValue();
    }

    public double getTotal()
    {
        return total;
    }

    public CompoundNBT toNBT()
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putDouble("total",total);
        CompoundNBT items = new CompoundNBT();
        int i = 0;
        for(Double key : map.keySet())
        {
            CompoundNBT entry =  new CompoundNBT();
            entry.putDouble("weight",key);
            entry.put("resource",map.get(key).toNBT());
            items.put(i+"",entry);
            i++;
        }
        compoundNBT.put("items",items);
        return compoundNBT;
    }


    public static WeightedRandom<Resource> fromNBT(CompoundNBT compoundNBT)
    {
        WeightedRandom<Resource> resourceWeightedRandom = new WeightedRandom<Resource>();
        resourceWeightedRandom.total = compoundNBT.getDouble("total");
        CompoundNBT items = compoundNBT.getCompound("items");
        for(int i=0; i<items.size(); i++)
        {
            CompoundNBT entry = items.getCompound(i+"");
            resourceWeightedRandom.map.put(
                    entry.getDouble("weight"),
                    Resource.fromNBT(entry.getCompound("resource"))
            );
        }
        return resourceWeightedRandom;
    }

}
