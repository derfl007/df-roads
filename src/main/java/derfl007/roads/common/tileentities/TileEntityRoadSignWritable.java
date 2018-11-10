package derfl007.roads.common.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRoadSignWritable extends TileEntity {
    private String message = null;
    public int x = 0;
    public int y = 0;
    public int textSize = 0;

    /**
     * Constructer for writable Road Signs
     * @param x Sets the x position of where the text should start (from the middle of the block)
     * @param y Sets the y position of where the text should start (from the middle of the block)
     * @param textSize Sets the text size
     */
    public TileEntityRoadSignWritable(int x, int y, int textSize) {
        this.x = x;
        this.y = y;
        this.textSize = textSize;
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    public String getMessage() {
//        System.out.println("get message: " + message);
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.message = tagCompound.getString("message");
        this.x = tagCompound.getInteger("x");
        this.y = tagCompound.getInteger("y");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        if(this.message != null) tagCompound.setString("message", this.message);
        tagCompound.setInteger("x", this.x);
        tagCompound.setInteger("y", this.y);
        return tagCompound;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
//        System.out.println("message received by te: " + pkt.getNbtCompound().getString("message"));
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, getBlockMetadata(), this.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }
}
