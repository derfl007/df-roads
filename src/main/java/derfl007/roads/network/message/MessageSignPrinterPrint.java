package derfl007.roads.network.message;

import derfl007.roads.RecipesSign;
import derfl007.roads.common.tileentities.TileEntitySignPrinter;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSignPrinterPrint implements IMessage, IMessageHandler<MessageSignPrinterPrint, IMessage> {

    private int currentSign, currentTab, x, y, z;
    private boolean clear;

    public MessageSignPrinterPrint() {
    }

    public MessageSignPrinterPrint(int currentSign, int currentTab, int x, int y, int z, boolean clear) {
        this.currentSign = currentSign;
        this.currentTab = currentTab;
        this.x = x;
        this.y = y;
        this.z = z;
        this.clear = clear;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.currentSign = buf.readInt();
        this.currentTab = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.clear = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(currentSign);
        buf.writeInt(currentTab);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeBoolean(clear);
    }

    @Override
    public IMessage onMessage(MessageSignPrinterPrint message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        TileEntity tile_entity = player.world.getTileEntity(new BlockPos(message.x, message.y, message.z));

        if (tile_entity instanceof TileEntitySignPrinter) {

            TileEntitySignPrinter tileEntitySignPrinter = (TileEntitySignPrinter) tile_entity;
            ItemStack inputSlot = tileEntitySignPrinter.getStackInSlot(0);

            if(inputSlot == null) return null;

            int currentDamageM = tileEntitySignPrinter.getStackInSlot(1).getItemDamage();
            int currentDamageY = tileEntitySignPrinter.getStackInSlot(2).getItemDamage();
            int currentDamageC = tileEntitySignPrinter.getStackInSlot(3).getItemDamage();
            tileEntitySignPrinter.setStackDamage(1, currentDamageM + RecipesSign.getDamage("M", message.currentTab, message.currentSign));
            tileEntitySignPrinter.setStackDamage(2, currentDamageY + RecipesSign.getDamage("Y", message.currentTab, message.currentSign));
            tileEntitySignPrinter.setStackDamage(3, currentDamageC + RecipesSign.getDamage("C", message.currentTab, message.currentSign));

            if (message.clear) {
                tileEntitySignPrinter.clear();
            } else {
                tileEntitySignPrinter.decrStackSize(0, RecipesSign.getBaseItemCount(message.currentTab));
            }
            EntityItem entityItem = new EntityItem(player.world, player.posX, player.posY + 1, player.posZ, new ItemStack(Item.getItemFromBlock(tileEntitySignPrinter.getSetByTabID(message.currentTab)[message.currentSign])));
            player.world.spawnEntity(entityItem);
        }
        return null;
    }
}
