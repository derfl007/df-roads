package derfl007.roads.gui;

import derfl007.roads.common.tileentities.TileEntityRoadTownSign;
import derfl007.roads.common.tileentities.TileEntitySignPrinter;
import derfl007.roads.gui.containers.ContainerSignPrinter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if(te instanceof TileEntitySignPrinter) {
            return new ContainerSignPrinter(player.inventory, (TileEntitySignPrinter) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if (te instanceof TileEntityRoadTownSign ) {
            return new GuiRoadTownSign(x, y, z);
        }
        if(te instanceof TileEntitySignPrinter) {
            return new GuiSignPrinter(player.inventory, (TileEntitySignPrinter) te);
        }
        return null;
    }
}