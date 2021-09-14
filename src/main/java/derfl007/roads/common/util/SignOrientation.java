package derfl007.roads.common.util;

import net.minecraft.util.EnumFacing;

public final class SignOrientation {

	/***
	 * @deprecated Utility class should not be instantiated.
	 */
	@Deprecated
	private SignOrientation() {
	}

	public static final int WEST = 4;
	public static final int NORTH = 8;
	public static final int EAST = 12;
	public static final int SOUTH = 0;

	// Strange order to keep facing compatibility with older versions.
	private static final int[] orientations = { SOUTH, WEST, NORTH, EAST, 1, 2, 3, 5, 6, 7, 9, 10, 11, 13, 14, 15 };
	private static final int[] metadata = { 0, 4, 5, 6, 1, 7, 8, 9, 2, 10, 11, 12, 3, 13, 14, 15 };

	public static int toMetadata(int rotation) {

		if (rotation < 0 || rotation > 15) {
			throw new IllegalArgumentException();
		}

		return metadata[rotation];

	}

	public static int fromMetadata(int metadata) {

		if (metadata < 0 || metadata > 15) {
			return SOUTH;
		}
		return orientations[metadata];
	}

	public static float toAngle(int rotation) {
		return -22.5F * rotation;
	}

	public static EnumFacing toFacing(int rotation) {
		switch (rotation) {
		case 0:
		case 1:
		case 2:
		case 15:
			return EnumFacing.SOUTH;

		case 3:
		case 4:
		case 5:
		case 6:
			return EnumFacing.EAST;

		case 7:
		case 8:
		case 9:
		case 10:
			return EnumFacing.NORTH;

		case 11:
		case 12:
		case 13:
		case 14:
			return EnumFacing.WEST;
		}

		throw new IllegalArgumentException();
	}

	public static boolean intersects(int rotation, EnumFacing direction) {
		if (rotation < 0 || rotation > 15) {
			throw new IllegalArgumentException();
		}

		switch (direction) {
		case SOUTH:
			return rotation > EAST || rotation < WEST;

		case EAST:
			return rotation > NORTH;

		case NORTH:
			return rotation > WEST && rotation < EAST;

		case WEST:
			return rotation < NORTH;

		default:
			return false;
		}
	}

}
