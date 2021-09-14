package derfl007.roads.common.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import derfl007.roads.common.util.BlockStateUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockRedstoneTransmitter extends Block {

	private int optGetPower(IBlockState state) {
		return BlockStateUtil.optGetValue(state, POWER, -1);
	}

	protected static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);

	public BlockRedstoneTransmitter(Material materialIn) {
		super(materialIn);
		this.setDefaultState(this.blockState.getBaseState().withProperty(POWER, 0));
	}

	@Override
	public final boolean canProvidePower(IBlockState state) {
		return true;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return getWeakPower(blockState, blockAccess, pos, side);
	}

	@Override
	public final int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		int power = optGetPower(blockState);
		if (power <= 0) {
			return 0;
		}

		if (canProvidePowerTo(blockAccess, blockState, pos, side.getOpposite())) {
			return power;
		}

		return 0;
	}

	private int computePower(IBlockState state, World worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos).getBlock() != this) {
			return 0;
		}

		int power = worldIn.isBlockIndirectlyGettingPowered(pos);
		if (power < 2) {
			return 0;
		}

		return power - 2;
	}

	private void checkPowerChange(World worldIn, BlockPos pos, IBlockState state, boolean force) {
		if (worldIn.getBlockState(pos).getBlock() != this) {
			return;
		}

		int power = computePower(state, worldIn, pos);

		if (force || power != optGetPower(state)) {
			worldIn.setBlockState(pos, state.withProperty(POWER, power));

			worldIn.notifyNeighborsOfStateChange(pos, this, false);
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);

		if (worldIn.isRemote) {
			return;
		}
		checkPowerChange(worldIn, pos, state, true);
		
		worldIn.scheduleUpdate(pos, this, 20);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);

		if (worldIn.isRemote) {
			return;
		}

		checkPowerChange(worldIn, pos, state, true);

	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (worldIn.isRemote) {
			return;
		}
		checkPowerChange(worldIn, pos, state, false);
	}

	@Override
	public final boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos,
			@Nullable EnumFacing side) {
		return side != null;
	}

	private final boolean canProvidePowerTo(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing side) {
		return worldIn.getBlockState(pos.offset(side)).getBlock() instanceof BlockRedstoneTransmitter;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(POWER, Math.max(0, optGetPower(state)));
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		checkPowerChange(worldIn, pos, state, false);
		tickRate(worldIn);
		worldIn.scheduleUpdate(pos, this, 10);
		
	}
}
