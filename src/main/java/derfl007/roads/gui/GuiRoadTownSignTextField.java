package derfl007.roads.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class GuiRoadTownSignTextField extends GuiTextField
{
    private final int MAX_WIDTH, MAX_LINES;

    GuiRoadTownSignTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int width, int height, int maxWidth, int maxLines)
    {
        super(componentId, fontrendererObj, x, y, width, height);
        this.MAX_WIDTH = maxWidth;
        this.MAX_LINES = maxLines;
    }

    @Override
    public void writeText(String s)
    {
        if(Minecraft.getMinecraft().getRenderManager().getFontRenderer().listFormattedStringToWidth(getText() + s, MAX_WIDTH).size() <= 4)
        {
            super.writeText(s);
        }
    }
}