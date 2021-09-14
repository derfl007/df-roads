package derfl007.roads.models;

import derfl007.roads.Reference;
import derfl007.roads.common.blocks.BlockRoad;
import derfl007.roads.models.signs.RoadSignModel;
import derfl007.roads.models.signs.TrafficLightsModel;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class RoadSignModelLoader implements ICustomModelLoader {

	private final RoadSignModel signModel = new RoadSignModel();
	private final TrafficLightsModel trafficLightsModel = new TrafficLightsModel();
	private final RoadLineModel roadLineModel = new RoadLineModel();
	private final RoadModel roadModel = new RoadModel();

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		// TODO Auto-generated method stub
		if (Reference.MOD_ID.equals(modelLocation.getResourceDomain()) && modelLocation.getResourcePath() != null) {
			if (modelLocation.getResourcePath().startsWith("road_sign_")
					&& !"road_sign_post".equals(modelLocation.getResourcePath())) {
				return true;
			}
			if (modelLocation.getResourcePath().contains("traffic_light")
					&& !modelLocation.getResourcePath().contains("model")
					&& !modelLocation.getResourcePath().contains("auto")) {
				return true;
			}
			if (BlockRoad.getRegisteredBlocks().contains(modelLocation.getResourcePath())) {
				return true;
			}
			return modelLocation.getResourcePath().startsWith("road_line");
		}
		return false;

	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		// TODO Auto-generated method stub

		if (modelLocation.getResourcePath().contains("traffic_light")) {
			return trafficLightsModel;
		}
		if (BlockRoad.getRegisteredBlocks().contains(modelLocation.getResourcePath())) {
			return roadModel;
		}
		if (modelLocation.getResourcePath().startsWith("road_sign_")) {
			return signModel;
		}
		return roadLineModel;
	}

}
