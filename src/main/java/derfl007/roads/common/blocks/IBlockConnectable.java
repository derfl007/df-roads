package derfl007.roads.common.blocks;

import derfl007.roads.common.util.UnlistedPropertyBoolean;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IBlockConnectable {
	static final UnlistedPropertyBoolean UP = new UnlistedPropertyBoolean("up");
	static final UnlistedPropertyBoolean DOWN = new UnlistedPropertyBoolean("down");
	static final UnlistedPropertyBoolean NORTH = new UnlistedPropertyBoolean("north");
	static final UnlistedPropertyBoolean SOUTH = new UnlistedPropertyBoolean("south");
	static final UnlistedPropertyBoolean EAST = new UnlistedPropertyBoolean("east");
	static final UnlistedPropertyBoolean WEST = new UnlistedPropertyBoolean("west");

	boolean canConnectTo(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing direction);

}
