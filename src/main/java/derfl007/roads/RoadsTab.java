package derfl007.roads;

import derfl007.roads.init.RoadBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RoadsTab extends CreativeTabs {

    public RoadsTab() {
        super("tabRoads");

    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(RoadBlocks.road_line);
    }


}