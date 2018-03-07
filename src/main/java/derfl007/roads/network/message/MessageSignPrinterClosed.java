package derfl007.roads.network.message;

import derfl007.roads.common.tileentities.TileEntitySignPrinter;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSignPrinterClosed implements IMessage, IMessageHandler<MessageSignPrinterClosed, IMessage>
{

    private int x, y, z;

    public MessageSignPrinterClosed()
    {
    }

    public MessageSignPrinterClosed(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public IMessage onMessage(MessageSignPrinterClosed message, MessageContext ctx)
    {
        EntityPlayerMP player = ctx.getServerHandler().player;
        World world = ctx.getServerHandler().player.world;

        TileEntity tile_entity = player.world.getTileEntity(new BlockPos(message.x, message.y, message.z));
        if (tile_entity instanceof TileEntitySignPrinter)
        {
            TileEntitySignPrinter tileEntitySignPrinter = (TileEntitySignPrinter) tile_entity;

            if (!tileEntitySignPrinter.getStackInSlot(0).isEmpty())
            {
                player.entityDropItem(tileEntitySignPrinter.getStackInSlot(0), 1);
                tileEntitySignPrinter.setInventorySlotContents(0, ItemStack.EMPTY);
            }
            tileEntitySignPrinter.setUsing(false);
        }
        BlockPos pos = new BlockPos(message.x, message.y, message.z);
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        return null;
    }

}
