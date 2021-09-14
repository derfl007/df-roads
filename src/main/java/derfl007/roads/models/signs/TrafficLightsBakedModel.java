package derfl007.roads.models.signs;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;

import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightBase;
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

public class TrafficLightsBakedModel extends BaseSignBakedModel<BlockRoadTrafficLightBase> {
	private static final TrafficLightsItemOverride itemOverride = new TrafficLightsItemOverride();

	public static final String TEXTURE_GREEN_OFF = "df-roads:blocks/traffic_light_green_off";
	public static final String TEXTURE_YELLOW_OFF = "df-roads:blocks/traffic_light_yellow_off";
	public static final String TEXTURE_RED_OFF = "df-roads:blocks/traffic_light_red_off";

	public static final String TEXTURE_GREEN_ON = "df-roads:blocks/traffic_light_green_on";
	public static final String TEXTURE_YELLOW_ON = "df-roads:blocks/traffic_light_yellow_on";
	public static final String TEXTURE_RED_ON = "df-roads:blocks/traffic_light_red_on";

	public static final String TEXTURE_YELLOW_BLINKING = "df-roads:blocks/traffic_light_yellow_blinking";

	TrafficLightsBakedModel(VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		super(format, bakedTextureGetter);
	}

	protected TrafficLightsBakedModel(TrafficLightsBakedModel parentModel) {
		super(parentModel);
	}

	@Nullable
	@Override
	protected IModel getModel(@Nullable IBlockState state) {
		// TODO Auto-generated method stub
		int modelIndex = TRAFFIC_LIGHTS_INDEX;
		if (state != null) {
			BlockRoadTrafficLightBase trafficLight = (BlockRoadTrafficLightBase) state.getBlock();
			if (trafficLight.isPedestrianLights()) {
				modelIndex = PEDESTRIAN_TRAFFIC_LIGHTS_INDEX;
			}
		}
		return getModel(modelIndex);
	}

	@Nullable
	@Override
	protected IModelState getModelState(IModel model, @Nullable IBlockState state) {
		// TODO Auto-generated method stub

		if (state == null) {
			return ModelRotation.X0_Y0;
		}

		final EnumFacing facing = state.getValue(BlockRoadTrafficLightBase.FACING);

		return getModelState(facing);
	}

	@Override
	protected IModel retexture(IModel model, BlockRoadTrafficLightBase trafficLight) {

		Map<String, String> textures = new HashMap<>();

		textures.put("1", TEXTURE_GREEN_OFF);
		if (trafficLight.isPedestrianLights()) {
			textures.put("2", TEXTURE_RED_OFF);
		} else {
			textures.put("2", TEXTURE_YELLOW_OFF);
			textures.put("3", TEXTURE_RED_OFF);
		}

		switch (trafficLight.getState()) {
		case GREEN:
			textures.put("1", TEXTURE_GREEN_ON);
			break;
		case YELLOW:
			textures.put("2", TEXTURE_YELLOW_ON);
			break;
		case RED:
			textures.put(trafficLight.isPedestrianLights() ? "2" : "3", TEXTURE_RED_ON);
			break;
		case DEACTIVATED:
			if (!trafficLight.isPedestrianLights()) {
				textures.put("2", TEXTURE_YELLOW_BLINKING);
			}
		}

		model = model.retexture(ImmutableMap.copyOf(textures));

		return model;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		// TODO Auto-generated method stub
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/coal_block");
	}

	@Override
	public ItemOverrideList getOverrides() {
		// TODO Auto-generated method stub
		return itemOverride;
	}

}
