package derfl007.roads.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import derfl007.roads.network.PacketHandler;
import derfl007.roads.network.message.MessageRoadTownSign;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

public class GuiRoadTownSign extends GuiScreen
{
    private int x, y, z;
    private GuiTextField messageTextField;
    private GuiButton doneBtn;
    private GuiButton cancelBtn;

    public GuiRoadTownSign(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        messageTextField = new GuiRoadTownSignTextField(2, fontRenderer, this.width / 2 - 150, this.height / 2 - 20, 300, 20, 50, 4);
        messageTextField.setFocused(true);
        messageTextField.setMaxStringLength(100);
        doneBtn = new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done"));
        cancelBtn = new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel"));
        buttonList.add(doneBtn);
        buttonList.add(cancelBtn);
        doneBtn.enabled = false;
    }

    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(fontRenderer, "Enter Message", this.width / 2, this.height / 2 - 40, 16777215);
        messageTextField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        messageTextField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        messageTextField.textboxKeyTyped(typedChar, keyCode);
        doneBtn.enabled = messageTextField.getText().length() > 0;
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(!button.enabled)
        {
            return;
        }

        if(button.id == 0)
        {
            PacketHandler.INSTANCE.sendToServer(new MessageRoadTownSign(x, y, z, messageTextField.getText()));
            closeGui();
        }
        else if(button.id == 1)
        {
            closeGui();
        }
    }

    private void closeGui()
    {
        this.mc.displayGuiScreen(null);

        if (this.mc.currentScreen == null)
        {
            this.mc.setIngameFocus();
        }
    }
}