package pie.ilikepiefoo2.clickminer.util;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class WeightedRandom<E extends Resource> {
    private final NavigableMap<Double,E> map = new TreeMap<Double,E>();
    private static final Random rand = new Random();
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

    public CompoundNBT toNBT()
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putDouble("total",total);
        for(int i=0; i<map.size(); i++)
        {
            CompoundNBT entry =  new CompoundNBT();
            for(Double key : map.keySet())
            {
                entry.putDouble("weight",key);
                entry.put("resource",map.get(key).toNBT());
            }
            compoundNBT.put(i+"",entry);
        }
        return compoundNBT;
    }


    public static WeightedRandom<Resource> fromNBT(CompoundNBT compoundNBT)
    {
        WeightedRandom<Resource> resourceWeightedRandom = new WeightedRandom<Resource>();
        resourceWeightedRandom.total = compoundNBT.getDouble("total");
        for(int i=0; i<compoundNBT.size()-1; i++)
        {
            CompoundNBT entry = compoundNBT.getCompound(i+"");
            resourceWeightedRandom.map.put(
                    entry.getDouble("weight"),
                    Resource.fromNBT(entry.getCompound("resource"))
            );
        }
        return resourceWeightedRandom;
    }
    public WeightedRandom<E> loadNBT(CompoundNBT compoundNBT)
    {
        total = compoundNBT.getDouble("total");
        for(int i=0; i<compoundNBT.size()-1; i++)
        {
            CompoundNBT entry = compoundNBT.getCompound(i+"");
            map.put(
                entry.getDouble("weight"),
                (E) E.fromNBT(entry.getCompound("resource"))
            );
        }
        return this;
    }

}
