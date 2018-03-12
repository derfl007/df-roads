package derfl007.roads.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSlope extends ModelBase {

    public static ModelRenderer left;
    public static ModelRenderer right;
    public static ModelRenderer back;
    public static ModelRenderer plane;

    public ModelSlope() {
        right = new ModelRenderer(this, 0, 0);
        right.addBox(0, 0, 0, 0, 15, 16);
        left = new ModelRenderer(this, 0, 0);
        left.addBox(0, 0, 0, 0, 15, 16);
        back = new ModelRenderer(this, 0, 0);
        back.addBox(0, 0,0, 16, 15 ,0);
        plane = new ModelRenderer(this, 0, 0);
        plane.addBox(0, 0, 0, 16, 0, 16);
        plane.rotateAngleX = (float) 0.44610616;
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        right.render(0.0625F);
        left.render(0.0625F);
        back.render(0.0625F);
        plane.render(0.0625F);
    }
}
