package derfl007.roads.common.blocks.trafficlights.pedestriantrafficlights;

import java.util.Random;

import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightBase;
import derfl007.roads.init.RoadBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockRoadPedestrianTrafficLightBase extends BlockRoadTrafficLightBase {
	
	@Override
	public boolean isPedestrianLights() {
		return true;
	}

	public BlockRoadPedestrianTrafficLightBase(String unlocalizedName) {
		super(unlocalizedName);
		// setTickRandomly(true);
		// this.setCreativeTab(null);
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(RoadBlocks.road_pedestrian_traffic_light_manual);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(RoadBlocks.road_pedestrian_traffic_light_manual);
	}

	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state) {
		return new ItemStack(RoadBlocks.road_pedestrian_traffic_light_manual);
	}

	@Override
	protected Block getTextureForLightsState(LightsState state) {
		switch (state) {
		case GREEN:
			return RoadBlocks.road_pedestrian_traffic_light_green;
		case YELLOW:
		case RED:
			return RoadBlocks.road_pedestrian_traffic_light_red;
		case DEACTIVATED:
			return RoadBlocks.road_pedestrian_traffic_light_off;
		default:
			throw new IllegalArgumentException();
		}
	}
	
}
