package derfl007.roads;

import derfl007.roads.init.RoadBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class YellowRoadsTab extends CreativeTabs {

    public YellowRoadsTab() {
        super("tabYellowRoads");
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(RoadBlocks.road_line_yellow);
    }
}
