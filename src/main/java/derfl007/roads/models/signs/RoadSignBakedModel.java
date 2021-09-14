package derfl007.roads.models.signs;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;

import derfl007.roads.Reference;
import derfl007.roads.common.blocks.BlockRoadSignRotatable;
import derfl007.roads.common.util.SignOrientation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

public class RoadSignBakedModel extends BaseSignBakedModel<BlockRoadSignRotatable> {

	RoadSignBakedModel(VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		super(format, bakedTextureGetter);
	}

	protected RoadSignBakedModel(RoadSignBakedModel parentModel) {
		super(parentModel);
	}

	private static final RoadSignItemOverride itemOverride = new RoadSignItemOverride();

	@Override
	@Nullable
	protected final IModel getModel(@Nullable IBlockState state) {
		int modelIndex = 0;

		if (state != null) {
			final int rotation = state.getValue(BlockRoadSignRotatable.ORIENTATION);
			modelIndex = rotation % 4;
		}
		return getModel(modelIndex);

	}

	@Override
	protected IModelState getModelState(IModel model, @Nullable IBlockState state) {

		if (state == null) {
			return model.getDefaultState();
		}

		final int rotation = state.getValue(BlockRoadSignRotatable.ORIENTATION);

		return getModelState(SignOrientation.toFacing(rotation));
	}

	@Override
	protected final IModel retexture(IModel model, BlockRoadSignRotatable block) {
		Map<String, String> textures = new HashMap<>();
		textures.put("front", "df-roads:blocks/" + block.getFrontTexturePath());
		textures.put("back", "df-roads:blocks/" + block.getBackTexturePath());
		model = model.retexture(ImmutableMap.copyOf(textures));

		return model;
	}

	@Override
	public final TextureAtlasSprite getParticleTexture() {
		// TODO Auto-generated method stub
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(Reference.MOD_ID + ":blocks/metal");
	}

	@Override
	public ItemOverrideList getOverrides() {
		// TODO Auto-generated method stub
		return itemOverride;
	}

	@Override
	protected IModelState getModelState(EnumFacing facing) {

		switch (facing) {
		case WEST:
			return ModelRotation.X0_Y270;

		case NORTH:
			return ModelRotation.X0_Y180;

		case EAST:
			return ModelRotation.X0_Y90;

		default:
			return ModelRotation.X0_Y0;
		}
	}

}
