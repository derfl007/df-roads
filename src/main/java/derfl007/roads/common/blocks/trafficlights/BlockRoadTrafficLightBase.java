package derfl007.roads.common.blocks.trafficlights;

import java.util.NoSuchElementException;
import java.util.Random;

import derfl007.roads.common.blocks.BlockRoadSignRotatable;
import derfl007.roads.common.blocks.ExtendedBlockHorizontal;
import derfl007.roads.common.blocks.IBlockConnectable;
import derfl007.roads.common.commands.light.CommandTrafficLightsLightAdd;
import derfl007.roads.common.items.ItemTrafficRemote;
import derfl007.roads.common.items.ItemWrench;
import derfl007.roads.common.util.BlockStateUtil;
import derfl007.roads.init.RoadBlocks;
import derfl007.roads.trafficlights.LightsSetList;
import derfl007.roads.trafficlights.PropertyMode;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;

public abstract class BlockRoadTrafficLightBase extends ExtendedBlockHorizontal implements IBlockConnectable {

	public enum LightsState {
		GREEN, YELLOW, DEACTIVATED, RED
	}

	public boolean isPedestrianLights() {
		return false;
	}

	public abstract LightsState getState();

	private static final PropertyBool POWERED = PropertyBool.create("powered");
	protected static final PropertyMode CONTROL_MODE = new PropertyMode("control_mode");

	public static final int DEFAULT_YELLOW_DURATION = 3;

	public enum TrafficLightsControlMode {
		deactivated(0), redstone_controlled(1), command_controlled(2); // Names have to break Java naming conventions
		// because uppercase letters are not allowed in block properties.

		public final int index;

		TrafficLightsControlMode(int index) {
			this.index = index;
		}

		static TrafficLightsControlMode fromIndex(int index) {
			for (TrafficLightsControlMode mode : TrafficLightsControlMode.values()) {
				if (mode.index == index) {
					return mode;
				}
			}
			throw new NoSuchElementException();
		}

		TrafficLightsControlMode next() {
			switch (this) {
			case redstone_controlled:
				return command_controlled;
			case command_controlled:
				return deactivated;
			default:
				return redstone_controlled;
			}
		}
	}

	public BlockRoadTrafficLightBase(String unlocalizedName) {
		super(unlocalizedName);
		this.setDefaultState(this.getDefaultState().withProperty(POWERED, false).withProperty(CONTROL_MODE,
				TrafficLightsControlMode.redstone_controlled));
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

		if (worldIn.isRemote) {
			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
		if (!playerIn.capabilities.allowEdit) {
			playerIn.sendMessage(new TextComponentTranslation("commands.generic.permission"));
			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
		ItemStack itemstack = playerIn.getHeldItem(hand);

		if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemWrench) {

			blockToggled(worldIn, pos, state, playerIn);

			return true;
		} else if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemTrafficRemote) {

			CommandTrafficLightsLightAdd.addLight(pos, playerIn, this, worldIn, state);
			return true;
		}

		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);

	}

	@Override
	public final IBlockState getStateFromMeta(int meta) {
		int facing = meta % 4;
		int modeIndex = (meta - facing) / 4;
		TrafficLightsControlMode mode = TrafficLightsControlMode.fromIndex(modeIndex);

		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4))
				.withProperty(CONTROL_MODE, mode);
	}

	@Override
	public final int getMetaFromState(IBlockState state) {
		return super.getMetaFromState(state) + getMode(state).index * 4;
	}

	/**
	 * Called after the block is set in the Chunk data, but before the Tile Entity
	 * is set
	 */
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);

		if (worldIn.isRemote) {
			return;
		}
		notifyRedstoneSignal(worldIn, pos, state, true);

		worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);

		if (!isPlaced(worldIn, pos)) {
			return;
		}
		if (worldIn.isRemote) {
			return;
		}
		if (getMode(state) != TrafficLightsControlMode.redstone_controlled) {
			return;
		}

		notifyRedstoneSignal(worldIn, pos, state, false);

		worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
	}

	/**
	 * Called when a neighboring block was changed and marks that this state should
	 * perform any checks during a neighbor change. Cases may include when redstone
	 * power is updated, cactus blocks popping off due to a neighboring solid block,
	 * etc.
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (worldIn.isRemote) {
			return;
		}
		notifyRedstoneSignal(worldIn, pos, state, false);
	}

	protected Block getTextureForLightsState(LightsState state) {
		switch (state) {
		case GREEN:
			return RoadBlocks.road_traffic_light_green;
		case YELLOW:
			return RoadBlocks.road_traffic_light_yellow;
		case RED:
			return RoadBlocks.road_traffic_light_red;
		case DEACTIVATED:
			return RoadBlocks.road_traffic_light_yellow_blinking;
		default:
			throw new IllegalArgumentException();
		}
	}

	public final void setLightsState(World worldIn, BlockPos pos, LightsState newState) {
		if (getState() == LightsState.DEACTIVATED) {
			return;
		}
		if (newState == LightsState.DEACTIVATED) {
			throw new UnsupportedOperationException("Must be deactivated with a wrench");
		}
		replaceBlockState(worldIn, pos, getTextureForLightsState(newState));
	}

	private void lightsActivated(World worldIn, BlockPos pos) {
		replaceBlockState(worldIn, pos, getTextureForLightsState(LightsState.GREEN));
	}
	
	private void lightsDeactivated(World worldIn, BlockPos pos) {
		replaceBlockState(worldIn, pos, getTextureForLightsState(LightsState.DEACTIVATED));
	}

	private void notifyRedstoneSignal(World worldIn, BlockPos pos, IBlockState state, boolean force) {
		if (worldIn.isRemote) {
			return;
		}
		if (!isPlaced(worldIn, pos)) {
			return;
		}
		boolean isPowered = isPowered(worldIn, pos);
		boolean wasPowered = getSavedPowered(state);

		setPowered(worldIn, pos, state, isPowered(worldIn, pos));

		if (!force && isPowered == wasPowered) {
			return;
		}

		if (getMode(state) != TrafficLightsControlMode.redstone_controlled) {
			return;
		}

		if (isPowered) {
			System.out.println("Notifying signal received");
			redstoneSignalReceived(worldIn, pos, state);
		} else {
			redstoneSignalInterrupted(worldIn, pos, state);
		}
	}

	protected abstract void redstoneSignalReceived(World worldIn, BlockPos pos, IBlockState state);

	protected abstract void redstoneSignalInterrupted(World worldIn, BlockPos pos, IBlockState state);

	private void modeChanged(World worldIn, BlockPos pos, TrafficLightsControlMode newMode, EntityPlayer player) {

		if (newMode == TrafficLightsControlMode.deactivated) {
			lightsDeactivated(worldIn, pos);
		} else {
			lightsActivated(worldIn, pos);
		}

		switch (newMode) {
		case redstone_controlled:
			player.sendMessage(new TextComponentTranslation("dfroads.trafficlight.mode.redstone_controlled"));
			break;
		case command_controlled:
			player.sendMessage(new TextComponentTranslation("dfroads.trafficlight.mode.command_controlled"));
			break;
		case deactivated:
			player.sendMessage(new TextComponentTranslation("dfroads.trafficlight.mode.deactivated"));
			break;
		}
		if (LightsSetList.get(worldIn).lightAlreadyUsed(pos)
				&& newMode != TrafficLightsControlMode.command_controlled) {
			player.sendMessage(new TextComponentTranslation("dfroads.trafficlight.mode.deprecated"));
		}

	}

	private void blockToggled(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (worldIn.isRemote) {
			return;
		}
		if (!isPlaced(worldIn, pos)) {
			return;
		}

		TrafficLightsControlMode newMode = getMode(state).next();
		state = state.withProperty(CONTROL_MODE, newMode);

		worldIn.setBlockState(pos, state, 2);

		modeChanged(worldIn, pos, newMode, player);

		worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));

	}

	protected void replaceBlockState(World worldIn, BlockPos pos, Block blockTexture) {
		if (this.equals(blockTexture)) {
			return;
		}
		IBlockState currentState = worldIn.getBlockState(pos);
		if (currentState.getBlock() instanceof BlockRoadTrafficLightBase) {
			worldIn.setBlockState(pos,
					blockTexture.getDefaultState().withProperty(FACING, currentState.getValue(FACING))
							.withProperty(CONTROL_MODE, currentState.getValue(CONTROL_MODE)),
					2);
		}
	}

	private void setPowered(World worldIn, BlockPos pos, IBlockState state, boolean powered) {
		if (powered == getSavedPowered(state)) {
			return;
		}
		worldIn.setBlockState(pos, state.withProperty(POWERED, powered), 2);
	}

	protected final boolean getSavedPowered(IBlockState state) {
		return state.getValue(POWERED);
	}

	public TrafficLightsControlMode getMode(IBlockState state) {
		return state.getValue(CONTROL_MODE);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer.Builder(this).add(FACING, CONTROL_MODE, POWERED, POWER)
				.add(UP, DOWN, NORTH, SOUTH, EAST, WEST).build();
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

	protected final boolean isPlaced(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getBlock().equals(this);
	}

	protected final boolean isPowered(World worldIn, BlockPos pos) {
		return isPlaced(worldIn, pos) && worldIn.isBlockPowered(pos);
	}

	@Override
	public final IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand)
				.withProperty(CONTROL_MODE, TrafficLightsControlMode.redstone_controlled);
	}

	private boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();
		String blockname = block.getRegistryName().toString();
		return block instanceof ExtendedBlockHorizontal || block instanceof BlockRoadSignRotatable;
	}

	@Override
	public boolean canConnectTo(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing side) {
		return canConnectTo(worldIn, pos.offset(side));
	}

	@Override
	public IExtendedBlockState getExtendedState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return BlockStateUtil.getExtendedState(this, state, worldIn, pos);
	}

}
