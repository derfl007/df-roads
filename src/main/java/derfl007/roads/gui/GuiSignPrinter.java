package derfl007.roads.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import derfl007.roads.RecipesSign;
import derfl007.roads.common.tileentities.TileEntitySignPrinter;
import derfl007.roads.gui.containers.ContainerSignPrinter;
import derfl007.roads.init.RoadItems;
import derfl007.roads.network.PacketHandler;
import derfl007.roads.network.message.MessageSignPrinterClosed;
import derfl007.roads.network.message.MessageSignPrinterPrint;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiSignPrinter extends GuiContainer {

    private static final ResourceLocation gui = new ResourceLocation("df-roads:textures/gui/sign_printer.png");
    private TileEntitySignPrinter te;
    private GuiButton tab_1, tab_2, tab_3, tab_4, tab_5;
    private GuiButton next, prev, print;
    private int currentSign;
    private int currentTab;
    private ItemStack inputSlot;
    private ItemStack magentaSlot;
    private ItemStack yellowSlot;
    private ItemStack cyanSlot;
    private Item baseItem;



    public GuiSignPrinter(InventoryPlayer inventoryPlayer, TileEntitySignPrinter te) {
        super(new ContainerSignPrinter(inventoryPlayer, te));
        this.te = te;
        this.xSize = 176;
        this.ySize = 207;
    }

    @Override
    public void initGui() {
        super.initGui();

        buttonList.clear();

        int centerX = width/2;
        int centerY = height/2+10;

        tab_1 = new GuiButton(0, centerX - 79, centerY - 114, 59, 20, I18n.format("gui.sign_printer.warning"));
        tab_2 = new GuiButton(1, centerX - 79, centerY - 92, 59, 20, I18n.format("gui.sign_printer.mandatory"));
        tab_3 = new GuiButton(2, centerX - 79, centerY - 70, 59, 20, I18n.format("gui.sign_printer.info"));
        tab_4 = new GuiButton(3, centerX - 79, centerY - 48, 59, 20,  I18n.format("gui.sign_printer.prohibitory"));
        tab_5 = new GuiButton(4, centerX - 79, centerY - 26, 59, 20, I18n.format("gui.sign_printer.others"));

        next = new GuiButton(5,centerX + 67, centerY - 94, 15, 20, ">");
        prev = new GuiButton(6,centerX -15, centerY - 94, 15, 20, "<");

        print = new GuiButton(7, centerX + 40, centerY - 23, 40, 20, I18n.format("gui.sign_printer.print"));

        buttonList.add(tab_1);
        buttonList.add(tab_2);
        buttonList.add(tab_3);
        buttonList.add(tab_4);
        buttonList.add(tab_5);
        buttonList.add(next);
        buttonList.add(prev);
        buttonList.add(print);

        this.currentSign = te.getCurrentSign();
        this.currentTab = te.getCurrentTab();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(!button.enabled) return;
        switch(button.id) {
            case 0:
                this.te.setCurrentTab(0);
                this.te.setCurrentSign(0);
                this.currentTab = 0;
                this.currentSign = 0;
                break;
            case 1:
                this.te.setCurrentTab(1);
                this.te.setCurrentSign(0);
                this.currentTab = 1;
                this.currentSign = 0;
                break;
            case 2:
                this.te.setCurrentTab(2);
                this.te.setCurrentSign(0);
                this.currentTab = 2;
                this.currentSign = 0;
                break;
            case 3:
                this.te.setCurrentTab(3);
                this.te.setCurrentSign(0);
                this.currentTab = 3;
                this.currentSign = 0;
                break;
            case 4:
                this.te.setCurrentTab(4);
                this.te.setCurrentSign(0);
                this.currentTab = 4;
                this.currentSign = 0;
                break;
            case 5:
                currentSign++;
                if (currentSign > te.getCurrentSet().length - 1) {
                    currentSign = te.getCurrentSet().length - 1;
                }
                this.te.setCurrentSign(currentSign);
                System.out.println(te.getCurrentSign());
                break;
            case 6:
                currentSign--;
                if (currentSign < 0) {
                    currentSign = 0;
                }
                this.te.setCurrentSign(currentSign);
                System.out.println(te.getCurrentSign());
                break;
            case 7:
                this.inputSlot = this.inventorySlots.getSlot(0).getStack();
                this.magentaSlot = this.inventorySlots.getSlot(1).getStack();
                this.yellowSlot = this.inventorySlots.getSlot(2).getStack();
                this.cyanSlot = this.inventorySlots.getSlot(3).getStack();
                this.baseItem = RecipesSign.getBaseItem(currentTab);
                int magentaRequired = 32 - RecipesSign.getDamage("M", currentSign, currentTab);
                int yellowRequired = 32 - RecipesSign.getDamage("Y", currentSign, currentTab);
                int cyanRequired = 32 - RecipesSign.getDamage("C", currentSign, currentTab);
                if (inputSlot != null) {
                    if (inputSlot.getItem() == baseItem &&
                            (magentaSlot.getItem() == RoadItems.magenta_ink_cartridge) &&
                            (yellowSlot.getItem() == RoadItems.yellow_ink_cartridge) &&
                            (cyanSlot.getItem() == RoadItems.cyan_ink_cartridge)) {
                        if (inputSlot.getCount() == RecipesSign.getBaseItemCount(currentTab) && magentaSlot.getItemDamage() < magentaRequired && yellowSlot.getItemDamage() < yellowRequired && cyanSlot.getItemDamage() < cyanRequired) {
                            PacketHandler.INSTANCE.sendToServer(new MessageSignPrinterPrint(currentSign, currentTab, this.te.getPos().getX(), this.te.getPos().getY(), this.te.getPos().getZ(), true));
                        } else if (inputSlot.getCount() > RecipesSign.getBaseItemCount(currentTab) && magentaSlot.getItemDamage() < magentaRequired && yellowSlot.getItemDamage() < yellowRequired && cyanSlot.getItemDamage() < cyanRequired) {
                            PacketHandler.INSTANCE.sendToServer(new MessageSignPrinterPrint(currentSign, currentTab, this.te.getPos().getX(), this.te.getPos().getY(), this.te.getPos().getZ(), false));
                        }
                    }
                }
        }
    }

    @Override
    public void onGuiClosed() {
        PacketHandler.INSTANCE.sendToServer(new MessageSignPrinterClosed(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ()));
        super.onGuiClosed();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        itemRender.zLevel = 100.0F;

        if ((currentSign - 1) >= 0)
        {
            ItemStack prev = Item.getItemFromBlock(te.getCurrentSet()[this.currentSign - 1]).getDefaultInstance();
            itemRender.renderItemAndEffectIntoGUI(prev, 90, 24);
            itemRender.renderItemOverlays(this.fontRenderer, prev, 90, 24);
        }

        ItemStack stock = Item.getItemFromBlock(te.getCurrentSet()[this.currentSign]).getDefaultInstance();
        itemRender.renderItemAndEffectIntoGUI(stock, 113, 24);
        itemRender.renderItemOverlays(this.fontRenderer, stock, 113, 24);

        if ((currentSign + 1) < te.getCurrentSet().length)
        {
            ItemStack next = Item.getItemFromBlock(te.getCurrentSet()[this.currentSign + 1]).getDefaultInstance();
            itemRender.renderItemAndEffectIntoGUI(next, 136, 24);
            itemRender.renderItemOverlays(this.fontRenderer, next, 136, 24);
        }
        ItemStack iron = RecipesSign.getBaseItem(currentTab).getDefaultInstance();

        itemRender.renderItemAndEffectIntoGUI(iron, 90, 47);
        itemRender.renderItemOverlays(this.fontRenderer, iron, 90, 47);
        itemRender.zLevel = 0.0F;
        GL11.glDisable(GL11.GL_LIGHTING);

        this.fontRenderer.drawString("x" + RecipesSign.getBaseItemCount(currentTab), 110, 51, 0);

        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        super.drawScreen(par1, par2, par3);
        ItemStack currentSign = Item.getItemFromBlock(te.getCurrentSet()[this.currentSign]).getDefaultInstance();
        if (this.isPointInRegion(113, 22, 16, 16, par1, par2))
        {
            this.renderToolTip(currentSign, par1, par2);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(gui);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        this.drawTexturedModalRect(l, i1 - 10, 0, 0, xSize, ySize + 21);
    }
}
