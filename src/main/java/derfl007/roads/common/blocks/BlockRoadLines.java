package derfl007.roads.common.blocks;

import java.util.List;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import derfl007.roads.common.util.UnlistedPropertyEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
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

@SuppressWarnings("Duplicates")
public class BlockRoadLines extends BlockRoad {

	private final boolean yellow;

	private BlockRoadLines(String name, String textureBaseName, int height, boolean yellow) {
		super(name, textureBaseName, height, yellow, true);

		this.yellow = yellow;

		registerTexture(textureBaseName + "_inner_right_corner" + (yellow ? "_yellow" : ""));
		registerTexture(textureBaseName + "_inner_left_corner" + (yellow ? "_yellow" : ""));
		registerTexture(textureBaseName + "_outer_right_corner" + (yellow ? "_yellow" : ""));
		registerTexture(textureBaseName + "_outer_left_corner" + (yellow ? "_yellow" : ""));
	}

	public static Block[] create(String name, String textureBaseName) {
		return create(name, textureBaseName, false);
	}

	public static Block[] createYellow(String name, String textureBaseName) {
		return create(name, textureBaseName, true);
	}

	private static Block[] create(String name, String textureBaseName, boolean yellow) {
		return new Block[] { new BlockRoadLines(name, textureBaseName, 0, yellow),
				new BlockRoadLines(name, textureBaseName, 1, yellow),
				new BlockRoadLines(name, textureBaseName, 2, yellow),
				new BlockRoadLines(name, textureBaseName, 3, yellow) };
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
	public static Block[] createYellow(String name) {
		return BlockRoad.createYellow(name);
	}

	@Override
	public TextureAtlasSprite getTextureSprite(@Nullable IBlockState state,
			Function<ResourceLocation, TextureAtlasSprite> textureGetter) {

		String baseTexturePath = getTexturePath();

		if (state != null) {
			IExtendedBlockState extendedState = (IExtendedBlockState) state;

			System.out.println(extendedState.getValue(SHAPE));
			switch (extendedState.getValue(SHAPE)) {
			case INNER_RIGHT:
				baseTexturePath += "_inner_right_corner";
				break;
			case INNER_LEFT:
				baseTexturePath += "_inner_left_corner";
				break;
			case OUTER_RIGHT:
				baseTexturePath += "_outer_right_corner";
				break;
			case OUTER_LEFT:
				baseTexturePath += "_outer_left_corner";
				break;
			default:
				break;
			}
		}
		if (yellow) {
			baseTexturePath += "_yellow";
		}

		return getTexture(baseTexturePath, textureGetter);

	}

	private static final UnlistedPropertyEnum<BlockStairs.EnumShape> SHAPE = UnlistedPropertyEnum.create("shape",
			BlockStairs.EnumShape.class);

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			tooltip.add(I18n.format("block.road_lines.tooltip"));
		} else {
			tooltip.add(I18n.format("gui.tooltip.view_more"));
		}
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		IExtendedBlockState state = (IExtendedBlockState) super.getStateForPlacement(world, pos, facing, hitX, hitY,
				hitZ, meta, placer, hand);
		state = state.withProperty(SHAPE, BlockStairs.EnumShape.STRAIGHT);
		return state;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex();
	}

	@Override
	public IExtendedBlockState getExtendedState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

		IExtendedBlockState extendedState = super.getExtendedState(state, worldIn, pos);
		return extendedState.withProperty(SHAPE, getShape(state, worldIn, pos));
	}

	public static BlockStairs.EnumShape getShape(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		EnumFacing facing = state.getValue(FACING);
		BlockRoadLines current = (BlockRoadLines) state.getBlock();

		BlockPos right = pos.offset(facing.rotateYCCW());
		IBlockState rightBlockState = worldIn.getBlockState(right);
		if (rightBlockState.getBlock() instanceof BlockRoadLines
				&& ((BlockRoadLines) rightBlockState.getBlock()).baseName == current.baseName) {

			EnumFacing rightFacing = rightBlockState.getValue(FACING);
			if(rightFacing.getAxis() != facing.getAxis()) {
				if(rightFacing == facing.rotateYCCW()) {
					return BlockStairs.EnumShape.OUTER_LEFT;
				}else {
					return BlockStairs.EnumShape.OUTER_RIGHT;
				}
			}
		}
		
		BlockPos left = pos.offset(facing.rotateY());
		IBlockState leftBlockState = worldIn.getBlockState(left);
		if (leftBlockState.getBlock() instanceof BlockRoadLines
				&& ((BlockRoadLines) leftBlockState.getBlock()).baseName == current.baseName) {

			EnumFacing leftFacing = leftBlockState.getValue(FACING);
			if(leftFacing.getAxis() != facing.getAxis()) {
				if(leftFacing == facing.rotateY()) {
					return BlockStairs.EnumShape.INNER_RIGHT;
				}else {
					return BlockStairs.EnumShape.INNER_LEFT;
				}
			}
		}
		

			
		

		return BlockStairs.EnumShape.STRAIGHT;
	}

	@Override
	@SuppressWarnings({ "incomplete-switch", "deprecation" })
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		EnumFacing enumfacing = state.getValue(FACING);
		BlockStairs.EnumShape blockstairs_enumshape = ((IExtendedBlockState) state).getValue(SHAPE);

		IExtendedBlockState extendedState = ((IExtendedBlockState) state.withRotation(Rotation.CLOCKWISE_180));

		switch (mirrorIn) {
		case LEFT_RIGHT:

			if (enumfacing.getAxis() == EnumFacing.Axis.Z) {
				switch (blockstairs_enumshape) {
				case OUTER_LEFT:
					return extendedState.withProperty(SHAPE, BlockStairs.EnumShape.OUTER_RIGHT);
				case OUTER_RIGHT:
					return extendedState.withProperty(SHAPE, BlockStairs.EnumShape.OUTER_LEFT);
				case INNER_RIGHT:
					return extendedState.withProperty(SHAPE, BlockStairs.EnumShape.INNER_LEFT);
				case INNER_LEFT:
					return extendedState.withProperty(SHAPE, BlockStairs.EnumShape.INNER_RIGHT);
				default:
					return extendedState;
				}
			}

			break;
		case FRONT_BACK:

			if (enumfacing.getAxis() == EnumFacing.Axis.X) {
				switch (blockstairs_enumshape) {
				case OUTER_LEFT:
					return extendedState.withProperty(SHAPE, BlockStairs.EnumShape.OUTER_RIGHT);
				case OUTER_RIGHT:
					return extendedState.withProperty(SHAPE, BlockStairs.EnumShape.OUTER_LEFT);
				case INNER_RIGHT:
					return extendedState.withProperty(SHAPE, BlockStairs.EnumShape.INNER_RIGHT);
				case INNER_LEFT:
					return extendedState.withProperty(SHAPE, BlockStairs.EnumShape.INNER_LEFT);
				case STRAIGHT:
					return extendedState;
				}
			}
		}

		return super.withMirror(state, mirrorIn);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer.Builder(this).add(FACING, TEXTURE_ROTATION)
				.add(SLOPE, SLOPE_BOTTOM, HEIGHT, SHAPE).build();
	}

}
