package derfl007.roads.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerSignPrinter extends Container {

    IInventory signPrinterInventory;

    public ContainerSignPrinter(IInventory playerInventory, IInventory signPrinterInventory) {
        this.signPrinterInventory = signPrinterInventory;
        signPrinterInventory.openInventory(null);

        this.addSlotToContainer(new Slot(signPrinterInventory, 0, 136, 68));

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
}
