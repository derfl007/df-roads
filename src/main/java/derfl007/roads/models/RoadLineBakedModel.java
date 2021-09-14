package derfl007.roads.models;

import java.util.List;
import java.util.function.Function;

import derfl007.roads.common.blocks.BlockRoad;
import derfl007.roads.common.blocks.BlockRoadLine;
import derfl007.roads.render.Quad;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.property.IExtendedBlockState;

public class RoadLineBakedModel extends RoadBakedModel {

	private class AutoVec3d {

		private final BlockRoad block;
		private final boolean slope;
		private final float height;
		private final float slopeBottom;
		private final EnumFacing facing;

		public AutoVec3d(BlockRoad block, EnumFacing facing, float height, boolean slope, float slopeBottom) {
			this.block = block;
			this.slope = slope;
			this.height = height;
			this.slopeBottom = slopeBottom;
			this.facing = facing;
		}

		public Vec3d create(double xIn, double zIn) {
			xIn /= 16d;
			zIn /= 16d;

			// increment by 0.005 to avoid flickering due to
			// interference with the underneath road texture.
			return new Vec3d(xIn, block.getHeightAtPosition(facing, height, slope, slopeBottom, xIn, zIn) + 0.005, zIn);
		}

	}

	RoadLineBakedModel(VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		super(format, bakedTextureGetter);
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		// TODO Auto-generated method stub

		// state may not be null; item rendering is done by RoadBakedModel
		List<BakedQuad> quads = super.getQuads(state, side, rand);

		IExtendedBlockState extendedState = (IExtendedBlockState) state;

		BlockRoadLine block = (BlockRoadLine) extendedState.getBlock();
		boolean yellow = block.isYellow();

		boolean slope = extendedState.getValue(BlockRoad.SLOPE);
		float height = extendedState.getValue(BlockRoad.HEIGHT);

		float slopeBottom = extendedState.getValue(BlockRoad.SLOPE_BOTTOM);
		EnumFacing facing = extendedState.getValue(BlockRoad.FACING);

		boolean south = extendedState.getValue(BlockRoadLine.SOUTH);
		boolean east = extendedState.getValue(BlockRoadLine.EAST);
		boolean north = extendedState.getValue(BlockRoadLine.NORTH);
		boolean west = extendedState.getValue(BlockRoadLine.WEST);

		TextureAtlasSprite roadLineSprite = yellow ? getTexture("blocks/road_yellow") : getTexture("blocks/road_white");

		AutoVec3d vecCreator = new AutoVec3d(block, facing, height, slope, slopeBottom);

		Quad mid = new Quad(vecCreator.create(6, 10), vecCreator.create(10, 10), vecCreator.create(10, 6),
				vecCreator.create(6, 6), roadLineSprite, format);
		quads.add(mid.bake());

		if (south) {
			Quad line = new Quad(vecCreator.create(6, 10), vecCreator.create(6, 16), vecCreator.create(10, 16),
					vecCreator.create(10, 10), roadLineSprite, format);

			quads.add(line.bake());
		}
		if (north) {
			Quad line = new Quad(vecCreator.create(6, 0), vecCreator.create(6, 6), vecCreator.create(10, 6),
					vecCreator.create(10, 0), roadLineSprite, format);

			quads.add(line.bake());
		}
		if (west) {
			Quad line = new Quad(vecCreator.create(0, 6), vecCreator.create(0, 10), vecCreator.create(6, 10),
					vecCreator.create(6, 6), roadLineSprite, format);

			quads.add(line.bake());
		}
		if (east) {
			Quad line = new Quad(vecCreator.create(10, 6), vecCreator.create(10, 10), vecCreator.create(16, 10),
					vecCreator.create(16, 6), roadLineSprite, format);

			quads.add(line.bake());
		}

		return quads;
	}

}
