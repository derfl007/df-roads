package derfl007.roads.network.message;

import derfl007.roads.common.tileentities.TileEntityRoadTownSign;
import derfl007.roads.util.TileEntityUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRoadTownSign implements IMessage, IMessageHandler<MessageRoadTownSign, IMessage> {
    public int x, y, z;
    private String message;

    public MessageRoadTownSign() {}

    public MessageRoadTownSign(int x, int y, int z, String message) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.message = message;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        ByteBufUtils.writeUTF8String(buf, message);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.message = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public IMessage onMessage(MessageRoadTownSign message, MessageContext ctx) {
        World world = ctx.getServerHandler().player.world;
        BlockPos pos = new BlockPos(message.x, message.y, message.z);
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityRoadTownSign) {
            TileEntityRoadTownSign te = (TileEntityRoadTownSign) tileEntity;
            te.setMessage(message.message);
//            System.out.println("message received!");
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }
        return null;
    }
}