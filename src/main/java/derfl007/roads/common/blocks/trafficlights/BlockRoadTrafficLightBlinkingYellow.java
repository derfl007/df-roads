package derfl007.roads.common.blocks.trafficlights;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRoadTrafficLightBlinkingYellow extends BlockRoadTrafficLightGen {

	public BlockRoadTrafficLightBlinkingYellow() {
		super("road_traffic_light_yellow_blinking");
		this.setDefaultState(this.getDefaultState().withProperty(CONTROL_MODE, Mode.deactivated));
	}

	@Override
	protected void updateState(boolean updated, World worldIn, BlockPos pos, IBlockState state) {
		return;
	}

	/**
	 * Called after the block is set in the Chunk data, but before the Tile Entity
	 * is set
	 */
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.setMode(worldIn, pos, state, Mode.deactivated);
	}

	@Override
	@Deprecated
	public void setMode(World worldIn, BlockPos pos, IBlockState state, Mode mode) {
		throw new IllegalStateException("Must be activated with wrench.");
	}

	@Override
	protected List<Mode> getLegalModes() {
		return new ArrayList<Mode>(Arrays.asList(Mode.deactivated));
	}

}
