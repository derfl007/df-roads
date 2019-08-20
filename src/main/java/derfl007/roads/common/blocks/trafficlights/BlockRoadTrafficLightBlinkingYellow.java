package derfl007.roads.common.blocks.trafficlights;

import derfl007.roads.init.RoadBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRoadTrafficLightBlinkingYellow extends BlockRoadTrafficLightGen {

	public BlockRoadTrafficLightBlinkingYellow() {
		super("road_traffic_light_yellow_blinking");
	}

	@Override
	protected void updateState(boolean updated, World worldIn, BlockPos pos, IBlockState state) {
		return;
	}

	@Override
	protected void blockToggled(World worldIn, BlockPos pos, IBlockState state) {
		setBlockState(worldIn, pos, state, RoadBlocks.road_traffic_light_green_dyn);
	}

}
