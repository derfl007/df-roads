package derfl007.roads;

import com.google.common.collect.Ordering;

import derfl007.roads.init.RoadBlocks;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class SignsTab extends CreativeTabs {

	public SignsTab() {
		super("tabSigns");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(RoadBlocks.road_sign_priority_2);
	}

	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> items) {
		super.displayAllRelevantItems(items);
		items.sort(Ordering.explicit(RoadBlocks.RegistrationHandler.BLOCKS)
				.onResultOf((ItemStack item) -> Block.getBlockFromItem(item.getItem())));
	}
}