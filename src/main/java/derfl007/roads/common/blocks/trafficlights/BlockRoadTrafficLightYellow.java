package derfl007.roads.common.blocks.trafficlights;

import java.util.Random;

import derfl007.roads.Reference;
import derfl007.roads.trafficlights.YellowLightsStartTimes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRoadTrafficLightYellow extends BlockRoadTrafficLightBase {

	public BlockRoadTrafficLightYellow() {
		super("road_traffic_light_yellow_dyn");
	}

	@Override
	protected void redstoneSignalReceived(World worldIn, BlockPos pos, IBlockState state) {

	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);

		if (!isPlaced(worldIn, pos)) {
			return;
		}

		if (getMode(state) == TrafficLightsControlMode.redstone_controlled) {
			YellowLightsStartTimes addedTimes = YellowLightsStartTimes.get(worldIn);

			if (worldIn.getTotalWorldTime() - addedTimes.getAddedTime(worldIn, pos) > DEFAULT_YELLOW_DURATION
					* Reference.TICK_RATE) {
				setLightsState(worldIn, pos, LightsState.RED);
			} else {
				worldIn.scheduleUpdate(pos, this, 4);
			}
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);

		if (getMode(state) == TrafficLightsControlMode.redstone_controlled) {
			YellowLightsStartTimes addedTimes = YellowLightsStartTimes.get(worldIn);

			if (worldIn.getTotalWorldTime() - addedTimes.getAddedTime(worldIn, pos) > DEFAULT_YELLOW_DURATION
					* Reference.TICK_RATE) {
				addedTimes.updateAddedTime(worldIn, pos);
			}
			worldIn.scheduleUpdate(pos, this, 4);
		}

	}

	@Override
	public LightsState getState() {
		// TODO Auto-generated method stub
		return LightsState.YELLOW;
	}

	@Override
	protected void redstoneSignalInterrupted(World worldIn, BlockPos pos, IBlockState state) {
		// TODO Auto-generated method stub
		setLightsState(worldIn, pos, LightsState.GREEN);
	}

}
