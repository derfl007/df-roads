package derfl007.roads.common.blocks.trafficlights.pedestriantrafficlights;

import java.util.Random;

import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightGen;
import derfl007.roads.init.RoadBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockRoadPedestrianTrafficLightGen extends BlockRoadTrafficLightGen {

	public BlockRoadPedestrianTrafficLightGen(String unlocalizedName) {
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
	protected void blockToggledInternal(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (worldIn.isRemote) {
			return;
		}
		if (!isPlaced(worldIn, pos)) {
			return;
		}
		if (player != null) {
			switch (getMode(state)) {
			case redstone_controlled:
				setMode(worldIn, pos, state, Mode.command_controlled, player);
				setBlockState(worldIn, pos, state, RoadBlocks.road_pedestrian_traffic_light_green,
						Mode.command_controlled);
				
				break;
			case command_controlled:
				setMode(worldIn, pos, state, Mode.deactivated, player);
				setBlockState(worldIn, pos, state, RoadBlocks.road_pedestrian_traffic_light_off, Mode.deactivated);
			
				break;
			case deactivated:
				setMode(worldIn, pos, state, Mode.redstone_controlled, player);
				setBlockState(worldIn, pos, state, RoadBlocks.road_pedestrian_traffic_light_green,
						Mode.redstone_controlled);
	
			}
		}
	}

}
