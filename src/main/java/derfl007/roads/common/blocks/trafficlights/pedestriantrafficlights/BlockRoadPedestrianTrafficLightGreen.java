package derfl007.roads.common.blocks.trafficlights.pedestriantrafficlights;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRoadPedestrianTrafficLightGreen extends BlockRoadPedestrianTrafficLightBase {

	public BlockRoadPedestrianTrafficLightGreen() {
		super("road_pedestrian_traffic_light_green");
	}

	@Override
	protected void redstoneSignalReceived(World worldIn, BlockPos pos, IBlockState state) {
			setLightsState(worldIn, pos, LightsState.RED);		
	}
	
	@Override
	public LightsState getState() {
		// TODO Auto-generated method stub
		return LightsState.GREEN;
	}

	@Override
	protected void redstoneSignalInterrupted(World worldIn, BlockPos pos, IBlockState state) {
		// TODO Auto-generated method stub
		
	}
	
}
