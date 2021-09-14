package derfl007.roads.common.util;

import java.util.Optional;

import derfl007.roads.common.blocks.IBlockConnectable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public final class BlockStateUtil {

	public static IExtendedBlockState getExtendedState(IBlockConnectable block, IBlockState state, IBlockAccess worldIn,
			BlockPos pos) {
		return ((IExtendedBlockState) state).withProperty(IBlockConnectable.UP, block.canConnectTo(worldIn, state, pos, EnumFacing.UP))
				.withProperty(IBlockConnectable.DOWN, block.canConnectTo(worldIn, state, pos, EnumFacing.DOWN))
				.withProperty(IBlockConnectable.NORTH, block.canConnectTo(worldIn, state, pos, EnumFacing.NORTH))
				.withProperty(IBlockConnectable.SOUTH, block.canConnectTo(worldIn, state, pos, EnumFacing.SOUTH))
				.withProperty(IBlockConnectable.EAST, block.canConnectTo(worldIn, state, pos, EnumFacing.EAST))
				.withProperty(IBlockConnectable.WEST, block.canConnectTo(worldIn, state, pos, EnumFacing.WEST));
	}

	@Deprecated
	private BlockStateUtil() {

	}

	public static <T extends Comparable<T>> T optGetValue(IBlockState state, IProperty<T> property, T fallbackValue) {
		try {
			Comparable<?> value = state.getProperties().get(property);
			if (value == null) {
				return fallbackValue;
			}

			return property.getValueClass().cast(value);

		} catch (NullPointerException | IllegalArgumentException ex) {
			return fallbackValue;
		}
	}

	public static <T extends Comparable<T>> T optGetValue(IExtendedBlockState state, IUnlistedProperty<T> property,
			T fallbackValue) {
		try {
			Optional<?> value = state.getUnlistedProperties().get(property);
			if (value == null || !value.isPresent()) {
				return fallbackValue;
			}

			return property.getType().cast(value.get());

		} catch (NullPointerException | IllegalArgumentException ex) {
			return fallbackValue;
		}
	}

}
