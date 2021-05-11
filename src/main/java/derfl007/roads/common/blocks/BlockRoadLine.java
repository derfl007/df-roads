package derfl007.roads.common.blocks;

import java.util.List;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import derfl007.roads.common.util.UnlistedPropertyBoolean;
import derfl007.roads.init.RoadBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;

public class BlockRoadLine extends BlockRoad {

	private final boolean yellow;

	public boolean isYellow() {
		return yellow;
	}

	public static final UnlistedPropertyBoolean NORTH = new UnlistedPropertyBoolean("north");
	public static final UnlistedPropertyBoolean EAST = new UnlistedPropertyBoolean("east");
	public static final UnlistedPropertyBoolean SOUTH = new UnlistedPropertyBoolean("south");
	public static final UnlistedPropertyBoolean WEST = new UnlistedPropertyBoolean("west");

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			tooltip.add(I18n.format("block.road_lines.tooltip"));
		} else {
			tooltip.add(I18n.format("gui.tooltip.view_more"));
		}
	}

	private static Block[] create(String name, String texture, boolean yellow) {
		return new Block[] { new BlockRoadLine(name, texture, 0, yellow), new BlockRoadLine(name, texture, 1, yellow),
				new BlockRoadLine(name, texture, 2, yellow), new BlockRoadLine(name, texture, 3, yellow) };
	}

	public static Block[] create() {
		return create("road_line", "road", false);
	}

	public static Block[] createYellow() {
		return create("road_line_yellow", "road", true);
	}

	/**
	 * @deprecated Call from BlockRoad.
	 * @param name
	 * @param texture
	 * @return
	 */
	@Deprecated
	public static Block[] create(String name, String texture) {
		return BlockRoad.create(name, texture);
	}

	/**
	 * @deprecated Call from BlockRoad.
	 * @param name
	 * @param texture
	 * @return
	 */
	@Deprecated
	public static Block[] create(String name) {
		return BlockRoad.create(name);
	}

	/**
	 * @deprecated Call from BlockRoad.
	 * @param name
	 * @param texture
	 * @return
	 */
	@Deprecated
	public static Block[] createYellow(String name, String texture) {
		return BlockRoad.createYellow(name, texture);
	}

	/**
	 * @deprecated Call from BlockRoad.
	 * @param name
	 * @param texture
	 * @return
	 */
	@Deprecated
	public static Block[] createYellow(String name) {
		return BlockRoad.createYellow(name);
	}

	protected BlockRoadLine(String name, String texture, int height, boolean yellow) {
		super(name, texture, height, yellow, false);

		this.yellow = yellow;
	}

	@Override
	public TextureAtlasSprite getTextureSprite(@Nullable IBlockState state,
			Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
		if (state == null) { // rendered as item
			if (yellow) {
				return getTexture("road_line_single_yellow", textureGetter);
			} else {
				return getTexture("road_line_single", textureGetter);
			}
		}
		return super.getTextureSprite(state, textureGetter);
	}

	private  boolean canConnectTo(SlopeState block) {
		if (block == null) {
			return false;
		}
		for (connectableBlocks b : connectableBlocks.values()) {
			for (Block bl : b.blocks()) {
				if (bl.equals(block.getBlock())) {
					return true;
				}
			}
		}
		return false;
	}

	public enum connectableBlocks {
		ROAD_LINE(RoadBlocks.road_line), ROAD_LINE_SIMPLE(RoadBlocks.road_line_simple),
		ROAD_LINE_MERGE(RoadBlocks.road_line_merge), ROAD_LINE_DIAGONAL(RoadBlocks.road_line_diagonal),
		ROAD_WHITE_QUARTER(RoadBlocks.road_white_quarter), ROAD_WHITE_HALF(RoadBlocks.road_white_half),
		ROAD_EXCL_ZONE_SPLIT_IN_L(RoadBlocks.road_excl_zone_split_in_l),
		ROAD_EXCL_ZONE_SPLIT_IN_R(RoadBlocks.road_excl_zone_split_in_r),
		ROAD_EXCL_ZONE_SPLIT_OUT_L(RoadBlocks.road_excl_zone_split_out_l),
		ROAD_EXCL_ZONE_SPLIT_OUT_R(RoadBlocks.road_excl_zone_split_out_r),
		ROAD_LINE_YELLOW(RoadBlocks.road_line_yellow), ROAD_LINE_SIMPLE_YELLOW(RoadBlocks.road_line_simple_yellow),
		ROAD_LINE_MERGE_YELLOW(RoadBlocks.road_line_merge_yellow),
		ROAD_LINE_DIAGONAL_YELLOW(RoadBlocks.road_line_diagonal_yellow),
		ROAD_WHITE_QUARTER_YELLOW(RoadBlocks.road_white_quarter_yellow),
		ROAD_WHITE_HALF_YELLOW(RoadBlocks.road_white_half_yellow),
		ROAD_EXCL_ZONE_SPLIT_IN_L_YELLOW(RoadBlocks.road_excl_zone_split_in_l_yellow),
		ROAD_EXCL_ZONE_SPLIT_IN_R_YELLOW(RoadBlocks.road_excl_zone_split_in_r_yellow),
		ROAD_EXCL_ZONE_SPLIT_OUT_L_YELLOW(RoadBlocks.road_excl_zone_split_out_l_yellow),
		ROAD_EXCL_ZONE_SPLIT_OUT_R_YELLOW(RoadBlocks.road_excl_zone_split_out_r_yellow);

		private final Block[] blocks;

		connectableBlocks(Block... block) {
			this.blocks = block;
		}

		private Block[] blocks() {
			return blocks;
		}

		public String getName() {
			return blocks()[0].toString();
		}
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies
	 * properties not visible in the metadata, such as fence connections.
	 */
	@Override
	public IExtendedBlockState getExtendedState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

		IExtendedBlockState extendedState = super.getExtendedState(state, worldIn, pos);

		return (IExtendedBlockState) extendedState
				.withProperty(NORTH, canConnectInDirection(worldIn, extendedState, pos, EnumFacing.NORTH))
				.withProperty(EAST, canConnectInDirection(worldIn, extendedState, pos, EnumFacing.EAST))
				.withProperty(SOUTH, canConnectInDirection(worldIn, extendedState, pos, EnumFacing.SOUTH))
				.withProperty(WEST, canConnectInDirection(worldIn, extendedState, pos, EnumFacing.WEST))
				.withProperty(TEXTURE_ROTATION, EnumFacing.SOUTH); // TEXTURE_ROTATION set to avoid exceptions, but
																	// unused
	}

	private  SlopeState getSlopeState(IBlockAccess worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		if (state.getBlock() instanceof BlockRoad) {
			return shouldBeSlope(worldIn, state, pos);
		}
		return null;
	}

	private  boolean canConnectInDirection(IBlockAccess worldIn, IExtendedBlockState state, BlockPos pos,
			EnumFacing direction) {

		SlopeState slope = shouldBeSlope(worldIn, state, pos);
		
		BlockPos testNeighbor = pos.offset(direction).up();
		SlopeState testState = getSlopeState(worldIn, testNeighbor);
		if (canConnectTo(testState) && getHeight() == 1 + testState.getBottomPosition()) {
			return testState.isSlope() && testState.getFacing() == direction.getOpposite()
					&& (!slope.isSlope() || slope.getFacing() == testState.getFacing());
		}

		testNeighbor = pos.offset(direction);
		testState = getSlopeState(worldIn, testNeighbor);
		if (canConnectTo(testState)) {

			if (slope.isSlope()) {
				if (direction == slope.getFacing()) { // bottom of the slope
					if (testState.getBlock().getHeight() == slope.getBottomPosition()) {
						// slope stops or continues
						return !testState.isSlope() || testState.getFacing() == slope.getFacing();
					} else if (testState.getBottomPosition() == slope.getBottomPosition()) {
						// slope inverts
						return testState.isSlope() && testState.getFacing() == slope.getFacing().getOpposite();
					} else {
						return false;
					}
				} else if (direction == slope.getFacing().getOpposite()) { // top of the slope
					if (testState.getBlock().getHeight() == getHeight()) {
						// slope stops or inverts
						return !testState.isSlope() || testState.getFacing() == slope.getFacing().getOpposite();
					} else if (testState.getBottomPosition() == getHeight()) {
						// slope continues
						return testState.isSlope() && testState.getFacing() == slope.getFacing();
					} else {
						return false;
					}
				} else {
					return slope.equals(testState); // sides of the slope
				}
			} else {
				System.out.println("bottom" + String.valueOf(testState.getBottomPosition()));
				System.out.println("height" + String.valueOf(getHeight()));
				System.out.println("direction" + direction);
				if (testState.isSlope()) {
					System.out.println("facing" + testState.getFacing());
				}
				if (testState.isSlope() && testState.getFacing() != direction) {
					if (testState.getFacing() == direction.getOpposite()) {
						return testState.getBottomPosition() == getHeight();
					} else {
						return false;
					}
				} else {
					return testState.getBlock().getHeight() == getHeight();
				}
			}

		}

		if (slope.isSlope() && slope.getFacing() == direction) {
			testNeighbor = pos.offset(direction).down();
			testState = getSlopeState(worldIn, testNeighbor);
			if (canConnectTo(testState)) {
				if (testState.isSlope() && testState.getFacing() != direction) {
					return false;
				}
				return testState.getBlock().getHeight() == 1 + slope.getBottomPosition();
			}
		}

		return false;
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 */
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		if (!(state instanceof IExtendedBlockState)) {
			return state;
		}
		IExtendedBlockState extendedState = (IExtendedBlockState) state;

		switch (rot) {
		case CLOCKWISE_180:
			return extendedState.withProperty(NORTH, extendedState.getValue(SOUTH))
					.withProperty(EAST, extendedState.getValue(WEST)).withProperty(SOUTH, extendedState.getValue(NORTH))
					.withProperty(WEST, extendedState.getValue(EAST));
		case COUNTERCLOCKWISE_90:
			return extendedState.withProperty(NORTH, extendedState.getValue(EAST))
					.withProperty(EAST, extendedState.getValue(SOUTH)).withProperty(SOUTH, extendedState.getValue(WEST))
					.withProperty(WEST, extendedState.getValue(NORTH));
		case CLOCKWISE_90:
			return extendedState.withProperty(NORTH, extendedState.getValue(WEST))
					.withProperty(EAST, extendedState.getValue(NORTH)).withProperty(SOUTH, extendedState.getValue(EAST))
					.withProperty(WEST, extendedState.getValue(SOUTH));
		default:
			return state;
		}
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		if (!(state instanceof IExtendedBlockState)) {
			return state;
		}
		IExtendedBlockState extendedState = (IExtendedBlockState) state;

		switch (mirrorIn) {
		case LEFT_RIGHT:
			return extendedState.withProperty(NORTH, extendedState.getValue(SOUTH)).withProperty(SOUTH,
					extendedState.getValue(NORTH));
		case FRONT_BACK:
			return extendedState.withProperty(EAST, extendedState.getValue(WEST)).withProperty(WEST,
					extendedState.getValue(EAST));
		default:
			return super.withMirror(state, mirrorIn);
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer.Builder(this).add(FACING, TEXTURE_ROTATION)
				.add(SLOPE, SLOPE_BOTTOM, HEIGHT, NORTH, EAST, WEST, SOUTH).build();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return false;

	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex();
	}

	/*
	 * @Override public boolean shouldSideBeRendered(IBlockState blockState,
	 * IBlockAccess blockAccess, BlockPos pos, EnumFacing side) { return true; }
	 */

}