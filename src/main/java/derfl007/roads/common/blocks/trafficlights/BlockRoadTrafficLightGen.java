package derfl007.roads.common.blocks.trafficlights;

import java.util.Random;

import derfl007.roads.common.blocks.BlockRoadSign;
import derfl007.roads.common.items.ItemWrench;
import derfl007.roads.init.RoadBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockRoadTrafficLightGen extends BlockRoadSign {

	protected static final PropertyBool POWERED = PropertyBool.create("powered");

	protected static final int YELLOW_DURATION = 60; // 3 seconds

	public BlockRoadTrafficLightGen(String unlocalizedName) {
		super(unlocalizedName);
		this.setDefaultState(this.getDefaultState().withProperty(POWERED, false));
		// setTickRandomly(true);
		// this.setCreativeTab(null);
	}

	@Override
	public CreativeTabs getCreativeTabToDisplayOn() {
		return null;
	}

	/**
	 * Called when the block is right clicked by a player.
	 */
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = playerIn.getHeldItem(hand);

		if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemWrench) {

			blockToggledInternal(worldIn, pos, state);

			return true;
		} else {
			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
	}

	/**
	 * Called after the block is set in the Chunk data, but before the Tile Entity
	 * is set
	 */
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		setPowered(worldIn, pos, state, isPowered(worldIn, pos)); // to prevent immediate change if
		// lights are already powered before they are placed.
		// updateStateInternal(worldIn, pos, state);

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		worldIn.scheduleUpdate(new BlockPos(x, y, z), this, this.tickRate(worldIn));
	}

	/**
	 * Called when a neighboring block was changed and marks that this state should
	 * perform any checks during a neighbor change. Cases may include when redstone
	 * power is updated, cactus blocks popping off due to a neighboring solid block,
	 * etc.
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!getSavedPowered(state) && isPowered(worldIn, pos)) {
			updateStateInternal(true, worldIn, pos, state);
		} else {
			setPowered(worldIn, pos, state, isPowered(worldIn, pos));
			worldIn.scheduleUpdate(pos, this, 4);
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);

		if (!isPlaced(worldIn, pos)) {
			return;
		}
		boolean updated = !getSavedPowered(state) && isPowered(worldIn, pos);
		updateStateInternal(updated, worldIn, pos, state);

		setPowered(worldIn, pos, state, isPowered(worldIn, pos));

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		worldIn.scheduleUpdate(new BlockPos(x, y, z), this, this.tickRate(worldIn));
	}

	private void updateStateInternal(boolean updated, World worldIn, BlockPos pos, IBlockState state) {
		if (worldIn.isRemote) {
			return;
		}
		if (!isPlaced(worldIn, pos)) {
			return;
		}
		setPowered(worldIn, pos, state, isPowered(worldIn, pos));
		updateState(updated, worldIn, pos, state);
	}
	/*
	 * { if (worldIn.isRemote) { System.out.println("Returning"); return; }
	 * 
	 * if (activated) { System.out.println("State:" + this.state.name()); switch
	 * (this.state) { case Green: if (worldIn.isBlockPowered(pos)) {
	 * setBlockState(worldIn, pos, state, RoadBlocks.road_traffic_light_yellow);
	 * this.state = COLOR.Yellow; lastTransitionTime = worldIn.getTotalWorldTime();
	 * } break; case Yellow: System.out.println("Total:" +
	 * worldIn.getTotalWorldTime()); System.out.println("Last:" +
	 * lastTransitionTime); if (worldIn.isBlockPowered(pos) ||
	 * worldIn.getTotalWorldTime() - lastTransitionTime >= YELLOW_DURATION) {
	 * setBlockState(worldIn, pos, state, RoadBlocks.road_traffic_light_red);
	 * this.state = COLOR.Red; lastTransitionTime = 0; } break; case Red: if
	 * (worldIn.isBlockPowered(pos)) { setBlockState(worldIn, pos, state,
	 * RoadBlocks.road_traffic_light_green); this.state = COLOR.Green;
	 * lastTransitionTime = 0; } break; } } else { if (!force &&
	 * worldIn.getTotalWorldTime() - lastTransitionTime < BLINKING_INTERVAL) {
	 * return; } if (yellowOn) { setBlockState(worldIn, pos, state,
	 * RoadBlocks.road_traffic_light_off); lastTransitionTime =
	 * worldIn.getTotalWorldTime(); yellowOn = false; } else {
	 * setBlockState(worldIn, pos, state, RoadBlocks.road_traffic_light_yellow);
	 * lastTransitionTime = worldIn.getTotalWorldTime(); yellowOn = true; }
	 * 
	 * }
	 * 
	 * }
	 */

	protected abstract void updateState(boolean updated, World worldIn, BlockPos pos,
			IBlockState state);/*
								 * 
								 * protected abstract void setBlockState(World worldIn, BlockPos pos,
								 * IBlockState state, Block blockTexture);/* { worldIn.setBlockState(pos,
								 * blockTexture.getDefaultState().withProperty(FACING, state.getValue(FACING)),
								 * 2); }
								 * 
								 */

	private void blockToggledInternal(World worldIn, BlockPos pos, IBlockState state) {
		if (worldIn.isRemote) {
			return;
		}
		if (!isPlaced(worldIn, pos)) {
			return;
		}
		blockToggled(worldIn, pos, state);
	}

	protected abstract void blockToggled(World worldIn, BlockPos pos, IBlockState state);

	protected final void setBlockState(World worldIn, BlockPos pos, IBlockState state, Block blockTexture) {
		worldIn.setBlockState(pos, blockTexture.getDefaultState().withProperty(FACING, state.getValue(FACING)), 2);
	}

	protected final void setPowered(World worldIn, BlockPos pos, IBlockState state, boolean powered) {
		if (powered == getSavedPowered(state)) {
			return;
		}
		worldIn.setBlockState(pos, this.getActualState(state, worldIn, pos).withProperty(POWERED, powered), 2);
	}

	protected final boolean getSavedPowered(IBlockState state) {
		return state.getValue(POWERED);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, UP, DOWN, NORTH, SOUTH, EAST, WEST, POWERED);
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(RoadBlocks.road_traffic_light_manual);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(RoadBlocks.road_traffic_light_manual);
	}

	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state) {
		return new ItemStack(RoadBlocks.road_traffic_light_manual);
	}

	protected boolean isPlaced(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getBlock().equals(this);
	}

	protected boolean isPowered(World worldIn, BlockPos pos) {
		return isPlaced(worldIn, pos) && worldIn.isBlockPowered(pos);
	}

}
