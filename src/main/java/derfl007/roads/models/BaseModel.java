package derfl007.roads.models;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import derfl007.roads.Reference;
import derfl007.roads.common.blocks.BlockRoadSignRotatable;
import derfl007.roads.models.signs.BaseSignBakedModel;
import derfl007.roads.models.signs.TrafficLightsBakedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;

public abstract class BaseModel implements IModel {

	private final Set<ResourceLocation> DEPENDENCIES;
	private final Set<ResourceLocation> TEXTURES;

	public BaseModel() {
		HashSet<ResourceLocation> set = new HashSet<>();

		for (String model : BaseSignBakedModel.getModelsPath()) {
			set.add(new ResourceLocation(Reference.MOD_ID, model));
		}
		
		DEPENDENCIES = Collections.unmodifiableSet(set);

		set = new HashSet<>();
		for (String texture : BlockRoadSignRotatable.getRegisteredTextures()) {
			set.add(new ResourceLocation(Reference.MOD_ID, "blocks/" + texture));
		}
		set.add(new ResourceLocation(TrafficLightsBakedModel.TEXTURE_GREEN_OFF));
		set.add(new ResourceLocation(TrafficLightsBakedModel.TEXTURE_GREEN_ON));
		set.add(new ResourceLocation(TrafficLightsBakedModel.TEXTURE_YELLOW_OFF));
		set.add(new ResourceLocation(TrafficLightsBakedModel.TEXTURE_YELLOW_ON));
		set.add(new ResourceLocation(TrafficLightsBakedModel.TEXTURE_RED_ON));
		set.add(new ResourceLocation(TrafficLightsBakedModel.TEXTURE_RED_OFF));
		set.add(new ResourceLocation(TrafficLightsBakedModel.TEXTURE_YELLOW_BLINKING));
		
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
