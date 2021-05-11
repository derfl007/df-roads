package derfl007.roads.common.blocks.trafficlights.pedestriantrafficlights;

import derfl007.roads.Roads;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRoadPedestrianTrafficLight extends BlockRoadPedestrianTrafficLightBase {
		  
	public BlockRoadPedestrianTrafficLight(){
		super("road_pedestrian_traffic_light_manual");
	}

	@Override
	public CreativeTabs getCreativeTabToDisplayOn() {
		return Roads.SIGNS_TAB;
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		setLightsState(worldIn, pos, LightsState.GREEN);
	}

	@Override
	public LightsState getState() {
		// TODO Auto-generated method stub
		return LightsState.GREEN;  //for item rendering
	}

	@Override
	protected void redstoneSignalReceived(World worldIn, BlockPos pos, IBlockState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void redstoneSignalInterrupted(World worldIn, BlockPos pos, IBlockState state) {
		// TODO Auto-generated method stub
		
	}

}
