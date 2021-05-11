package derfl007.roads.models.signs;

import java.util.ArrayList;

import javax.annotation.Nullable;

import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TrafficLightsItemOverride extends ItemOverrideList {
	
	public TrafficLightsItemOverride() {
		super(new ArrayList<ItemOverride>());
	}
	

	@Override
	public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world,
			@Nullable EntityLivingBase entity) {
		if (originalModel instanceof TrafficLightsBakedModel && !stack.isEmpty()) {
			Block block = Block.getBlockFromItem(stack.getItem());
			if(block != null && block instanceof BlockRoadTrafficLightBase) {
				return new TrafficLightsItemBakedModel((TrafficLightsBakedModel) originalModel, (BlockRoadTrafficLightBase) block);
			}
		}
		return originalModel;
	}

}
