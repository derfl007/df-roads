package derfl007.roads.common.blocks;

import derfl007.roads.Roads;
import derfl007.roads.common.tileentities.TileEntitySignPrinter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockSignPrinter extends Block{

    public BlockSignPrinter() {
        super(Material.IRON);
        this.setRegistryName("sign_printer");
        this.setUnlocalizedName("sign_printer");
        this.setCreativeTab(Roads.ROADS_TAB);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntitySignPrinter();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote)
        {
            TileEntity tile_entity = worldIn.getTileEntity(pos);

            if (tile_entity instanceof TileEntitySignPrinter)
            {
                TileEntitySignPrinter signPrinter = (TileEntitySignPrinter) tile_entity;
                if (!signPrinter.getUsing())
                {
                    signPrinter.setUsing(true);
                    playerIn.openGui(Roads.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
                else
                {
                    playerIn.sendMessage(new TextComponentString("Someone is using this"));
                }
            }
        }
        return true;
    }
}
