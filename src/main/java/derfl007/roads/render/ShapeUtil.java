package derfl007.roads.render;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

public final class ShapeUtil {

	@Deprecated
	private ShapeUtil() {

	}

	/**
	 * Creates an axis-aligned rectangle with the given settings.
	 * 
	 * @param settings The settings of the rectangle. See overload for details.
	 * 
	 * @param sprite   The texture to be used with the quad.
	 * @param format   The vertex format to be associated with the quad.
	 * @return
	 */
	public static Quad createAxisAlignedRectangle(EnumFacing face, AxisAlignedRectangleSettings settings,
			TextureAtlasSprite sprite, VertexFormat format) {
		return createAxisAlignedRectangle(face, settings.offset, settings.left, settings.bottom, settings.width,
				settings.height, sprite, format);
	}

	/**
	 * Creates an axis-aligned rectangle parallel to the specified face.
	 * 
	 * @param face   The face the rectangle will texture.
	 * @param offset The offset (toward the inside of the cube) in relation to the
	 *               location of the outer corresponding face.
	 * @param left   The horizontal offset from the left edge of the face, assuming
	 *               the face is being looked at standing, from the outside of the
	 *               cube. If the face is UP (resp. DOWN), it is assumed to be
	 *               looked at lying, with the head facing north (resp. south).
	 * @param bottom The vertical offset from the *bottom* edge of the face,
	 *               assuming the face is being looked at standing, from the outside
	 *               of the cube. If the face is UP (resp. DOWN), it is assumed to
	 *               be looked at lying, with the head facing north (resp. south).
	 * @param width  The width of the rectangle. If the face is UP or DOWN, this is
	 *               the size along X axis.
	 * @param height The height of the rectangle. If the face is UP or DOWN, this is
	 *               the size along Z axis.
	 * @param sprite The texture to be used with the quad.
	 * @param format The vertex format to be associated with the quad.
	 * @return
	 */
	public static Quad createAxisAlignedRectangle(EnumFacing face, double offset, double left, double bottom,
			double width, double height, TextureAtlasSprite sprite, VertexFormat format) {
		Quad quad;

		switch (face) {
		case SOUTH:
			double zPos = 1 - offset;

			double xMin = left;
			double xMax = left + width;
			double yMin = bottom;
			double yMax = bottom + height;

			quad = new Quad(new Vec3d(xMin, yMax, zPos), new Vec3d(xMin, yMin, zPos), new Vec3d(xMax, yMin, zPos),
					new Vec3d(xMax, yMax, zPos), sprite, format);
			break;
		case WEST:
			double xPos = offset;

			double zMin = left;
			double zMax = left + width;
			yMin = bottom;
			yMax = bottom + height;

			quad = new Quad(new Vec3d(xPos, yMax, zMin), new Vec3d(xPos, yMin, zMin), new Vec3d(xPos, yMin, zMax),
					new Vec3d(xPos, yMax, zMax), sprite, format);
			break;
		case NORTH:
			zPos = offset;

			xMin = 1 - left;
			xMax = 1 - left - width;
			yMin = bottom;
			yMax = bottom + height;

			quad = new Quad(new Vec3d(xMin, yMax, zPos), new Vec3d(xMin, yMin, zPos), new Vec3d(xMax, yMin, zPos),
					new Vec3d(xMax, yMax, zPos), sprite, format);
			break;
		case EAST:
			xPos = 1 - offset;

			zMin = 1 - left;
			zMax = 1 - left - width;
			yMin = bottom;
			yMax = bottom + height;

			quad = new Quad(new Vec3d(xPos, yMax, zMin), new Vec3d(xPos, yMin, zMin), new Vec3d(xPos, yMin, zMax),
					new Vec3d(xPos, yMax, zMax), sprite, format);
			break;
		case UP:
			double yPos = 1 - offset;

			xMin = left;
			xMax = left + width;
			zMin = 1 - bottom;
			zMax = 1 - bottom - height;

			quad = new Quad(new Vec3d(xMin, yPos, zMax), new Vec3d(xMin, yPos, zMin), new Vec3d(xMax, yPos, zMin),
					new Vec3d(xMax, yPos, zMax), sprite, format);
			break;
		case DOWN:
			yPos = offset;

			xMin = left;
			xMax = left + width;
			zMin = bottom;
			zMax = bottom + height;

			quad = new Quad(new Vec3d(xMin, yPos, zMax), new Vec3d(xMin, yPos, zMin), new Vec3d(xMax, yPos, zMin),
					new Vec3d(xMax, yPos, zMax), sprite, format);
			break;
		default:
			throw new IllegalArgumentException();
		}

		quad.setUV(0, 0, width, height);

		return quad;

	}

}
