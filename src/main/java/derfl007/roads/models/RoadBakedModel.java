package derfl007.roads.models;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import derfl007.roads.Reference;
import derfl007.roads.common.blocks.BlockRoad;
import derfl007.roads.render.AxisAlignedRectangleSettings;
import derfl007.roads.render.Quad;
import derfl007.roads.render.ShapeUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.property.IExtendedBlockState;

public class RoadBakedModel implements IBakedModel {

	private static final RoadItemOverrideList itemOverride = new RoadItemOverrideList();

	protected final VertexFormat format;
	protected final Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter;

	RoadBakedModel(VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		this.format = format;
		this.bakedTextureGetter = bakedTextureGetter;
	}

	protected RoadBakedModel(RoadBakedModel parentModel) {
		this.format = parentModel.format;
		this.bakedTextureGetter = parentModel.bakedTextureGetter;
	}

	protected TextureAtlasSprite getTexture(String name) {
		return bakedTextureGetter.apply(new ResourceLocation(Reference.MOD_ID, name));
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		// TODO Auto-generated method stub

		List<BakedQuad> quads = new ArrayList<>();
		if (state == null || !(state instanceof IExtendedBlockState)) {
			return quads;
		}

		IExtendedBlockState extendedState = (IExtendedBlockState) state;

		BlockRoad block = (BlockRoad) extendedState.getBlock();
		EnumFacing facing = extendedState.getValue(BlockRoad.FACING);
		EnumFacing textureFacing = extendedState.getValue(BlockRoad.TEXTURE_ROTATION);
		boolean slope = extendedState.getValue(BlockRoad.SLOPE);
		float slopeBottom = extendedState.getValue(BlockRoad.SLOPE_BOTTOM);
		float blockBottom = Math.min(slopeBottom, 0F);

		float height = extendedState.getValue(BlockRoad.HEIGHT);

		TextureAtlasSprite roadSprite = getTexture("blocks/road");
		TextureAtlasSprite specificTexture = block.getTextureSprite(extendedState, bakedTextureGetter);

		Quad bottom = ShapeUtil.createAxisAlignedRectangle(EnumFacing.DOWN,
				new AxisAlignedRectangleSettings().setOffset(blockBottom), roadSprite, format);
		quads.add(bottom.bake());

		Quad top;
		if (slope) {

			TextureAtlasSprite slopeLeftSprite = getTexture("blocks/road_slope_left");
			TextureAtlasSprite slopeRightSprite = getTexture("blocks/road_slope_right");

			BakedQuad back = ShapeUtil.createAxisAlignedRectangle(facing.getOpposite(),
					new AxisAlignedRectangleSettings().setBottom(blockBottom).setHeight(height - blockBottom),
					roadSprite, format).bake();
			quads.add(back);

			if (slopeBottom > blockBottom) {
				AxisAlignedRectangleSettings bottomSettings = new AxisAlignedRectangleSettings().setBottom(blockBottom)
						.setHeight(slopeBottom - blockBottom);

				BakedQuad leftBottom = ShapeUtil
						.createAxisAlignedRectangle(facing.rotateY(), bottomSettings, roadSprite, format).bake();
				quads.add(leftBottom);

				BakedQuad rightBottom = ShapeUtil
						.createAxisAlignedRectangle(facing.rotateYCCW(), bottomSettings, roadSprite, format).bake();
				quads.add(rightBottom);
			}

			AxisAlignedRectangleSettings slopeSettings = new AxisAlignedRectangleSettings().setBottom(slopeBottom)
					.setHeight(height - slopeBottom);

			Quad leftSlope = ShapeUtil.createAxisAlignedRectangle(facing.rotateY(), slopeSettings, slopeLeftSprite,
					format);
			leftSlope.fillTexture();
			quads.add(leftSlope.bake());

			Quad rightSlope = ShapeUtil.createAxisAlignedRectangle(facing.rotateYCCW(), slopeSettings, slopeRightSprite,
					format);
			rightSlope.fillTexture();
			quads.add(rightSlope.bake());

			top = new Quad(new Vec3d(0, height, 0), new Vec3d(0, slopeBottom, 1), new Vec3d(1, slopeBottom, 1),
					new Vec3d(1, height, 0), specificTexture, format);
			top.fillTexture();

			top.rotateY(-facing.getHorizontalIndex() * 90);
			for (int i = 0; i < (textureFacing.getHorizontalIndex() + 2) % 4; i++) {
				top.rotateTexture90Clockwise();
			}
		} else {

			AxisAlignedRectangleSettings settings = new AxisAlignedRectangleSettings().setHeight(height);

			BakedQuad east = ShapeUtil.createAxisAlignedRectangle(EnumFacing.EAST, settings, roadSprite, format).bake();
			BakedQuad west = ShapeUtil.createAxisAlignedRectangle(EnumFacing.WEST, settings, roadSprite, format).bake();
			BakedQuad south = ShapeUtil.createAxisAlignedRectangle(EnumFacing.SOUTH, settings, roadSprite, format)
					.bake();
			BakedQuad north = ShapeUtil.createAxisAlignedRectangle(EnumFacing.NORTH, settings, roadSprite, format)
					.bake();
			top = ShapeUtil.createAxisAlignedRectangle(EnumFacing.UP,
					new AxisAlignedRectangleSettings().setOffset(1D - height), specificTexture, format);

			quads.add(east);
			quads.add(west);
			quads.add(south);
			quads.add(north);
			for (int i = 0; i < (textureFacing.getHorizontalIndex() + facing.getHorizontalIndex() + 2) % 4; i++) {
				top.rotateTexture90Clockwise();
			}
		}

		quads.add(top.bake());

		return quads;
	}

	@Override
	public final TextureAtlasSprite getParticleTexture() {
		// TODO Auto-generated method stub
		return getTexture("blocks/road");
	}

	@Override
	public ItemOverrideList getOverrides() {
		// TODO Auto-generated method stub
		return itemOverride;
	}

	@Override
	public final boolean isAmbientOcclusion() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public final boolean isGui3d() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public final boolean isBuiltInRenderer() {
		// TODO Auto-generated method stub
		return false;
	}

}
