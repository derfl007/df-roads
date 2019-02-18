package derfl007.roads.common.blocks;

import derfl007.roads.Reference;
import derfl007.roads.Roads;
import derfl007.roads.common.items.ItemWrench;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class BlockRoadRotatable extends BlockHorizontal {
    public BlockRoadRotatable(String name) {
        super(Material.ROCK);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(Roads.ROADS_TAB);
        this.setHardness(1.5F);
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            tooltip.add(I18n.format("block.road_rotatable.tooltip"));
        } else {
            tooltip.add(I18n.format("gui.tooltip.view_more"));
        }
    }

    private static final AxisAlignedBB EAST_0 = new AxisAlignedBB(0.0D, -0.2D,0.0D, 1.0D, -0.1D,1.0D);
    private static final AxisAlignedBB EAST_1 = new AxisAlignedBB(0.0D, -0.1D,0.0D, 0.9D, 0.0D, 1.0D);
    private static final AxisAlignedBB EAST_2 = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.8D, 0.1D, 1.0D);
    private static final AxisAlignedBB EAST_3 = new AxisAlignedBB(0.0D, 0.1D, 0.0D, 0.7D, 0.2D, 1.0D);
    private static final AxisAlignedBB EAST_4 = new AxisAlignedBB(0.0D, 0.2D, 0.0D, 0.6D, 0.3D, 1.0D);
    private static final AxisAlignedBB EAST_5 = new AxisAlignedBB(0.0D, 0.3D, 0.0D, 0.5D, 0.4D, 1.0D);
    private static final AxisAlignedBB EAST_6 = new AxisAlignedBB(0.0D, 0.4D, 0.0D, 0.4D, 0.5D, 1.0D);
    private static final AxisAlignedBB EAST_7 = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.3D, 0.6D, 1.0D);
    private static final AxisAlignedBB EAST_8 = new AxisAlignedBB(0.0D, 0.6D, 0.0D, 0.2D, 0.7D, 1.0D);
    private static final AxisAlignedBB EAST_9 = new AxisAlignedBB(0.0D, 0.7D, 0.0D, 0.1D, 0.8D, 1.0D);

    private static final AxisAlignedBB SOUTH_0 = new AxisAlignedBB(0.0D, -0.2D,0.0D, 1.0D, -0.1D,1.0D);
    private static final AxisAlignedBB SOUTH_1 = new AxisAlignedBB(0.0D, -0.1D,0.0D, 1.0D, 0.0D, 0.9D);
    private static final AxisAlignedBB SOUTH_2 = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1D, 0.8D);
    private static final AxisAlignedBB SOUTH_3 = new AxisAlignedBB(0.0D, 0.1D, 0.0D, 1.0D, 0.2D, 0.7D);
    private static final AxisAlignedBB SOUTH_4 = new AxisAlignedBB(0.0D, 0.2D, 0.0D, 1.0D, 0.3D, 0.6D);
    private static final AxisAlignedBB SOUTH_5 = new AxisAlignedBB(0.0D, 0.3D, 0.0D, 1.0D, 0.4D, 0.5D);
    private static final AxisAlignedBB SOUTH_6 = new AxisAlignedBB(0.0D, 0.4D, 0.0D, 1.0D, 0.5D, 0.4D);
    private static final AxisAlignedBB SOUTH_7 = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 0.6D, 0.3D);
    private static final AxisAlignedBB SOUTH_8 = new AxisAlignedBB(0.0D, 0.6D, 0.0D, 1.0D, 0.7D, 0.2D);
    private static final AxisAlignedBB SOUTH_9 = new AxisAlignedBB(0.0D, 0.7D, 0.0D, 1.0D, 0.8D, 0.1D);

    private static final AxisAlignedBB WEST_0 = new AxisAlignedBB(0.0D, -0.2D,0.0D, 1.0D, -0.1D, 1.0D);
    private static final AxisAlignedBB WEST_1 = new AxisAlignedBB(0.1D, -0.1D,0.0D, 1.0D, 0.0D, 1.0D);
    private static final AxisAlignedBB WEST_2 = new AxisAlignedBB(0.2D, 0.0D, 0.0D, 1.0D, 0.1D, 1.0D);
    private static final AxisAlignedBB WEST_3 = new AxisAlignedBB(0.3D, 0.1D, 0.0D, 1.0D, 0.2D, 1.0D);
    private static final AxisAlignedBB WEST_4 = new AxisAlignedBB(0.5D, 0.2D, 0.0D, 1.0D, 0.3D, 1.0D);
    private static final AxisAlignedBB WEST_5 = new AxisAlignedBB(0.5D, 0.3D, 0.0D, 1.0D, 0.4D, 1.0D);
    private static final AxisAlignedBB WEST_6 = new AxisAlignedBB(0.6D, 0.4D, 0.0D, 1.0D, 0.5D, 1.0D);
    private static final AxisAlignedBB WEST_7 = new AxisAlignedBB(0.7D, 0.5D, 0.0D, 1.0D, 0.6D, 1.0D);
    private static final AxisAlignedBB WEST_8 = new AxisAlignedBB(0.8D, 0.6D, 0.0D, 1.0D, 0.7D, 1.0D);
    private static final AxisAlignedBB WEST_9 = new AxisAlignedBB(0.9D, 0.7D, 0.0D, 1.0D, 0.8D, 1.0D);

    private static final AxisAlignedBB NORTH_0 = new AxisAlignedBB(0.0D, -0.2D,0.0D, 1.0D, -0.1D,1.0D);
    private static final AxisAlignedBB NORTH_1 = new AxisAlignedBB(0.0D, -0.1D,0.1D, 1.0D, 0.0D, 1.0D);
    private static final AxisAlignedBB NORTH_2 = new AxisAlignedBB(0.0D, 0.0D, 0.2D, 1.0D, 0.1D, 1.0D);
    private static final AxisAlignedBB NORTH_3 = new AxisAlignedBB(0.0D, 0.1D, 0.3D, 1.0D, 0.2D, 1.0D);
    private static final AxisAlignedBB NORTH_4 = new AxisAlignedBB(0.0D, 0.2D, 0.5D, 1.0D, 0.3D, 1.0D);
    private static final AxisAlignedBB NORTH_5 = new AxisAlignedBB(0.0D, 0.3D, 0.5D, 1.0D, 0.4D, 1.0D);
    private static final AxisAlignedBB NORTH_6 = new AxisAlignedBB(0.0D, 0.4D, 0.6D, 1.0D, 0.5D, 1.0D);
    private static final AxisAlignedBB NORTH_7 = new AxisAlignedBB(0.0D, 0.5D, 0.7D, 1.0D, 0.6D, 1.0D);
    private static final AxisAlignedBB NORTH_8 = new AxisAlignedBB(0.0D, 0.6D, 0.8D, 1.0D, 0.7D, 1.0D);
    private static final AxisAlignedBB NORTH_9 = new AxisAlignedBB(0.0D, 0.7D, 0.9D, 1.0D, 0.8D, 1.0D);


    private static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static final PropertyDirection SLOPE_FACING = PropertyDirection.create("slope_facing", EnumFacing.Plane.HORIZONTAL);
    private static final PropertyBool SLOPE = PropertyBool.create("slope");

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
        Boolean isSlope = shouldBeSlope(worldIn, pos, state);
        EnumFacing facing = state.getValue(FACING);
        if(isSlope) {
            switch(facing) {
                case WEST:
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_0);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_1);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_2);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_3);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_4);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_5);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_6);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_7);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_8);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_9);
                    break;
                case SOUTH:
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_0);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_1);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_2);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_3);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_4);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_5);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_6);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_7);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_8);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_9);
                    break;
                case NORTH:
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_0);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_1);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_2);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_3);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_4);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_5);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_6);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_7);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_8);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_9);
                    break;
                case EAST:
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_0);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_1);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_2);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_3);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_4);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_5);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_6);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_7);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_8);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_9);
                    break;
                default:
                    break;
            }
        } else {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, Reference.ROAD_BLOCK_AABB);
        }
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return Reference.ROAD_BLOCK_AABB;
    }

    private boolean shouldBeSlope(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
        Block block_under = worldIn.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock();
        return block_under instanceof BlockRoadRotatable || block_under instanceof BlockRoadLine;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        state = state.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
        return state;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if(meta < 4) {
            return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(0))
                    .withProperty(SLOPE_FACING, EnumFacing.getHorizontal(meta));
        } else if(meta < 8) {
            return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(1))
                    .withProperty(SLOPE_FACING, EnumFacing.getHorizontal(meta-4));
        } else if(meta < 12) {
            return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(2))
                    .withProperty(SLOPE_FACING, EnumFacing.getHorizontal(meta-8));
        } else {
            return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(3))
                    .withProperty(SLOPE_FACING, EnumFacing.getHorizontal(meta-12));
        }
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int facing = state.getValue(FACING).getHorizontalIndex();
        int slopeFacing = state.getValue(SLOPE_FACING).getHorizontalIndex();
        if(facing == 0) {
            return slopeFacing;
        } else if (facing == 1) {
            return 4 + slopeFacing;
        } else if (facing == 2) {
            return 8 + slopeFacing;
        } else if (facing == 3) {
            return 12 + slopeFacing;
        } else {
            return 0;
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

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, SLOPE, SLOPE_FACING);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(SLOPE, shouldBeSlope(worldIn, pos, state));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote) {
            return true;
        }
        if(playerIn.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemWrench) {
            EnumFacing slopeFacing = worldIn.getBlockState(pos).getValue(SLOPE_FACING);
            if(slopeFacing == EnumFacing.NORTH) {
                worldIn.setBlockState(pos, state.withProperty(SLOPE_FACING, EnumFacing.EAST).withProperty(SLOPE, shouldBeSlope(worldIn, pos, state)));
            } else if(slopeFacing == EnumFacing.EAST) {
                worldIn.setBlockState(pos, state.withProperty(SLOPE_FACING, EnumFacing.SOUTH).withProperty(SLOPE, shouldBeSlope(worldIn, pos, state)));
            } else if(slopeFacing == EnumFacing.SOUTH) {
                worldIn.setBlockState(pos, state.withProperty(SLOPE_FACING, EnumFacing.WEST).withProperty(SLOPE, shouldBeSlope(worldIn, pos, state)));
            } else if(slopeFacing == EnumFacing.WEST) {
                worldIn.setBlockState(pos, state.withProperty(SLOPE_FACING, EnumFacing.NORTH).withProperty(SLOPE, shouldBeSlope(worldIn, pos, state)));
            } else {
                worldIn.setBlockState(pos, state.withProperty(SLOPE_FACING, EnumFacing.NORTH).withProperty(SLOPE, shouldBeSlope(worldIn, pos, state)));
            }
            System.out.println("texture rotated");
            return true;
        }
        return false;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }
}