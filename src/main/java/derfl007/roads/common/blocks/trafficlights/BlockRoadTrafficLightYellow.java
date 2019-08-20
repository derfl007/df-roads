package derfl007.roads.common.blocks.trafficlights;

import java.util.HashMap;
import java.util.Map;

import derfl007.roads.init.RoadBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRoadTrafficLightYellow extends BlockRoadTrafficLightGen {

	private Map<BlockPos, Long> addedTimes = new HashMap<>();

	public BlockRoadTrafficLightYellow() {
		super("road_traffic_light_yellow_dyn");
	}

	@Override
	protected void updateState(boolean updated, World worldIn, BlockPos pos, IBlockState state) {
		if (!addedTimes.containsKey(pos)) {
			System.out.println("new:" + pos.toString());
			addedTimes.put(pos, worldIn.getTotalWorldTime());
		}

		if (addedTimes.get(pos) > 0 && worldIn.getTotalWorldTime() - addedTimes.get(pos) >= YELLOW_DURATION) {
			setBlockState(worldIn, pos, state, RoadBlocks.road_traffic_light_red_dyn);
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		if (!addedTimes.containsKey(pos) || worldIn.getTotalWorldTime() - addedTimes.get(pos) > YELLOW_DURATION) {
			addedTimes.put(pos, worldIn.getTotalWorldTime());
		}
	}

	@Override
	protected void blockToggled(World worldIn, BlockPos pos, IBlockState state) {
		setBlockState(worldIn, pos, state, RoadBlocks.road_traffic_light_yellow_blinking);
	}

}
