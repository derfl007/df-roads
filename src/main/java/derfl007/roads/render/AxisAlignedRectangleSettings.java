package derfl007.roads.render;

import net.minecraft.util.EnumFacing;

public class AxisAlignedRectangleSettings {

	public final double offset;

	public final double left;
	public final double bottom;
	public final double width;
	public final double height;

	private AxisAlignedRectangleSettings(double offset, double left, double bottom, double width, double height) {
		this.offset = offset;
		this.left = left;
		this.bottom = bottom;
		this.width = width;
		this.height = height;
	}

	public AxisAlignedRectangleSettings() {
		this(0D, 0D, 0D, 1D, 1D);
	}

	public AxisAlignedRectangleSettings setOffset(double offset) {
		return new AxisAlignedRectangleSettings(offset, left, bottom, width, height);
	}

	public AxisAlignedRectangleSettings setLeft(double left) {
		return new AxisAlignedRectangleSettings(offset, left, bottom, width, height);
	}

	public AxisAlignedRectangleSettings setBottom(double bottom) {
		return new AxisAlignedRectangleSettings(offset, left, bottom, width, height);
	}

	public AxisAlignedRectangleSettings setWidth(double width) {
		return new AxisAlignedRectangleSettings(offset, left, bottom, width, height);
	}

	public AxisAlignedRectangleSettings setHeight(double height) {
		return new AxisAlignedRectangleSettings(offset, left, bottom, width, height);
	}
}
