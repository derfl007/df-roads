package derfl007.roads.common.items;


import java.util.List;

import derfl007.roads.Roads;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemWrench extends Item {

    public ItemWrench() {
        setUnlocalizedName("item_wrench");
        setRegistryName("ItemWrench");
        setCreativeTab(Roads.ROADS_TAB);
    }

//    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
//    {
//        list.add("Use this to rotate the top texture of many road blocks");
//    }
}