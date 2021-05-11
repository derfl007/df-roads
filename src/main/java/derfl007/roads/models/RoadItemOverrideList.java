package derfl007.roads.models;

import java.util.ArrayList;

import javax.annotation.Nullable;

import derfl007.roads.common.blocks.BlockRoad;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RoadItemOverrideList extends ItemOverrideList {

	public RoadItemOverrideList() {
		super(new ArrayList<ItemOverride>());
	}

	@Override
	public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world,
			@Nullable EntityLivingBase entity) {
		if (originalModel instanceof RoadBakedModel && !stack.isEmpty()) {
			Block block = Block.getBlockFromItem(stack.getItem());
			if (block != null && block instanceof BlockRoad) {
				return new RoadItemBakedModel((RoadBakedModel) originalModel, (BlockRoad) block);
			}
		}
		return originalModel;
	}
}
