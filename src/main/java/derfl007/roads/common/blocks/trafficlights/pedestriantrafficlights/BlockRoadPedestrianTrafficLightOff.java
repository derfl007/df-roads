package derfl007.roads.common.blocks.trafficlights.pedestriantrafficlights;

import derfl007.roads.init.RoadBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRoadPedestrianTrafficLightOff extends BlockRoadPedestrianTrafficLightGen {

	public BlockRoadPedestrianTrafficLightOff() {
		super("road_pedestrian_traffic_light_off");
	}

	@Override
	protected void updateState(boolean updated, World worldIn, BlockPos pos, IBlockState state) {
		return;
	}
	
	@Override
	protected void blockToggled(World worldIn, BlockPos pos, IBlockState state) {
		setBlockState(worldIn, pos, state, RoadBlocks.road_pedestrian_traffic_light_green);
	}

}
