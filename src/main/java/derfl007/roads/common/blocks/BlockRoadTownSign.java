package derfl007.roads.common.blocks;

import derfl007.roads.Roads;
import derfl007.roads.common.tileentities.TileEntityRoadTownSign;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRoadTownSign extends Block {

    public BlockRoadTownSign(String name) {
        super(Material.IRON);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(Roads.SIGNS_TAB);
        this.setHardness(1.4F);
    }

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static final PropertyBool UP = PropertyBool.create("up");
    private static final PropertyBool DOWN = PropertyBool.create("down");
    private static final PropertyBool NORTH = PropertyBool.create("north");
    private static final PropertyBool SOUTH = PropertyBool.create("south");
    private static final PropertyBool EAST = PropertyBool.create("east");
    private static final PropertyBool WEST = PropertyBool.create("west");

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World worldIn, IBlockState state) {
        return new TileEntityRoadTownSign();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        state = state.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
        return state;
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
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (placer instanceof EntityPlayer) ((EntityPlayer) placer).openGui(Roads.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
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

    private boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
        return block.getClass() == RoadBlocks.road_sign_info_1a.getClass() || block == RoadBlocks.road_traffic_light
                || block == RoadBlocks.road_pedestrian_traffic_light || block == RoadBlocks.road_sign_post;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
}