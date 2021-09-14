package derfl007.roads.models.signs;

import java.util.ArrayList;

import javax.annotation.Nullable;

import derfl007.roads.common.blocks.BlockRoadSignRotatable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RoadSignItemOverride extends ItemOverrideList {

	public RoadSignItemOverride() {
		super(new ArrayList<ItemOverride>());
	}

	@Override
	public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world,
			@Nullable EntityLivingBase entity) {
		if (originalModel instanceof RoadSignBakedModel && !stack.isEmpty()) {
			Block block = Block.getBlockFromItem(stack.getItem());
			if (block != null && block instanceof BlockRoadSignRotatable) {
				return new RoadSignItemBakedModel((RoadSignBakedModel) originalModel, (BlockRoadSignRotatable) block);
			}
		}
		return originalModel;
	}

}
