package derfl007.roads.common.blocks;

import java.util.Random;

import derfl007.roads.Reference;
import derfl007.roads.Roads;
import derfl007.roads.common.tileentities.TileEntityRoadLantern;
import derfl007.roads.init.RoadBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRoadLantern  extends Block {
    private final boolean isOn;

    public BlockRoadLantern(String name, boolean isOn) {
        super(Material.IRON);
        this.isOn = isOn;
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setHardness(1.2F);
        if(!isOn) setCreativeTab(Roads.ROADS_TAB);
        if(isOn) this.setLightLevel(1.0F);
    }

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return Reference.ROAD_BLOCK_AABB;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        state = state.withProperty(FACING, placer.getHorizontalFacing());
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
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    public void updateLight(World worldIn, BlockPos pos, IBlockState state) {
        if (worldIn.provider.hasSkyLight()) {
            int i = 15 - (worldIn.getLightFor(EnumSkyBlock.SKY, pos) - worldIn.getSkylightSubtracted());
            i = MathHelper.clamp(i, 0, 15);
            if (i >= 3) {
                worldIn.setBlockState(pos, RoadBlocks.road_lantern_lit.getDefaultState().withProperty(FACING, state.getValue(FACING)), 2);
            } else {
                worldIn.setBlockState(pos, RoadBlocks.road_lantern.getDefaultState().withProperty(FACING, state.getValue(FACING)), 2);
            }
        }
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityRoadLantern(this, state);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(RoadBlocks.road_lantern);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(RoadBlocks.road_lantern);
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(RoadBlocks.road_lantern);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
}