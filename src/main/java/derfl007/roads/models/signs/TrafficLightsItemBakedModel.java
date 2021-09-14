package derfl007.roads.models.signs;

import javax.annotation.Nullable;

import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraftforge.client.model.IModel;

public class TrafficLightsItemBakedModel extends TrafficLightsBakedModel {

	private final BlockRoadTrafficLightBase block;

	 TrafficLightsItemBakedModel(TrafficLightsBakedModel parentModel, BlockRoadTrafficLightBase block) {
		super(parentModel);

		this.block = block;
	}
	
	@Override
	protected final IModel retexture(IModel model) {
		return retexture(model, block);
	}

	@Deprecated()
	@Override
	public ItemOverrideList getOverrides() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
	@Nullable
	@Override
	protected IModel getModel(@Nullable IBlockState state) {
		// TODO Auto-generated method stub
		if(block.isPedestrianLights()) {
			return getModel(PEDESTRIAN_TRAFFIC_LIGHTS_INDEX);
		}else {
			return getModel(TRAFFIC_LIGHTS_INDEX);
		}
	}
}
