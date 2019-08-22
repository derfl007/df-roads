package derfl007.roads.common.items;


import derfl007.roads.Roads;
import net.minecraft.item.Item;

public class ItemTrafficRemote extends Item {

    public ItemTrafficRemote() {
        setUnlocalizedName("item_traffic_remote");
        setRegistryName("item_traffic_remote");
        setCreativeTab(Roads.ROADS_TAB);       
    }
    


//    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
//    {
//        list.add("Use this to rotate the top texture of many road blocks");
//    }
}