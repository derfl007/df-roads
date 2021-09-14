package derfl007.roads.common.blocks;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import derfl007.roads.Reference;
import derfl007.roads.Roads;
import derfl007.roads.common.items.ItemWrench;
import derfl007.roads.common.util.UnlistedPropertyBoolean;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.PropertyFloat;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockRoad extends BlockHorizontal {

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			tooltip.add(I18n.format("block.road_rotatable.tooltip"));
		} else {
			tooltip.add(I18n.format("gui.tooltip.view_more"));
		}
	}

	public class RoadItemBlock extends ItemBlock {
		public RoadItemBlock() {
			super(BlockRoad.this);
		}

		@Override
		public String getItemStackDisplayName(ItemStack stack) {
			return super.getItemStackDisplayName(stack) + " "
					+ getLocalizedString("dfroads.partial.road_height", BlockRoad.this.getHeight());
		}

		private String getLocalizedString(String translationKey, Object... args) {
			return new TextComponentTranslation(translationKey, args).getFormattedText();
		}

	}

	public RoadItemBlock createItemBlock() {
		return new RoadItemBlock();
	}

	private static final double HEIGHT_0 = 0.25;
	private static final double HEIGHT_1 = 0.5;
	private static final double HEIGHT_2 = 0.75;
	private static final double HEIGHT_3 = 1;

	public static Set<String> getRegisteredTextures() {
		return Collections.unmodifiableSet(REGISTERED_TEXTURES);
	}

	public static Set<String> getRegisteredBlocks() {
		return Collections.unmodifiableSet(REGISTERED_BLOCKS);
	}

	/**
	 * Make sure you call it on the correct BlockRoad instance.
	 * 
	 * @return
	 */
	public double getHeight() {
		switch (blockHeight) {
		case 0:
			return HEIGHT_0;
		case 1:
			return HEIGHT_1;
		case 2:
			return HEIGHT_2;
		default:
			return HEIGHT_3;
		}
	}

	private static final AxisAlignedBB BB_HEIGHT_0 = new AxisAlignedBB(0, 0, 0, 1, HEIGHT_0, 1);
	private static final AxisAlignedBB BB_HEIGHT_1 = new AxisAlignedBB(0, 0, 0, 1, HEIGHT_1, 1);
	private static final AxisAlignedBB BB_HEIGHT_2 = new AxisAlignedBB(0, 0, 0, 1, HEIGHT_2, 1);
	private static final AxisAlignedBB BB_HEIGHT_3 = new AxisAlignedBB(0, 0, 0, 1, HEIGHT_3, 1);

	private final int blockHeight;
	private final String texturePath;
	protected final String baseName;

	private static final Set<String> REGISTERED_BLOCKS = new HashSet<>();
	private static final Set<String> REGISTERED_TEXTURES = new HashSet<>();

	public static final PropertyDirection TEXTURE_ROTATION = PropertyDirection.create("slope_facing",
			EnumFacing.Plane.HORIZONTAL);

	public static final PropertyFloat HEIGHT = new PropertyFloat("height", 0F, 1F);
	public static final UnlistedPropertyBoolean SLOPE = new UnlistedPropertyBoolean("slope");
	public static final PropertyFloat SLOPE_BOTTOM = new PropertyFloat("bottom", -0.75F, 0.75F);

	protected BlockRoad(String name, String texture, int height, boolean yellow, boolean register) {
		super(Material.ROCK);
		this.setUnlocalizedName(name);

		this.baseName = name;

		String registryName = name;
		if (height != 2) {
			registryName += "_" + String.valueOf(height);
		}

		this.setRegistryName(registryName);
		if (yellow) {
			this.setCreativeTab(Roads.YELLOW_ROADS_TAB);
		} else {
			this.setCreativeTab(Roads.ROADS_TAB);
		}
		this.setHardness(1.5F);

		if (height < 0 || height > 3) {
			throw new IllegalArgumentException();
		}

		blockHeight = height;
		texturePath = texture;

		if (register) {
			REGISTERED_TEXTURES.add(texture);
			REGISTERED_BLOCKS.add(registryName);
		}

		// TODO Auto-generated constructor stub
	}

	protected void registerTexture(String texture) {
		REGISTERED_TEXTURES.add(texture);
	}

	public static void registerRecipes(Block[] blocks) {

		// Reduce height

		// three 3/4 = nine 1/4
		ResourceLocation recipeName = new ResourceLocation(Reference.MOD_ID,
				blocks[0].getRegistryName().getResourcePath() + "_0");
		GameRegistry.addShapedRecipe(recipeName, null, new ItemStack(blocks[0], 9), "XXX", 'X', blocks[2]);

		// two 1/2 = four 1/4
		recipeName = new ResourceLocation(Reference.MOD_ID, blocks[0].getRegistryName().getResourcePath() + "_1");
		GameRegistry.addShapedRecipe(recipeName, null, new ItemStack(blocks[0], 4), "XX", 'X', blocks[1]);

		// two 1 = four 1/2
		recipeName = new ResourceLocation(Reference.MOD_ID, blocks[1].getRegistryName().getResourcePath() + "_0");
		GameRegistry.addShapedRecipe(recipeName, null, new ItemStack(blocks[1], 4), "XX", 'X', blocks[3]);

		// Increase height

		// two 1/4 = one 1/2
		recipeName = new ResourceLocation(Reference.MOD_ID, blocks[1].getRegistryName().getResourcePath() + "_1");
		GameRegistry.addShapedRecipe(recipeName, null, new ItemStack(blocks[1]), "X", "X", 'X', blocks[0]);

		// three 1/4 = one 3/4
		recipeName = new ResourceLocation(Reference.MOD_ID, blocks[2].getRegistryName().getResourcePath() + "_1");
		GameRegistry.addShapedRecipe(recipeName, null, new ItemStack(blocks[2]), "X", "X", "X", 'X', blocks[0]);

		// two 1/2 = one 1
		recipeName = new ResourceLocation(Reference.MOD_ID, blocks[3].getRegistryName().getResourcePath());
		GameRegistry.addShapedRecipe(recipeName, null, new ItemStack(blocks[3]), "X", "X", 'X', blocks[1]);
	}

	private static Block[] create(String name, String texture, boolean yellow) {
		return new Block[] { new BlockRoad(name, texture, 0, yellow, true),
				new BlockRoad(name, texture, 1, yellow, true), new BlockRoad(name, texture, 2, yellow, true),
				new BlockRoad(name, texture, 3, yellow, true) };
	}

	public static Block[] create(String name, String texture) {
		return create(name, texture, false);
	}

	public static Block[] createYellow(String name, String texture) {
		return create(name, texture, true);
	}

	public static Block[] create(String name) {
		return create(name, name, false);
	}

	public static Block[] createYellow(String name) {
		return create(name, name, true);
	}

	protected final String getTexturePath() {
		return texturePath;
	}

	protected final TextureAtlasSprite getTexture(String name,
			Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
		return textureGetter.apply(new ResourceLocation(Reference.MOD_ID, "blocks/" + name));
	}

	// state is used by subclasses in overridden methods
	public TextureAtlasSprite getTextureSprite(@Nullable IBlockState state,
			Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
		return getTexture(this.texturePath, textureGetter);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);

		EnumFacing blockFacing = placer.getHorizontalFacing().getOpposite();

		state = state.withProperty(FACING, blockFacing);

		return state;
	}

	@Override
	public final boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public final boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public final boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return getBoxFromState((IExtendedBlockState) state, source, pos);
	}

	private AxisAlignedBB getBoxFromState(IExtendedBlockState state, IBlockAccess world, BlockPos pos) {

		AxisAlignedBB bb;

		switch (blockHeight) {
		case 0:
			bb = BB_HEIGHT_0;
			break;
		case 1:
			bb = BB_HEIGHT_1;
			break;
		case 2:
			bb = BB_HEIGHT_2;
			break;
		case 3:
			bb = BB_HEIGHT_3;
			break;
		default:
			return FULL_BLOCK_AABB;
		}

		SlopeState slope = shouldBeSlope(world, state, pos);
		if (slope.isSlope() && slope.getBottomPosition() < 0D) {
			bb = bb.expand(0, slope.getBottomPosition(), 0);
		}
		
		return bb;

	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		SlopeState slope = shouldBeSlope(worldIn, state, pos);
		float height = (float) getHeight();

		if (slope.isSlope()) {
			EnumFacing facing = state.getValue(FACING);

			if (facing.getAxis() == Axis.X) { // EAST or WEST
				for (int i = 0; i <= 9; i++) {
					double x1 = i / 10D;
					double x2 = (i + 1) / 10D;

					addCollisionBoxToList(pos, entityBox, collidingBoxes,
							new AxisAlignedBB(x1, slope.getBottomPosition(), 0, x2, getHeightAtPosition(facing, height,
									slope.isSlope(), slope.getBottomPosition(), (x1 + x2) / 2D, 0), 1));
				}
			}
			if (facing.getAxis() == Axis.Z) { // SOUTH or NORTH
				for (int i = 0; i <= 9; i++) {
					double z1 = i / 10D;
					double z2 = (i + 1) / 10D;

					addCollisionBoxToList(pos, entityBox, collidingBoxes,
							new AxisAlignedBB(0, slope.getBottomPosition(), z1, 1, getHeightAtPosition(facing, height,
									slope.isSlope(), slope.getBottomPosition(), 0, (z1 + z2) / 2D), z2));
				}
			}

		} else {
			addCollisionBoxToList(pos, entityBox, collidingBoxes,
					getBoxFromState((IExtendedBlockState) state, worldIn, pos));
		}
	}

	protected static class SlopeState {
		private final boolean slope;
		private final float slopeBottom;

		private final EnumFacing facing;

		private final BlockRoad block;

		public boolean isSlope() {
			return slope;
		}

		public float getBottomPosition() {
			if (slope) {
				return slopeBottom;
			}
			return 0F;
		}

		public EnumFacing getFacing() {
			if (!slope) {
				throw new IllegalStateException("Not a slope");
			}
			return facing;
		}

		public BlockRoad getBlock() {
			return block;
		}

		public SlopeState(BlockRoad block, EnumFacing facing, boolean slope, float slopeBottom) {
			this.block = block;
			this.facing = facing;
			this.slope = slope;
			this.slopeBottom = slopeBottom;
		}

		@Override
		public boolean equals(Object other) {
			if (other instanceof SlopeState) {
				SlopeState slope = (SlopeState) other;

				if (!slope.slope && !this.slope) {
					return slope.block.getHeight() == block.getHeight();
				}

				return slope.facing == facing && slope.slope == this.slope && slope.slopeBottom == slopeBottom
						&& slope.block.getHeight() == block.getHeight();
			}
			return super.equals(other);
		}
	}

	protected static SlopeState shouldBeSlope(IBlockAccess worldIn, IBlockState state, BlockPos pos) {
		Block block_under = worldIn.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock();
		Block block_facing = worldIn.getBlockState(pos.offset(state.getValue(FACING))).getBlock();

		double height = ((BlockRoad) state.getBlock()).getHeight();

		if (block_facing instanceof BlockRoad && ((BlockRoad) block_facing).getHeight() < height) {
			return new SlopeState((BlockRoad) state.getBlock(), state.getValue(FACING), true,
					(float) ((BlockRoad) block_facing).getHeight());
		}
		if (block_under instanceof BlockRoad && height <= ((BlockRoad) block_under).getHeight()) {
			return new SlopeState((BlockRoad) state.getBlock(), state.getValue(FACING), true,
					(float) ((BlockRoad) block_under).getHeight() - 1);
		}
		return new SlopeState((BlockRoad) state.getBlock(), state.getValue(FACING), false, 0F);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		int facing = meta / 4;
		int slopeFacing = meta % 4;

		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(facing))
				.withProperty(TEXTURE_ROTATION, EnumFacing.getHorizontal(slopeFacing));

	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int facing = state.getValue(FACING).getHorizontalIndex();
		int slopeFacing = state.getValue(TEXTURE_ROTATION).getHorizontalIndex();
		return 4 * facing + slopeFacing;

	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer.Builder(this).add(FACING, TEXTURE_ROTATION).add(SLOPE, SLOPE_BOTTOM, HEIGHT)
				.build();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		}

		if (playerIn.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemWrench) {
			EnumFacing slopeFacing = state.getValue(TEXTURE_ROTATION);

			worldIn.setBlockState(pos, state.withProperty(TEXTURE_ROTATION, slopeFacing.rotateY()));
			return true;

		}

		return false;
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}

	@Override
	public IExtendedBlockState getExtendedState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

		state = super.getExtendedState(state, worldIn, pos);

		SlopeState slope = shouldBeSlope(worldIn, state, pos);

		return ((IExtendedBlockState) state).withProperty(SLOPE, slope.isSlope())
				.withProperty(SLOPE_BOTTOM, slope.isSlope() ? slope.getBottomPosition() : 0F)
				.withProperty(HEIGHT, (float) getHeight());
	}

	public double getHeightAtPosition(EnumFacing facing, float height, boolean slope, float slopeBottom, double x,
			double z) {

		if (slope) {

			double baseHeight = slopeBottom;
			if (facing == EnumFacing.NORTH) {
				baseHeight += z * (height - slopeBottom);
			}
			if (facing == EnumFacing.SOUTH) {
				baseHeight += (1 - z) * (height - slopeBottom);
			}
			if (facing == EnumFacing.EAST) {
				baseHeight += (1 - x) * (height - slopeBottom);
			}
			if (facing == EnumFacing.WEST) {
				baseHeight += x * (height - slopeBottom);
			}

			return baseHeight;
		} else {
			return height;
		}

	}

}
