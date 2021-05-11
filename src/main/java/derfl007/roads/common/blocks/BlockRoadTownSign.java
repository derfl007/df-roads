package derfl007.roads.common.blocks;

import derfl007.roads.Roads;
import derfl007.roads.common.tileentities.TileEntityRoadTownSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRoadTownSign extends BlockRoadSignRotatable {

    public BlockRoadTownSign(String name) {
        super(name, "road_town_sign_back");      
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World worldIn, IBlockState state) {
        return new TileEntityRoadTownSign();
    }
   

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (placer instanceof EntityPlayer) ((EntityPlayer) placer).openGui(Roads.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
        
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

}