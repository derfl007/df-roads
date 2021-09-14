package derfl007.roads.common.tileentities;

import derfl007.roads.RecipesSign;
import derfl007.roads.Reference;
import derfl007.roads.gui.containers.ContainerSignPrinter;
import derfl007.roads.init.RoadItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.NonNullList;

public class TileEntitySignPrinter extends TileEntityLockable implements IInventory {

    protected NonNullList<ItemStack> inventory;

    private int currentSign;
    private int currentTab;
    private boolean isUsed;

    public TileEntitySignPrinter() {
        this.inventory = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
    }

    @Override
    public int getSizeInventory() {
        return 4;
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
        return this.inventory.get(index);
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

    public void setStackDamage(int index, int damage) {
        ItemStack itemStack = getStackInSlot(index);
        itemStack.setItemDamage(damage);
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
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
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
        switch (index) {
            case 0:
                return true;
            case 1:
                return stack == RoadItems.magenta_ink_cartridge.getDefaultInstance();
            case 2:
                return stack == RoadItems.yellow_ink_cartridge.getDefaultInstance();
            case 3:
                return stack == RoadItems.cyan_ink_cartridge.getDefaultInstance();
            default:
                return true;
        }
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
        this.setInventorySlotContents(0, new ItemStack(Items.AIR));
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
        return getSetByTabID(this.currentTab);
    }

    public Block[] getSetByTabID(int id) {
        switch(id) {
            case 0:
                return RecipesSign.signs_0;
            case 1:
                return RecipesSign.signs_1;
            case 2:
                return RecipesSign.signs_2;
            case 3:
                return RecipesSign.signs_3;
            case 4:
                return RecipesSign.signs_4;
        }
        return RecipesSign.signs_0;
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
