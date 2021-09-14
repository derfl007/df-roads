package derfl007.roads.common.blocks;

import java.util.List;

import com.google.common.collect.Lists;

import derfl007.roads.Reference;
import derfl007.roads.Roads;
import derfl007.roads.init.RoadBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRoadSidewalk extends Block {

    public BlockRoadSidewalk() {
        super(Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(SHAPE,
                BlockStairs.EnumShape.STRAIGHT));
        this.setRegistryName("road_sidewalk");
        this.setUnlocalizedName("road_sidewalk");
        this.setCreativeTab(Roads.ROADS_TAB);
        this.setHardness(1.5F);
    }

    private static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static final PropertyEnum<BlockStairs.EnumShape> SHAPE = PropertyEnum.create("shape",
            BlockStairs.EnumShape.class);

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
        state = this.getActualState(state, worldIn, pos);

        for (AxisAlignedBB axisalignedbb : getCollisionBoxList(state)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
        }
    }

    private static List<AxisAlignedBB> getCollisionBoxList(IBlockState bstate) {
        List<AxisAlignedBB> list = Lists.newArrayList();
        BlockStairs.EnumShape blocksidewalk$enumshape = bstate.getValue(SHAPE);

        list.add(FULL_BLOCK_AABB);

        if (blocksidewalk$enumshape == BlockStairs.EnumShape.STRAIGHT) {
            list.add(getStraightShape(bstate));
        } else if (blocksidewalk$enumshape == BlockStairs.EnumShape.OUTER_LEFT) {
            list.add(getOuterLeftShape(bstate));
        } else if (blocksidewalk$enumshape == BlockStairs.EnumShape.OUTER_RIGHT) {
            list.add(getOuterRightShape(bstate));
        } else if (blocksidewalk$enumshape == BlockStairs.EnumShape.INNER_LEFT) {
            list.add(getInnerLeftShape(bstate));
        } else if (blocksidewalk$enumshape == BlockStairs.EnumShape.INNER_RIGHT) {
            list.add(getInnerRightShape(bstate));
        }
        return list;
    }

    private static AxisAlignedBB getInnerRightShape(IBlockState bstate) {
        switch (bstate.getValue(FACING)) {
            case NORTH:
            default:
                return Reference.SC_INNER_NORTH_AABB;
            case SOUTH:
                return Reference.SC_INNER_SOUTH_AABB;
            case WEST:
                return Reference.SC_INNER_WEST_AABB;
            case EAST:
                return Reference.SC_INNER_EAST_AABB;
        }
    }

    private static AxisAlignedBB getInnerLeftShape(IBlockState bstate) {
        switch (bstate.getValue(FACING)) {
            case NORTH:
            default:
                return Reference.SC_INNER_WEST_AABB;
            case SOUTH:
                return Reference.SC_INNER_EAST_AABB;
            case WEST:
                return Reference.SC_INNER_SOUTH_AABB;
            case EAST:
                return Reference.SC_INNER_NORTH_AABB;
        }
    }

    private static AxisAlignedBB getOuterRightShape(IBlockState bstate) {
        switch (bstate.getValue(FACING)) {
            case NORTH:
            default:
                return Reference.SC_STRAIGHT_EAST_AABB;
            case SOUTH:
                return Reference.SC_STRAIGHT_WEST_AABB;
            case WEST:
                return Reference.SC_STRAIGHT_NORTH_AABB;
            case EAST:
                return Reference.SC_STRAIGHT_SOUTH_AABB;
        }
    }

    private static AxisAlignedBB getOuterLeftShape(IBlockState bstate) {
        switch (bstate.getValue(FACING)) {
            case NORTH:
            default:
                return Reference.SC_STRAIGHT_WEST_AABB;
            case SOUTH:
                return Reference.SC_STRAIGHT_EAST_AABB;
            case WEST:
                return Reference.SC_STRAIGHT_SOUTH_AABB;
            case EAST:
                return Reference.SC_STRAIGHT_NORTH_AABB;
        }
    }

    private static AxisAlignedBB getStraightShape(IBlockState bstate) {
        switch (bstate.getValue(FACING)) {
            case NORTH:
            default:
                return Reference.SC_STRAIGHT_NORTH_AABB;
            case SOUTH:
                return Reference.SC_STRAIGHT_SOUTH_AABB;
            case WEST:
                return Reference.SC_STRAIGHT_EAST_AABB;
            case EAST:
                return Reference.SC_STRAIGHT_WEST_AABB;
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        state = state.withProperty(FACING, placer.getHorizontalFacing()).withProperty(SHAPE,
                BlockStairs.EnumShape.STRAIGHT);
        return state;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
        return iblockstate;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;

        i = i | state.getValue(FACING).getIndex();
        return i;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(SHAPE, getStairsShape(state, worldIn, pos));
    }

    private static BlockStairs.EnumShape getStairsShape(IBlockState p_185706_0_, IBlockAccess p_185706_1_,
                                                        BlockPos p_185706_2_) {
        EnumFacing enumfacing = p_185706_0_.getValue(FACING);
        IBlockState iblockstate = p_185706_1_.getBlockState(p_185706_2_.offset(enumfacing));

        if (iblockstate.getBlock() == RoadBlocks.road_sidewalk) {
            EnumFacing enumfacing1 = iblockstate.getValue(FACING);

            if (enumfacing1.getAxis() != p_185706_0_.getValue(FACING).getAxis()) {
                if (enumfacing1 == enumfacing.rotateYCCW()) {
                    return BlockStairs.EnumShape.OUTER_LEFT;
                }

                return BlockStairs.EnumShape.OUTER_RIGHT;
            }
        }

        IBlockState iblockstate1 = p_185706_1_.getBlockState(p_185706_2_.offset(enumfacing.getOpposite()));

        if (iblockstate1.getBlock() == RoadBlocks.road_sidewalk) {
            EnumFacing enumfacing2 = iblockstate1.getValue(FACING);

            if (enumfacing2.getAxis() != p_185706_0_.getValue(FACING).getAxis()) {
                if (enumfacing2 == enumfacing.rotateYCCW()) {
                    return BlockStairs.EnumShape.INNER_LEFT;
                }

                return BlockStairs.EnumShape.INNER_RIGHT;
            }
        }

        return BlockStairs.EnumShape.STRAIGHT;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    @SuppressWarnings({ "incomplete-switch", "deprecation" })
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        EnumFacing enumfacing = state.getValue(FACING);
        BlockStairs.EnumShape blockstairs$enumshape = state.getValue(SHAPE);

        switch (mirrorIn) {
            case LEFT_RIGHT:

                if (enumfacing.getAxis() == EnumFacing.Axis.Z) {
                    switch (blockstairs$enumshape) {
                        case OUTER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE,
                                    BlockStairs.EnumShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE,
                                    BlockStairs.EnumShape.OUTER_LEFT);
                        case INNER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE,
                                    BlockStairs.EnumShape.INNER_LEFT);
                        case INNER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE,
                                    BlockStairs.EnumShape.INNER_RIGHT);
                        default:
                            return state.withRotation(Rotation.CLOCKWISE_180);
                    }
                }

                break;
            case FRONT_BACK:

                if (enumfacing.getAxis() == EnumFacing.Axis.X) {
                    switch (blockstairs$enumshape) {
                        case OUTER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE,
                                    BlockStairs.EnumShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE,
                                    BlockStairs.EnumShape.OUTER_LEFT);
                        case INNER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE,
                                    BlockStairs.EnumShape.INNER_RIGHT);
                        case INNER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE,
                                    BlockStairs.EnumShape.INNER_LEFT);
                        case STRAIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180);
                    }
                }
        }

        return super.withMirror(state, mirrorIn);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, SHAPE);
    }
}