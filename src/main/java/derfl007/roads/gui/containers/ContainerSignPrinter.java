package derfl007.roads.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSignPrinter extends Container {

    IInventory signPrinterInventory;

    public ContainerSignPrinter(IInventory playerInventory, IInventory signPrinterInventory) {
        this.signPrinterInventory = signPrinterInventory;
        signPrinterInventory.openInventory(null);

        this.addSlotToContainer(new Slot(signPrinterInventory, 0, 136, 48));
        this.addSlotToContainer(new Slot(signPrinterInventory, 1, 90, 72));
        this.addSlotToContainer(new Slot(signPrinterInventory, 2, 113, 72));
        this.addSlotToContainer(new Slot(signPrinterInventory, 3, 136, 72));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 115));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(playerInventory, i, i * 18 + 8, 173));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotId) {
        ItemStack itemCopy = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(slotId);

        if (slot != null && slot.getHasStack()) {
            ItemStack item = slot.getStack();
            itemCopy = item.copy();

            if (slotId <= 4) {
                if (!this.mergeItemStack(item, 1, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }else if (!this.mergeItemStack(item, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (item.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemCopy;
    }
}
