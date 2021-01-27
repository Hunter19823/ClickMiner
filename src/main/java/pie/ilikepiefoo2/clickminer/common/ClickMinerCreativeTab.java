package pie.ilikepiefoo2.clickminer.common;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import pie.ilikepiefoo2.clickminer.ClickMiner;

public class ClickMinerCreativeTab extends ItemGroup {
    public static final ClickMinerCreativeTab INSTANCE = new ClickMinerCreativeTab();

    public ClickMinerCreativeTab(){
        super(ClickMiner.MOD_ID);
        setNoTitle();
    }


    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(Items.COOKIE);
    }


}