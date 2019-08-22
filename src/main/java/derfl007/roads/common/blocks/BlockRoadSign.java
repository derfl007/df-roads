package derfl007.roads.common.blocks;

import derfl007.roads.Roads;
import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightGen;
import derfl007.roads.init.RoadBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRoadSign extends Block {
	public BlockRoadSign(String name) {
		super(Material.IRON);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Roads.SIGNS_TAB);
		this.setHardness(1.4F);
	}

	protected static final PropertyDirection FACING = BlockHorizontal.FACING;
	protected static final PropertyBool UP = PropertyBool.create("up");
	protected static final PropertyBool DOWN = PropertyBool.create("down");
	protected static final PropertyBool NORTH = PropertyBool.create("north");
	protected static final PropertyBool SOUTH = PropertyBool.create("south");
	protected static final PropertyBool EAST = PropertyBool.create("east");
	protected static final PropertyBool WEST = PropertyBool.create("west");

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
		state = state.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
		return state;
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
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		getActualState(state, worldIn, pos);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex();
	}

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

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	private boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();
		String blockname = block.getRegistryName().toString();
		try {
			return block instanceof BlockRoadSign || block == RoadBlocks.road_traffic_light
					|| block == RoadBlocks.road_traffic_light_manual || block == RoadBlocks.road_traffic_light_green
					|| block == RoadBlocks.road_traffic_light_yellow_fixed || block == RoadBlocks.road_traffic_light_red
					|| block instanceof BlockRoadTrafficLightGen || block == RoadBlocks.road_pedestrian_traffic_light
					|| block == RoadBlocks.road_pedestrian_traffic_light_manual;
		} catch (StringIndexOutOfBoundsException e) {
			return block == RoadBlocks.road_traffic_light || block == RoadBlocks.road_pedestrian_traffic_light;
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, UP, DOWN, NORTH, SOUTH, EAST, WEST);
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
}