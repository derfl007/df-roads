package derfl007.roads.network.message;

import derfl007.roads.common.tileentities.TileEntitySignPrinter;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSignPrinterSelect implements IMessage, IMessageHandler<MessageSignPrinterSelect, IMessage> {

    private int currentSign, currentTab, x, y, z;

    public MessageSignPrinterSelect() {
    }

    public MessageSignPrinterSelect(int currentSign, int currentTab, int x, int y, int z) {
        this.currentSign = currentSign;
        this.currentTab = currentTab;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.currentSign = buf.readInt();
        this.currentTab = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(currentSign);
        buf.writeInt(currentTab);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public IMessage onMessage(MessageSignPrinterSelect message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        World world = ctx.getServerHandler().player.world;

        TileEntity tile_entity = player.world.getTileEntity(new BlockPos(message.x, message.y, message.z));
        if (tile_entity instanceof TileEntitySignPrinter) {
            TileEntitySignPrinter tileEntitySignPrinter = (TileEntitySignPrinter) tile_entity;
            tileEntitySignPrinter.setCurrentTab(message.currentTab);
            tileEntitySignPrinter.setCurrentSign(message.currentSign);
        }
        BlockPos pos = new BlockPos(message.x, message.y, message.z);
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        return null;
    }
}