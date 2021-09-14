package derfl007.roads.models.signs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import derfl007.roads.common.blocks.IBlockConnectable;
import derfl007.roads.common.util.BlockStateUtil;
import derfl007.roads.models.BaseBakedModel;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;

public abstract class BaseSignBakedModel<T extends Block> extends BaseBakedModel {

	private static final String[] MODELS_PATH = { "block/road_sign", "block/road_sign_1", "block/road_sign_2",
			"block/road_sign_3", "block/road_traffic_light_model", "block/road_pedestrian_traffic_light_model",
			"block/road_sign_connector" };

	protected static final int TRAFFIC_LIGHTS_INDEX = 4;
	protected static final int PEDESTRIAN_TRAFFIC_LIGHTS_INDEX = 5;
	protected static final int CONNECTOR_INDEX = MODELS_PATH.length - 1;

	public static final ImmutableList<String> getModelsPath() {
		return ImmutableList.copyOf(MODELS_PATH);
	}

	private static final IModel[] MODELS = new IModel[MODELS_PATH.length];

	protected BaseSignBakedModel(BaseSignBakedModel<T> parentModel) {
		super(parentModel);
	}

	BaseSignBakedModel(VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		super(format, bakedTextureGetter);
	}

	protected final IModel retexture(IModel model, @Nullable IBlockState state) {
		if (state == null) {
			return retexture(model);
		}
		try {

			T block = (T) state.getBlock();
			return retexture(model, block);

		} catch (ClassCastException ex) {
			ex.printStackTrace();
			return retexture(model);
		}

	}

	/**
	 * To be overridden in subclasses.
	 * 
	 * @param model
	 * @return
	 */
	@Deprecated
	protected IModel retexture(IModel model) {
		return model;
	}

	protected abstract IModel retexture(IModel model, T block);

	@Nullable
	protected final IModel getModel(int modelIndex) {
		return getModel(MODELS_PATH, MODELS, modelIndex);
	}

	@Nullable
	protected abstract IModel getModel(@Nullable IBlockState state);

	protected abstract IModelState getModelState(IModel model, @Nullable IBlockState state);

	protected boolean shouldConnectTo(IBlockState state, EnumFacing side) {
		// TODO Auto-generated method stub
		if (!(state instanceof IExtendedBlockState)) {
			return false;
		}

		IExtendedBlockState extendedState = (IExtendedBlockState) state;

		switch (side) {
		case UP:
			return BlockStateUtil.optGetValue(extendedState, IBlockConnectable.UP, false);
		case DOWN:
			return BlockStateUtil.optGetValue(extendedState, IBlockConnectable.DOWN, false);
		case EAST:
			return BlockStateUtil.optGetValue(extendedState, IBlockConnectable.EAST, false);
		case WEST:
			return BlockStateUtil.optGetValue(extendedState, IBlockConnectable.WEST, false);
		case SOUTH:
			return BlockStateUtil.optGetValue(extendedState, IBlockConnectable.SOUTH, false);
		case NORTH:
			return BlockStateUtil.optGetValue(extendedState, IBlockConnectable.NORTH, false);
		}
		return false;
	}

	@Override
	public final List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		// TODO Auto-generated method stub

		IBakedModel fallbackModel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes()
				.getModelManager().getMissingModel();

		IModel model = getModel(state);
		if (model == null) {
			return fallbackModel.getQuads(state, side, rand);
		}

		IModelState modelState = getModelState(model, state);

		model = retexture(model, state);

		final List<BakedQuad> quads = new ArrayList<>();

		IModel modelConnector = getModel(CONNECTOR_INDEX);
		if (state != null && modelConnector != null) {
			modelConnector.uvlock(true);
			if (shouldConnectTo(state, EnumFacing.UP)) {
				quads.addAll(getQuads(modelConnector, ModelRotation.X180_Y0, state, side, rand));
			}
			if (shouldConnectTo(state, EnumFacing.DOWN)) {
				quads.addAll(getQuads(modelConnector, ModelRotation.X0_Y0, state, side, rand));
			}
			if (shouldConnectTo(state, EnumFacing.NORTH)) {
				quads.addAll(getQuads(modelConnector, ModelRotation.X90_Y180, state, side, rand));
			}
			if (shouldConnectTo(state, EnumFacing.SOUTH)) {
				quads.addAll(getQuads(modelConnector, ModelRotation.X90_Y0, state, side, rand));
			}
			if (shouldConnectTo(state, EnumFacing.EAST)) {
				quads.addAll(getQuads(modelConnector, ModelRotation.X90_Y270, state, side, rand));
			}
			if (shouldConnectTo(state, EnumFacing.WEST)) {
				quads.addAll(getQuads(modelConnector, ModelRotation.X90_Y90, state, side, rand));
			}
		}

		quads.addAll(getQuads(model, modelState, state, side, rand));

		return quads;

	}

}