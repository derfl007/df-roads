package derfl007.roads.client.renderer.tileentity;

import java.awt.Color;
import java.util.List;

import derfl007.roads.common.blocks.BlockRoadTownSign;
import derfl007.roads.common.tileentities.TileEntityRoadTownSign;
import derfl007.roads.common.util.SignOrientation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityRoadTownSignRenderer extends FastTESR<TileEntityRoadTownSign> {
    @Override
    public void renderTileEntityFast(TileEntityRoadTownSign te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
        String message = te.getMessage();
        if(message == null) return;
//        System.out.println("message: "+message);
        IBlockState state = getWorld().getBlockState(te.getPos());
        if(state.getBlock() instanceof BlockRoadTownSign) {
            int rotation = state.getValue(BlockRoadTownSign.ORIENTATION);
            GlStateManager.pushMatrix();
            {
                GlStateManager.translate(x, y, z); //set block as origin
                GlStateManager.translate(0.5, 0.5, 0.5); //translate origin to the middle of the block
                GlStateManager.rotate(SignOrientation.toAngle(rotation), 0, 1, 0); //rotate origin according to block facing
                GlStateManager.translate(-0.375, 0, 0.1); //translate origin to the left side of the sign and to the correct depth
                GlStateManager.scale(1, -1, -1);
                GlStateManager.scale(0.015625F, 0.015625F, 0.015625F);
                int lines = getFontRenderer().listFormattedStringToWidth(message, 50).size();
                GlStateManager.translate(0, -(lines * getFontRenderer().FONT_HEIGHT - 1) / 2, 0);
                GlStateManager.enableAlpha();
                GlStateManager.disableLighting();
                drawSplitString(getFontRenderer(), message, 0, 0, 50, Color.black.getRGB());
                GlStateManager.enableLighting();
                GlStateManager.disableAlpha();
            }
            GlStateManager.popMatrix();
        }
     
    }

    public void drawSplitString(FontRenderer renderer, String str, int x, int y, int wrapWidth, int textColor)
    {
        str = this.trimStringNewline(str);
        this.renderSplitStringCentered(renderer, str, x, y, wrapWidth, textColor);
    }

    private void renderSplitStringCentered(FontRenderer renderer, String str, int x, int y, int wrapWidth, int textColor)
    {
        List<String> lines = renderer.listFormattedStringToWidth(str, wrapWidth);
        for (int i = 0; i < lines.size() && i < 4; i++)
        {
            String line = lines.get(i);
            x = (wrapWidth + -renderer.getStringWidth(line)) / 2;
            renderer.drawString(line, x, y, textColor);
            y += renderer.FONT_HEIGHT;
        }
    }

    private String trimStringNewline(String text)
    {
        while (text != null && text.endsWith("\n"))
        {
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }
}
