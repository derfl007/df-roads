package derfl007.roads.models;

import java.util.ArrayList;
import java.util.List;

import derfl007.roads.common.blocks.BlockRoad;
import derfl007.roads.render.AxisAlignedRectangleSettings;
import derfl007.roads.render.Quad;
import derfl007.roads.render.ShapeUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class RoadItemBakedModel extends RoadBakedModel {

	private final BlockRoad block;

	RoadItemBakedModel(RoadBakedModel parentModel, BlockRoad block) {
		super(parentModel);
		this.block = block;
	}

	@Override
	public ItemOverrideList getOverrides() {
		// TODO Auto-generated method stub
		throw new IllegalStateException();
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		// TODO Auto-generated method stub

		List<BakedQuad> quads = new ArrayList<>();

		float height = (float) block.getHeight();

		TextureAtlasSprite roadSprite = getTexture("blocks/road");
		TextureAtlasSprite specificTexture = block.getTextureSprite(null, bakedTextureGetter);

		List<Quad> unbakedQuads = new ArrayList<>();

		AxisAlignedRectangleSettings slopeSettings = new AxisAlignedRectangleSettings().setHeight(height);
		Quad bottom = ShapeUtil.createAxisAlignedRectangle(EnumFacing.DOWN, slopeSettings, roadSprite, format);
		Quad west = ShapeUtil.createAxisAlignedRectangle(EnumFacing.WEST, slopeSettings, roadSprite, format);
		Quad east = ShapeUtil.createAxisAlignedRectangle(EnumFacing.EAST, slopeSettings, roadSprite, format);
		Quad north = ShapeUtil.createAxisAlignedRectangle(EnumFacing.NORTH, slopeSettings, roadSprite, format);
		Quad south = ShapeUtil.createAxisAlignedRectangle(EnumFacing.SOUTH, slopeSettings, roadSprite, format);

		Quad top = ShapeUtil.createAxisAlignedRectangle(EnumFacing.UP,
				new AxisAlignedRectangleSettings().setOffset(1D - height), specificTexture, format);

		unbakedQuads.add(bottom);
		unbakedQuads.add(west);
		unbakedQuads.add(east);
		unbakedQuads.add(north);
		unbakedQuads.add(top);
		unbakedQuads.add(south);

		for (Quad quad : unbakedQuads) {
			quad.rotateY(45);
			quad.rotateX(30);
			quad.scale(0.62);

			quads.add(quad.bake());
		}

		return quads;
	}
}
