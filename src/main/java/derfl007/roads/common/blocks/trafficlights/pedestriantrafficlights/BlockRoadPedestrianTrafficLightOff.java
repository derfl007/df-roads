package derfl007.roads.common.blocks.trafficlights.pedestriantrafficlights;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRoadPedestrianTrafficLightOff extends BlockRoadPedestrianTrafficLightBase {

	public BlockRoadPedestrianTrafficLightOff() {
		super("road_pedestrian_traffic_light_off");
		this.setDefaultState(this.getDefaultState().withProperty(CONTROL_MODE, TrafficLightsControlMode.deactivated));
	}

	@Override
	protected void redstoneSignalReceived(World worldIn, BlockPos pos, IBlockState state) {
	}

	@Override
	public LightsState getState() {
		// TODO Auto-generated method stub
		return LightsState.DEACTIVATED;
	}
	
	@Override
	public TrafficLightsControlMode getMode(IBlockState state) {
		return TrafficLightsControlMode.deactivated;
	}
	
	@Override
	protected void redstoneSignalInterrupted(World worldIn, BlockPos pos, IBlockState state) {
		// TODO Auto-generated method stub
		
	}
}
