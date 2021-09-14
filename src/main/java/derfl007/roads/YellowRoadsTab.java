package derfl007.roads;

import java.util.Comparator;

import derfl007.roads.init.RoadBlocks;
import derfl007.roads.init.RoadItems;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class YellowRoadsTab extends CreativeTabs implements Comparator<ItemStack> {

	public YellowRoadsTab() {
		super("tabYellowRoads");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(RoadBlocks.road_line_yellow[2]);
	}
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> items) {
		super.displayAllRelevantItems(items);
		items.sort(this);
	}
	
	@Override
	public int compare(ItemStack o1, ItemStack o2) {
		// TODO Auto-generated method stub
		Block b1 = Block.getBlockFromItem(o1.getItem());
		Block b2 = Block.getBlockFromItem(o2.getItem());

		int i1 = RoadBlocks.RegistrationHandler.BLOCKS.indexOf(b1);
		int i2 = RoadBlocks.RegistrationHandler.BLOCKS.indexOf(b2);

		return i1 - i2;

	}
}
