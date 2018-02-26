package derfl007.roads.common.blocks;


import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import derfl007.roads.Roads;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRoadSlope extends BlockHorizontal {
    private static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static final PropertyEnum<BlockRoadSlope.EnumHalf> HALF = PropertyEnum.create("half", BlockRoadSlope.EnumHalf.class);
    private static final PropertyEnum<BlockRoadSlope.EnumShape> SHAPE = PropertyEnum.create("shape", BlockRoadSlope.EnumShape.class);
    /**
     * B: .. T: xx
     * B: .. T: xx
     */
    private static final AxisAlignedBB AABB_SLAB_TOP = new AxisAlignedBB(0.0D, 0.3125D, 0.0D, 1.0D, 0.8125D, 1.0D);
    /**
     * B: .. T: x.
     * B: .. T: x.
     */
    private static final AxisAlignedBB AABB_QTR_TOP_WEST = new AxisAlignedBB(0.0D, 0.3125D, 0.0D, 0.5D, 0.8125D, 1.0D);
    /**
     * B: .. T: .x
     * B: .. T: .x
     */
    private static final AxisAlignedBB AABB_QTR_TOP_EAST = new AxisAlignedBB(0.5D, 0.3125D, 0.0D, 1.0D, 0.8125D, 1.0D);
    /**
     * B: .. T: xx
     * B: .. T: ..
     */
    private static final AxisAlignedBB AABB_QTR_TOP_NORTH = new AxisAlignedBB(0.0D, 0.3125D, 0.0D, 1.0D, 0.8125D, 0.5D);
    /**
     * B: .. T: ..
     * B: .. T: xx
     */
    private static final AxisAlignedBB AABB_QTR_TOP_SOUTH = new AxisAlignedBB(0.0D, 0.3125D, 0.5D, 1.0D, 0.8125D, 1.0D);
    /**
     * B: .. T: x.
     * B: .. T: ..
     */
    private static final AxisAlignedBB AABB_OCT_TOP_NW = new AxisAlignedBB(0.0D, 0.3125D, 0.0D, 0.5D, 0.8125D, 0.5D);
    /**
     * B: .. T: .x
     * B: .. T: ..
     */
    private static final AxisAlignedBB AABB_OCT_TOP_NE = new AxisAlignedBB(0.5D, 0.3125D, 0.0D, 1.0D, 0.8125D, 0.5D);
    /**
     * B: .. T: ..
     * B: .. T: x.
     */
    private static final AxisAlignedBB AABB_OCT_TOP_SW = new AxisAlignedBB(0.0D, 0.3125D, 0.5D, 0.5D, 0.8125D, 1.0D);
    /**
     * B: .. T: ..
     * B: .. T: .x
     */
    private static final AxisAlignedBB AABB_OCT_TOP_SE = new AxisAlignedBB(0.5D, 0.3125D, 0.5D, 1.0D, 0.8125D, 1.0D);
    /**
     * B: xx T: ..
     * B: xx T: ..
     */
    private static final AxisAlignedBB AABB_SLAB_BOTTOM = new AxisAlignedBB(0.0D, -0.1875D, 0.0D, 1.0D, 0.3125D, 1.0D);
    /**
     * B: x. T: ..
     * B: x. T: ..
     */
    private static final AxisAlignedBB AABB_QTR_BOT_WEST = new AxisAlignedBB(0.0D, -0.1875D, 0.0D, 0.5D, 0.3125D, 1.0D);
    /**
     * B: .x T: ..
     * B: .x T: ..
     */
    private static final AxisAlignedBB AABB_QTR_BOT_EAST = new AxisAlignedBB(0.5D, -0.1875D, 0.0D, 1.0D, 0.3125D, 1.0D);
    /**
     * B: xx T: ..
     * B: .. T: ..
     */
    private static final AxisAlignedBB AABB_QTR_BOT_NORTH = new AxisAlignedBB(0.0D, -0.1875D, 0.0D, 1.0D, 0.3125D, 0.5D);
    /**
     * B: .. T: ..
     * B: xx T: ..
     */
    private static final AxisAlignedBB AABB_QTR_BOT_SOUTH = new AxisAlignedBB(0.0D, -0.1875D, 0.5D, 1.0D, 0.3125D, 1.0D);
    /**
     * B: x. T: ..
     * B: .. T: ..
     */
    private static final AxisAlignedBB AABB_OCT_BOT_NW = new AxisAlignedBB(0.0D, -0.1875D, 0.0D, 0.5D, 0.3125D, 0.5D);
    /**
     * B: .x T: ..
     * B: .. T: ..
     */
    private static final AxisAlignedBB AABB_OCT_BOT_NE = new AxisAlignedBB(0.5D, -0.1875D, 0.0D, 1.0D, 0.3125D, 0.5D);
    /**
     * B: .. T: ..
     * B: x. T: ..
     */
    private static final AxisAlignedBB AABB_OCT_BOT_SW = new AxisAlignedBB(0.0D, -0.1875D, 0.5D, 0.5D, 0.3125D, 1.0D);
    /**
     * B: .. T: ..
     * B: .x T: ..
     */
    private static final AxisAlignedBB AABB_OCT_BOT_SE = new AxisAlignedBB(0.5D, -0.1875D, 0.5D, 1.0D, 0.3125D, 1.0D);

    public BlockRoadSlope(String name) {
        super(Material.ROCK);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(HALF, BlockRoadSlope.EnumHalf.BOTTOM).withProperty(SHAPE, BlockRoadSlope.EnumShape.STRAIGHT));
        this.setLightOpacity(255);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(Roads.ROADS_TAB);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        state = this.getActualState(state, worldIn, pos);

        for (AxisAlignedBB axisalignedbb : getCollisionBoxList(state))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
        }
    }

    private static List<AxisAlignedBB> getCollisionBoxList(IBlockState bstate)
    {
        List<AxisAlignedBB> list = Lists.newArrayList();
        boolean flag = bstate.getValue(HALF) == BlockRoadSlope.EnumHalf.TOP;
        list.add(flag ? AABB_SLAB_TOP : AABB_SLAB_BOTTOM);
        BlockRoadSlope.EnumShape blockstairs$enumshape = bstate.getValue(SHAPE);

        if (blockstairs$enumshape == BlockRoadSlope.EnumShape.STRAIGHT || blockstairs$enumshape == BlockRoadSlope.EnumShape.INNER_LEFT || blockstairs$enumshape == BlockRoadSlope.EnumShape.INNER_RIGHT)
        {
            list.add(getCollQuarterBlock(bstate));
        }

        if (blockstairs$enumshape != BlockRoadSlope.EnumShape.STRAIGHT)
        {
            list.add(getCollEighthBlock(bstate));
        }

        return list;
    }

    /**
     * Returns a bounding box representing a quarter of a block (two eight-size cubes back to back).
     * Used in all stair shapes except OUTER.
     */
    private static AxisAlignedBB getCollQuarterBlock(IBlockState bstate)
    {
        boolean flag = bstate.getValue(HALF) == BlockRoadSlope.EnumHalf.TOP;

        switch (bstate.getValue(FACING))
        {
            case NORTH:
            default:
                return flag ? AABB_QTR_BOT_NORTH : AABB_QTR_TOP_NORTH;
            case SOUTH:
                return flag ? AABB_QTR_BOT_SOUTH : AABB_QTR_TOP_SOUTH;
            case WEST:
                return flag ? AABB_QTR_BOT_WEST : AABB_QTR_TOP_WEST;
            case EAST:
                return flag ? AABB_QTR_BOT_EAST : AABB_QTR_TOP_EAST;
        }
    }

    /**
     * Returns a bounding box representing an eighth of a block (a block whose three dimensions are halved).
     * Used in all stair shapes except STRAIGHT (gets added alone in the case of OUTER; alone with a quarter block in
     * case of INSIDE).
     */
    private static AxisAlignedBB getCollEighthBlock(IBlockState bstate)
    {
        EnumFacing enumfacing = bstate.getValue(FACING);
        EnumFacing enumfacing1;

        switch (bstate.getValue(SHAPE))
        {
            case OUTER_LEFT:
            default:
                enumfacing1 = enumfacing;
                break;
            case OUTER_RIGHT:
                enumfacing1 = enumfacing.rotateY();
                break;
            case INNER_RIGHT:
                enumfacing1 = enumfacing.getOpposite();
                break;
            case INNER_LEFT:
                enumfacing1 = enumfacing.rotateYCCW();
        }

        boolean flag = bstate.getValue(HALF) == BlockRoadSlope.EnumHalf.TOP;

        switch (enumfacing1)
        {
            case NORTH:
            default:
                return flag ? AABB_OCT_BOT_NW : AABB_OCT_TOP_NW;
            case SOUTH:
                return flag ? AABB_OCT_BOT_SE : AABB_OCT_TOP_SE;
            case WEST:
                return flag ? AABB_OCT_BOT_SW : AABB_OCT_TOP_SW;
            case EAST:
                return flag ? AABB_OCT_BOT_NE : AABB_OCT_TOP_NE;
        }
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState iblockstate = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        iblockstate = iblockstate.withProperty(FACING, placer.getHorizontalFacing()).withProperty(SHAPE, BlockRoadSlope.EnumShape.STRAIGHT);
        return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double)hitY <= 0.5D) ? iblockstate.withProperty(HALF, BlockRoadSlope.EnumHalf.BOTTOM) : iblockstate.withProperty(HALF, BlockRoadSlope.EnumHalf.TOP);
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit.
     */
    @Nullable
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end)
    {
        List<RayTraceResult> list = Lists.newArrayList();

        for (AxisAlignedBB axisalignedbb : getCollisionBoxList(this.getActualState(blockState, worldIn, pos)))
        {
            list.add(this.rayTrace(pos, start, end, axisalignedbb));
        }

        RayTraceResult raytraceresult1 = null;
        double d1 = 0.0D;

        for (RayTraceResult raytraceresult : list)
        {
            if (raytraceresult != null)
            {
                double d0 = raytraceresult.hitVec.squareDistanceTo(end);

                if (d0 > d1)
                {
                    raytraceresult1 = raytraceresult;
                    d1 = d0;
                }
            }
        }

        return raytraceresult1;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState().withProperty(HALF, (meta & 4) > 0 ? BlockRoadSlope.EnumHalf.TOP : BlockRoadSlope.EnumHalf.BOTTOM);
        iblockstate = iblockstate.withProperty(FACING, EnumFacing.getHorizontal(5 - (meta & 3)));
        return iblockstate;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        if (state.getValue(HALF) == BlockRoadSlope.EnumHalf.TOP)
        {
            i |= 4;
        }

        i = i | 5 - state.getValue(FACING).getIndex();
        return i;
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state.withProperty(SHAPE, getStairsShape(state, worldIn, pos));
    }

    private static BlockRoadSlope.EnumShape getStairsShape(IBlockState p_185706_0_, IBlockAccess p_185706_1_, BlockPos p_185706_2_)
    {
        EnumFacing enumfacing = p_185706_0_.getValue(FACING);
        IBlockState iblockstate = p_185706_1_.getBlockState(p_185706_2_.offset(enumfacing));

        if (isBlockStairs(iblockstate) && p_185706_0_.getValue(HALF) == iblockstate.getValue(HALF))
        {
            EnumFacing enumfacing1 = iblockstate.getValue(FACING);

            if (enumfacing1.getAxis() != p_185706_0_.getValue(FACING).getAxis() && isDifferentStairs(p_185706_0_, p_185706_1_, p_185706_2_, enumfacing1.getOpposite()))
            {
                if (enumfacing1 == enumfacing.rotateYCCW())
                {
                    return BlockRoadSlope.EnumShape.OUTER_LEFT;
                }

                return BlockRoadSlope.EnumShape.OUTER_RIGHT;
            }
        }

        IBlockState iblockstate1 = p_185706_1_.getBlockState(p_185706_2_.offset(enumfacing.getOpposite()));

        if (isBlockStairs(iblockstate1) && p_185706_0_.getValue(HALF) == iblockstate1.getValue(HALF))
        {
            EnumFacing enumfacing2 = iblockstate1.getValue(FACING);

            if (enumfacing2.getAxis() != p_185706_0_.getValue(FACING).getAxis() && isDifferentStairs(p_185706_0_, p_185706_1_, p_185706_2_, enumfacing2))
            {
                if (enumfacing2 == enumfacing.rotateYCCW())
                {
                    return BlockRoadSlope.EnumShape.INNER_LEFT;
                }

                return BlockRoadSlope.EnumShape.INNER_RIGHT;
            }
        }

        return BlockRoadSlope.EnumShape.STRAIGHT;
    }

    private static boolean isDifferentStairs(IBlockState p_185704_0_, IBlockAccess p_185704_1_, BlockPos p_185704_2_, EnumFacing p_185704_3_)
    {
        IBlockState iblockstate = p_185704_1_.getBlockState(p_185704_2_.offset(p_185704_3_));
        return !isBlockStairs(iblockstate) || iblockstate.getValue(FACING) != p_185704_0_.getValue(FACING) || iblockstate.getValue(HALF) != p_185704_0_.getValue(HALF);
    }

    private static boolean isBlockStairs(IBlockState state)
    {
        return state.getBlock() instanceof BlockRoadSlope;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @SuppressWarnings("incomplete-switch")
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        EnumFacing enumfacing = state.getValue(FACING);
        BlockRoadSlope.EnumShape blockstairs$enumshape = state.getValue(SHAPE);

        switch (mirrorIn)
        {
            case LEFT_RIGHT:

                if (enumfacing.getAxis() == EnumFacing.Axis.Z)
                {
                    switch (blockstairs$enumshape)
                    {
                        case OUTER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, BlockRoadSlope.EnumShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, BlockRoadSlope.EnumShape.OUTER_LEFT);
                        case INNER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, BlockRoadSlope.EnumShape.INNER_LEFT);
                        case INNER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, BlockRoadSlope.EnumShape.INNER_RIGHT);
                        default:
                            return state.withRotation(Rotation.CLOCKWISE_180);
                    }
                }

                break;
            case FRONT_BACK:

                if (enumfacing.getAxis() == EnumFacing.Axis.X)
                {
                    switch (blockstairs$enumshape)
                    {
                        case OUTER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, BlockRoadSlope.EnumShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, BlockRoadSlope.EnumShape.OUTER_LEFT);
                        case INNER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, BlockRoadSlope.EnumShape.INNER_RIGHT);
                        case INNER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, BlockRoadSlope.EnumShape.INNER_LEFT);
                        case STRAIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180);
                    }
                }
        }

        return super.withMirror(state, mirrorIn);
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, HALF, SHAPE);
    }

    public enum EnumHalf implements IStringSerializable
    {
        TOP("top"),
        BOTTOM("bottom");

        private final String name;

        EnumHalf(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }
    }

    public enum EnumShape implements IStringSerializable
    {
        STRAIGHT("straight"),
        INNER_LEFT("inner_left"),
        INNER_RIGHT("inner_right"),
        OUTER_LEFT("outer_left"),
        OUTER_RIGHT("outer_right");

        private final String name;

        EnumShape(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }
    }
}