package derfl007.roads.common.blocks;

import derfl007.roads.Roads;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockRoadSignPost extends BlockRedstoneTransmitter {
	private static final PropertyBool UP = PropertyBool.create("up");
	private static final PropertyBool DOWN = PropertyBool.create("down");
	private static final PropertyBool NORTH = PropertyBool.create("north");
	private static final PropertyBool EAST = PropertyBool.create("east");
	private static final PropertyBool SOUTH = PropertyBool.create("south");
	private static final PropertyBool WEST = PropertyBool.create("west");

	public BlockRoadSignPost() {
		super(Material.IRON);
		this.setRegistryName("road_sign_post");
		this.setUnlocalizedName("road_sign_post");
		this.setDefaultState(this.blockState.getBaseState().withProperty(UP, false).withProperty(DOWN, false)
				.withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false)
				.withProperty(WEST, false).withProperty(POWER, 0));
		this.setCreativeTab(Roads.ROADS_TAB);
		this.setHardness(1.0F);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	private boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();
		return block != Blocks.AIR;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies
	 * properties not visible in the metadata, such as fence connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return super.getActualState(state, worldIn, pos).withProperty(UP, this.canConnectTo(worldIn, pos.up()))
				.withProperty(DOWN, this.canConnectTo(worldIn, pos.down()))
				.withProperty(NORTH, this.canConnectTo(worldIn, pos.north()))
				.withProperty(EAST, this.canConnectTo(worldIn, pos.east()))
				.withProperty(SOUTH, this.canConnectTo(worldIn, pos.south()))
				.withProperty(WEST, this.canConnectTo(worldIn, pos.west()));
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 */
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		switch (rot) {
		case CLOCKWISE_180:
			return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST))
					.withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST));
		case COUNTERCLOCKWISE_90:
			return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH))
					.withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH));
		case CLOCKWISE_90:
			return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH))
					.withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH));
		default:
			return state;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		switch (mirrorIn) {
		case LEFT_RIGHT:
			return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
		case FRONT_BACK:
			return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
		default:
			return super.withMirror(state, mirrorIn);
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer.Builder(this).add(UP, DOWN, NORTH, SOUTH, EAST, WEST).add(POWER).build();
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

}