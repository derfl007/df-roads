package derfl007.roads.common.blocks.trafficlights;

import derfl007.roads.trafficlights.YellowLightsStartTimes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRoadTrafficLightGreen extends BlockRoadTrafficLightBase {

	public BlockRoadTrafficLightGreen() {
		super("road_traffic_light_green_dyn");
	}

	@Override
	protected void redstoneSignalReceived(World worldIn, BlockPos pos, IBlockState state) {	
		setLightsState(worldIn, pos, LightsState.YELLOW);
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
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		YellowLightsStartTimes addedTimes = YellowLightsStartTimes.get(worldIn);
		addedTimes.removeAddedTime(pos);
	}
}
