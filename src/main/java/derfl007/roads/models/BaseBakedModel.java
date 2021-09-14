package derfl007.roads.models;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.annotation.Nullable;

import derfl007.roads.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

public abstract class BaseBakedModel implements IBakedModel {

	private final VertexFormat format;
	private final Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter;

	protected BaseBakedModel(BaseBakedModel parentModel) {
		this.format = parentModel.format;
		this.bakedTextureGetter = parentModel.bakedTextureGetter;
	}

	protected BaseBakedModel(VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		this.format = format;
		this.bakedTextureGetter = bakedTextureGetter;
	}

	protected final List<BakedQuad> getQuads(@Nullable IModel model, IModelState modelState,
			@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		if (model != null) {
			if (modelState == null) {
				modelState = model.getDefaultState();
			}
			IBakedModel bakedModel = model.bake(modelState, format, bakedTextureGetter);
			if (bakedModel != null)
				return bakedModel.getQuads(state, side, rand);
		}
		return new ArrayList<>();
	}

	@Override
	public final boolean isAmbientOcclusion() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public final boolean isGui3d() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public final boolean isBuiltInRenderer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Nullable
	protected static final IModel getModel(String[] modelsPath, IModel[] models, int modelIndex) {
		if (models[modelIndex] == null) {
			try {
				models[modelIndex] = ModelLoaderRegistry
						.getModel(new ResourceLocation(Reference.MOD_ID, modelsPath[modelIndex]));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return models[modelIndex];
	}

	protected IModelState getModelState(EnumFacing facing) {

		switch (facing) {
		case WEST:
			return ModelRotation.X0_Y90;

		case NORTH:
			return ModelRotation.X0_Y180;

		case EAST:
			return ModelRotation.X0_Y270;

		default:
			return ModelRotation.X0_Y0;
		}
	}
	
/*	protected List<BakedQuad> translate(List<BakedQuad> quads, int translX, int translY, int translZ) {
		
	}*/

}
