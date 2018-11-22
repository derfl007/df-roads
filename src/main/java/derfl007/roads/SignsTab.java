package derfl007.roads;

import derfl007.roads.init.RoadBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class SignsTab extends CreativeTabs {

    public SignsTab() {
        super("tabSigns");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(RoadBlocks.road_sign_priority_2);
    }

}
