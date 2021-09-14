package derfl007.roads.common.blocks.trafficlights.pedestriantrafficlights;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRoadPedestrianTrafficLightRed extends BlockRoadPedestrianTrafficLightBase {

	public BlockRoadPedestrianTrafficLightRed() {
		super("road_pedestrian_traffic_light_red");
	}

	@Override
	protected void redstoneSignalReceived(World worldIn, BlockPos pos, IBlockState state) {
		
	}

	@Override
	public LightsState getState() {
		// TODO Auto-generated method stub
		return LightsState.RED;
	}

	@Override
	protected void redstoneSignalInterrupted(World worldIn, BlockPos pos, IBlockState state) {
		// TODO Auto-generated method stub
		setLightsState(worldIn, pos, LightsState.GREEN);
	}

}
