package derfl007.roads.gui;

import derfl007.roads.common.tileentities.TileEntitySignPrinter;
import derfl007.roads.gui.containers.ContainerSignPrinter;
import derfl007.roads.network.PacketHandler;
import derfl007.roads.network.message.MessageSignPrinterClosed;
import derfl007.roads.network.message.MessageSignPrinterPrint;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiSignPrinter extends GuiContainer {

    private static final ResourceLocation gui = new ResourceLocation("df-roads:textures/gui/sign_printer.png");
    private TileEntitySignPrinter te;
    private GuiButton tab_1, tab_2, tab_3, tab_4, tab_5;
    private GuiButton next, prev, print;
    private int currentSign;
    private int currentTab;
    private ItemStack inputSlot;


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

        tab_1 = new GuiButton(0, centerX - 79, centerY - 114, 59, 20, "Warning");
        tab_2 = new GuiButton(1, centerX - 79, centerY - 92, 59, 20, "Mandatory");
        tab_3 = new GuiButton(2, centerX - 79, centerY - 70, 59, 20, "Info");
        tab_4 = new GuiButton(3, centerX - 79, centerY - 48, 59, 20,  "Prohibitory");
        tab_5 = new GuiButton(4, centerX - 79, centerY - 26, 59, 20, "Others");

        next = new GuiButton(5,centerX + 67, centerY - 94, 15, 20, ">");
        prev = new GuiButton(6,centerX -15, centerY - 94, 15, 20, "<");

        print = new GuiButton(7, centerX + 40, centerY - 23, 40, 20, "Print");

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
                this.inputSlot = this.te.getStackInSlot(0);
                if (inputSlot != null) {
                    if (inputSlot.getItem() == Items.IRON_INGOT) {
                        if (inputSlot.getCount() == 4) {
                            PacketHandler.INSTANCE.sendToServer(new MessageSignPrinterPrint(currentSign, currentTab, this.te.getPos().getX(), this.te.getPos().getY(), this.te.getPos().getZ(), true));
                        } else if (inputSlot.getCount() > 4) {
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
        int centerX = this.width/2;
        int centerY = this.height/2;

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
        ItemStack iron = Items.IRON_INGOT.getDefaultInstance();
        itemRender.renderItemAndEffectIntoGUI(iron, 90, 67);
        itemRender.renderItemOverlays(this.fontRenderer, iron, 90, 67);
        itemRender.zLevel = 0.0F;
        GL11.glDisable(GL11.GL_LIGHTING);

        this.fontRenderer.drawString("x4", 110, 71, 0);

        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        int centerX = width/2;
        int centerY = height/2;
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
