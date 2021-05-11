package derfl007.roads.common.blocks;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import derfl007.roads.Roads;
import derfl007.roads.common.util.BlockStateUtil;
import derfl007.roads.common.util.SignOrientation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRoadSignRotatable extends BlockRedstoneTransmitter implements IBlockConnectable {

	private static final Set<String> REGISTERED_TEXTURES = new HashSet<>();

	public static Set<String> getRegisteredTextures() {
		return Collections.unmodifiableSet(REGISTERED_TEXTURES);
	}

	private BlockRoadSignRotatable(String name) {
		super(Material.IRON);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Roads.SIGNS_TAB);
		this.setHardness(1.4F);

	}

	public BlockRoadSignRotatable(String name, String backTexturePath) {
		this(name);

		this.backTexturePath = backTexturePath;
		REGISTERED_TEXTURES.add(backTexturePath);

		this.frontTexturePath = name;
		REGISTERED_TEXTURES.add(name);

	}

	public BlockRoadSignRotatable(String name, String frontTexturePath, String backTexturePath) {
		this(name);

		this.backTexturePath = backTexturePath;
		REGISTERED_TEXTURES.add(backTexturePath);

		this.frontTexturePath = frontTexturePath;
		REGISTERED_TEXTURES.add(frontTexturePath);
	}

	public static final String BACK_ROUND = "road_sign_back_round";
	public static final String BACK_SQUARE = "road_sign_back_square";
	public static final String BACK_TRIANGLE = "road_sign_back_triangle";
	public static final String BACK_INVERTED_TRIANGLE = "road_sign_priority_1_back";
	public static final String BACK_HEXAGONAL = "road_sign_priority_2_back";
	public static final String BACK_DIAMOND = "road_sign_priority_3_back";
	public static final String BACK_SMALL_HORIZONTAL_RECTANGLE = "road_sign_info_10_back";
	public static final String BACK_SMALL_VERTICAL_RECTANGLE = "road_sign_warn_6c_back";
	public static final String BACK_AVERAGE_VERTICAL_RECTANGLE = "road_sign_info_23_back";
	public static final String BACK_ARROW_RIGHT = "road_sign_info_16b_back";
	public static final String BACK_ARROW_LEFT = "road_sign_info_16a_back";
	public static final String BACK_HORIZONTAL_RECTANGLE = "road_sign_back_rect_2";
	public static final String BACK_VERTICAL_RECTANGLE = "road_sign_back_rect";
	public static final String BACK_SMALL_HORIZONTAL_TOP_RECTANGLE = "road_sign_meta_back";

	public static final PropertyInteger ORIENTATION = PropertyInteger.create("orientation", 0, 15);

	private String frontTexturePath;
	private String backTexturePath;

	public String getFrontTexturePath() {
		return this.frontTexturePath;
	}

	public String getBackTexturePath() {
		return this.backTexturePath;
	}

	@Override
	public final boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		return true;
	}

	@Override
	public final IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {

		IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
		state = state.withProperty(ORIENTATION, (int) (((540 + placer.rotationYaw % 360) % 360) / 22.5));

		return state;
	}

	public final IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ORIENTATION, SignOrientation.fromMetadata(meta));
	}

	public final int getMetaFromState(IBlockState state) {
		return SignOrientation.toMetadata(state.getValue(ORIENTATION));
	}

	public final IBlockState withRotation(IBlockState state, Rotation rot) {

		int orientation = state.getValue(ORIENTATION);

		switch (rot) {
		case CLOCKWISE_180:
			orientation = (orientation + 8) % 16;
			break;
		case CLOCKWISE_90:
			orientation = (orientation + 4) % 16;
			break;
		case COUNTERCLOCKWISE_90:
			orientation = (orientation + 12) % 16;
			break;
		default:
			break;
		}
		return state.withProperty(ORIENTATION, orientation);
	}

	public final IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withProperty(ORIENTATION, mirrorIn.mirrorRotation(state.getValue(ORIENTATION), 16));
	}

	@Override
	protected final BlockStateContainer createBlockState() {
		return new BlockStateContainer.Builder(this).add(ORIENTATION, POWER).add(UP, DOWN, NORTH, SOUTH, EAST, WEST)
				.build();
	}

	@Override
	public IExtendedBlockState getExtendedState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return BlockStateUtil.getExtendedState(this, state, worldIn, pos);
	}

	@Override
	public boolean canConnectTo(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing direction) {

		BlockPos target = pos.offset(direction);

		if (canConnectInDirection(worldIn, state, direction)) {

			IBlockState iblockstate = worldIn.getBlockState(target);
			Block block = iblockstate.getBlock();
			if (block instanceof BlockRoadSignRotatable) {
				return canConnectInDirection(worldIn, iblockstate, direction.getOpposite());
			}
			if (block instanceof ExtendedBlockHorizontal) {
				return iblockstate.getValue(ExtendedBlockHorizontal.FACING) != direction.getOpposite();
			}

			return state.isFullBlock();

		}
		return false;

	}

	private boolean canConnectInDirection(IBlockAccess worldIn, IBlockState currentState, EnumFacing direction) {
		int rotation = currentState.getValue(ORIENTATION);

		return !SignOrientation.intersects(rotation, direction);
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);

		System.out.println(state);

	}

	public final BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos,
			EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

}
