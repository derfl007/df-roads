package derfl007.roads.common.blocks.trafficlights.pedestriantrafficlights;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRoadPedestrianTrafficLightOff extends BlockRoadPedestrianTrafficLightGen {

	public BlockRoadPedestrianTrafficLightOff() {
		super("road_pedestrian_traffic_light_off");
		this.setDefaultState(this.getDefaultState().withProperty(CONTROL_MODE, Mode.deactivated));
	}

	@Override
	protected void updateState(boolean updated, World worldIn, BlockPos pos, IBlockState state) {
		return;
	}
	
	@Override
	protected List<Mode> getLegalModes() {
		return new ArrayList<Mode>(Arrays.asList(Mode.deactivated));
	}

	/**
	 * Called after the block is set in the Chunk data, but before the Tile Entity
	 * is set
	 */
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		setMode(worldIn, pos, state, Mode.deactivated);
	}
}
