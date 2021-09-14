package derfl007.roads.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * @deprecated Should not be used for new blocks.
 *
 */
@Deprecated
public class BlockRoadSignLegacy extends ExtendedBlockHorizontal {

	public BlockRoadSignLegacy(String name) {
		super(name);
	}

	private static final PropertyBool UP = PropertyBool.create("up");
	private static final PropertyBool DOWN = PropertyBool.create("down");
	private static final PropertyBool NORTH = PropertyBool.create("north");
	private static final PropertyBool SOUTH = PropertyBool.create("south");
	private static final PropertyBool EAST = PropertyBool.create("east");
	private static final PropertyBool WEST = PropertyBool.create("west");


	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		int meta = state.getValue(FACING).getHorizontalIndex();
		switch (meta) {
		case 0:
			return state.withProperty(UP, this.canConnectTo(worldIn, pos.up()))
					.withProperty(DOWN, this.canConnectTo(worldIn, pos.down()))
					.withProperty(NORTH, this.canConnectTo(worldIn, pos.north()))
					.withProperty(SOUTH, this.canConnectTo(worldIn, pos.south()))
					.withProperty(EAST, this.canConnectTo(worldIn, pos.east()))
					.withProperty(WEST, this.canConnectTo(worldIn, pos.west()));
		case 1:
			return state.withProperty(UP, this.canConnectTo(worldIn, pos.up()))
					.withProperty(DOWN, this.canConnectTo(worldIn, pos.down()))
					.withProperty(NORTH, this.canConnectTo(worldIn, pos.east()))
					.withProperty(SOUTH, this.canConnectTo(worldIn, pos.west()))
					.withProperty(EAST, this.canConnectTo(worldIn, pos.south()))
					.withProperty(WEST, this.canConnectTo(worldIn, pos.north()));
		case 2:
			return state.withProperty(UP, this.canConnectTo(worldIn, pos.up()))
					.withProperty(DOWN, this.canConnectTo(worldIn, pos.down()))
					.withProperty(NORTH, this.canConnectTo(worldIn, pos.south()))
					.withProperty(SOUTH, this.canConnectTo(worldIn, pos.north()))
					.withProperty(EAST, this.canConnectTo(worldIn, pos.west()))
					.withProperty(WEST, this.canConnectTo(worldIn, pos.east()));
		case 3:
			return state.withProperty(UP, this.canConnectTo(worldIn, pos.up()))
					.withProperty(DOWN, this.canConnectTo(worldIn, pos.down()))
					.withProperty(NORTH, this.canConnectTo(worldIn, pos.west()))
					.withProperty(SOUTH, this.canConnectTo(worldIn, pos.east()))
					.withProperty(EAST, this.canConnectTo(worldIn, pos.north()))
					.withProperty(WEST, this.canConnectTo(worldIn, pos.south()));
		default:
			return state.withProperty(UP, this.canConnectTo(worldIn, pos.up()))
					.withProperty(DOWN, this.canConnectTo(worldIn, pos.down()))
					.withProperty(NORTH, this.canConnectTo(worldIn, pos.north()))
					.withProperty(SOUTH, this.canConnectTo(worldIn, pos.south()))
					.withProperty(EAST, this.canConnectTo(worldIn, pos.east()))
					.withProperty(WEST, this.canConnectTo(worldIn, pos.west()));
		}

	}


	private boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();
		String blockname = block.getRegistryName().toString();
		return block instanceof ExtendedBlockHorizontal || block instanceof BlockRoadSignRotatable;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, POWER, UP, DOWN, NORTH, SOUTH, EAST, WEST);

	}

}
