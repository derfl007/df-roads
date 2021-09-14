package derfl007.roads.models;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import derfl007.roads.Reference;
import derfl007.roads.common.blocks.BlockRoad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

public class RoadModel implements IModel {

	private final Set<ResourceLocation> DEPENDENCIES;
	private final Set<ResourceLocation> TEXTURES;

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format,
			Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		// TODO Auto-generated method stub
		return new RoadBakedModel(format, bakedTextureGetter);
	}

	RoadModel() {
		HashSet<ResourceLocation> set = new HashSet<>();
		DEPENDENCIES = Collections.unmodifiableSet(set);

		set = new HashSet<>();
		for (String texture : BlockRoad.getRegisteredTextures()) {
			set.add(new ResourceLocation(Reference.MOD_ID, "blocks/" + texture));
		}
		set.add(new ResourceLocation(Reference.MOD_ID, "blocks/road"));
		set.add(new ResourceLocation(Reference.MOD_ID, "blocks/road_slope_right"));
		set.add(new ResourceLocation(Reference.MOD_ID, "blocks/road_slope_left"));

		TEXTURES = Collections.unmodifiableSet(set);
	}

	@Override
	public final Collection<ResourceLocation> getDependencies() {
		return DEPENDENCIES;
	}

	@Override
	public final Collection<ResourceLocation> getTextures() {
		return TEXTURES;
	}
}
