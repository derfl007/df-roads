package derfl007.roads.common.tileentities;

import derfl007.roads.Reference;
import derfl007.roads.gui.containers.ContainerSignPrinter;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.NonNullList;

import static derfl007.roads.init.RoadBlocks.*;

public class TileEntitySignPrinter extends TileEntityLockable implements IInventory {

    protected NonNullList<ItemStack> inventory;

    private int currentSign;
    private int currentTab;
    private boolean isUsed;

    public Block[] signs_0 = {
            road_sign_warn_1, // uneven road
            road_sign_warn_2a, // dangerous right curve
            road_sign_warn_2b, // dangerous left curve
            road_sign_warn_2c, // dangerous curves, first right
            road_sign_warn_2d, // dangerous curves, first left
            road_sign_warn_3a, // junction
            road_sign_warn_3b, // junction with roundabout
            road_sign_warn_4, // crossroad with non-priority road
            road_sign_warn_8a, // road narrows on both sides
            road_sign_warn_8b, // road narrows from left
            road_sign_warn_8c, // road narrows from right
            road_sign_warn_9, // road works
            road_sign_warn_10a, // slippery road
            road_sign_warn_10b, // crosswind
            road_sign_warn_10c, // falling rocks
            road_sign_warn_11a, // pedestrian crossing
            road_sign_warn_11b, // cyclist crossing
            road_sign_warn_12, // children
            road_sign_warn_13, // animals
            road_sign_warn_14, // two-way traffic
            road_sign_warn_15, // traffic signals
            road_sign_warn_16 // other dangers
    };

    public Block[] signs_1 = {
        road_sign_mandat_1, // left only
        road_sign_mandat_2, // right only
        road_sign_mandat_3, // straight only
        road_sign_mandat_4, // turn left
        road_sign_mandat_5, // turn right
        road_sign_mandat_6, // turn left or go straight
        road_sign_mandat_7, // turn right or go straight
        road_sign_mandat_8, // turn left or right
        road_sign_mandat_9, // follow left lane
        road_sign_mandat_10, // follow right lane
    };

    public Block[] signs_2 = {
            road_sign_info_1a, // parking lot or parking lane
            road_sign_info_1b, // parking lot
            road_sign_info_8a, // expressway
            road_sign_info_8b, // end of expressway
            road_sign_info_8c, // motor road
            road_sign_info_8d, // end of motor road
            road_sign_info_9a, // pedestrian zone
            road_sign_info_9b, // end of pedestrian zone
            road_sign_info_10a, // one-way left
            road_sign_info_10b, // one-way right
            road_sign_info_10c, // one-way left (german)
            road_sign_info_10d, // one-way right (german)
            road_sign_info_11, // dead end
            road_sign_info_16a, // detour right
            road_sign_info_16b, // detour left
            road_sign_info_16c, //detour right (german)
            road_sign_info_16d, //detour left (german)
            road_sign_info_23, // two lanes merge into one
            road_sign_info_23b, // two lanes merge into one (german)
    };

    public Block[] signs_3 = {
            road_sign_prohib_1, // closed in both directions for all vehicles
            road_sign_prohib_2, // no entry
            road_sign_prohib_3a, // no left turn
            road_sign_prohib_3b, // no right turn
            road_sign_prohib_3c, // no u-turn
            road_sign_prohib_4a, // no overtaking
            road_sign_prohib_4b, // end of overtaking restriction
            road_sign_prohib_10a20, // speed sign 20
            road_sign_prohib_10a30, // speed sign 30
            road_sign_prohib_10a40, // speed sign 40
            road_sign_prohib_10a50, // speed sign 50
            road_sign_prohib_10a60, // speed sign 60
            road_sign_prohib_10a70, // speed sign 70
            road_sign_prohib_10a80, // speed sign 80
            road_sign_prohib_10a100, // speed sign 100
            road_sign_prohib_10b20, // end speed sign 20
            road_sign_prohib_10b30, // end speed sign 30
            road_sign_prohib_10b40, // end speed sign 40
            road_sign_prohib_10b50, // end speed sign 50
            road_sign_prohib_10b60, // end speed sign 60
            road_sign_prohib_10b70, // end speed sign 70
            road_sign_prohib_10b80, // end speed sign 80
            road_sign_prohib_10b100 // end speed sign 100
    };

    public Block[] signs_4 = {
            road_sign_priority_1, // give way
            road_sign_priority_2, // stop
            road_sign_priority_3a, // priority road
            road_sign_priority_3b, // end of priority road
            road_town_sign
    };

    public TileEntitySignPrinter() {
        this.inventory = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemStack : this.inventory) {
            if(!itemStack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return (ItemStack) this.inventory.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack itemStack = ItemStackHelper.getAndSplit(this.inventory, index, count);
        if(!itemStack.isEmpty()) this.markDirty();
        return itemStack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.inventory, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.inventory.set(index, stack);
        if(stack.getCount() > this.getInventoryStackLimit()) stack.setCount(this.getInventoryStackLimit());
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        setUsing(true);
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        setUsing(false);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return (stack == Item.getItemById(256).getDefaultInstance());
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        this.currentSign = tagCompound.getInteger("currentSign");
        this.currentTab = tagCompound.getInteger("currentTab");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("currentSign", currentSign);
        tagCompound.setInteger("currentTab", currentTab);
        return tagCompound;
    }

    public boolean getUsing() {
        return isUsed;
    }

    public void setUsing(boolean used) {
        this.isUsed = used;
    }

    public int getCurrentSign() {
        return currentSign;
    }

    public void setCurrentSign(int currentSign) {
        this.currentSign = currentSign;
    }

    public int getCurrentTab() {
        return currentTab;
    }

    public void setCurrentTab(int currentTab) {
        this.currentTab = currentTab;
    }

    public Block[] getCurrentSet() {
        switch(this.currentTab) {
            case 0:
                return signs_0;
            case 1:
                return signs_1;
            case 2:
                return signs_2;
            case 3:
                return signs_3;
            case 4:
                return signs_4;
        }
        return signs_0;
    }

    public Block[] getSetByTabID(int id) {
        switch(id) {
            case 0:
                return signs_0;
            case 1:
                return signs_1;
            case 2:
                return signs_2;
            case 3:
                return signs_3;
            case 4:
                return signs_4;
        }
        return signs_0;
    }
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(pos, getBlockMetadata(), this.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerSignPrinter(playerInventory, this);
    }

    @Override
    public String getGuiID() {
        return Reference.MOD_ID + ":sign_printer";
    }
}
