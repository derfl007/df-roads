package derfl007.roads.render;

import java.awt.Color;

import javax.vecmath.Matrix3d;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;

public class Quad {

	private Vec3d vec1;
	private Vec3d vec2;
	private Vec3d vec3;
	private Vec3d vec4;

	private double x1 = 0F;
	private double x2 = 16F;
	private double y1 = 0F;
	private double y2 = 16F;

	private TextureAtlasSprite sprite;

	protected VertexFormat format;

	public Quad(Vec3d vec1, Vec3d vec2, Vec3d vec3, Vec3d vec4, TextureAtlasSprite sprite, VertexFormat format) {
		this.vec1 = vec1;
		this.vec2 = vec2;
		this.vec3 = vec3;
		this.vec4 = vec4;

		this.format = format;

		this.sprite = sprite;

	}

	// Adapted from mcjty's tutorial
	// https://wiki.mcjty.eu/modding/index.php?title=Render_Block_Baked_Model-1.12
	public BakedQuad bake() {
		Vec3d normal = vec3.subtract(vec2).crossProduct(vec1.subtract(vec2)).normalize();

		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
		builder.setTexture(sprite);

		putVertex(builder, normal, vec1.x, vec1.y, vec1.z, x1, y1, sprite, 0);
		putVertex(builder, normal, vec2.x, vec2.y, vec2.z, x1, y2, sprite, 0);
		putVertex(builder, normal, vec3.x, vec3.y, vec3.z, x2, y2, sprite, 0);
		putVertex(builder, normal, vec4.x, vec4.y, vec4.z, x2, y1, sprite, 0);

		return builder.build();
	}

	// Copied from mcjty's tutorial
	// https://wiki.mcjty.eu/modding/index.php?title=Render_Block_Baked_Model-1.12
	protected void putVertex(UnpackedBakedQuad.Builder builder, Vec3d normal, double x, double y, double z, double u,
			double v, TextureAtlasSprite sprite, int col) {
		for (int e = 0; e < format.getElementCount(); e++) {
			switch (format.getElement(e).getUsage()) {
			case POSITION:
				builder.put(e, (float) x, (float) y, (float) z, 1.0f);
				break;
			case COLOR:
				if (col == 0) {
					builder.put(e, 1.0f, 1.0f, 1.0f, 1.0f);
				} else {
					Color color = new Color(col);
					builder.put(e, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f,
							color.getAlpha() / 255.0f);
				}
				break;
			case UV:
				if (format.getElement(e).getIndex() == 0) {
					u = sprite.getInterpolatedU(u);
					v = sprite.getInterpolatedV(v);
					builder.put(e, (float) u, (float) v, 0f, 1f);
					break;
				}
			case NORMAL:
				builder.put(e, (float) normal.x, (float) normal.y, (float) normal.z, 0f);
				break;
			default:
				builder.put(e);
				break;
			}
		}
	}

	public void fillTexture() {
		setUV(0, 0, 1, 1);
	}

	public void setUV(double x1, double y1, double x2, double y2) {
		this.x1 = 16f * x1;
		this.y1 = 16f * y1;
		this.x2 = 16f * x2;
		this.y2 = 16f * y2;
	}

	public void rotateY(int angleDeg) {
		double angleRad = Math.toRadians(angleDeg);
		double cos = Math.cos(angleRad);
		double sin = Math.sin(angleRad);

		Matrix3d transformation = new Matrix3d(cos, 0D, sin,
												0D, 1D, 0D,
												-sin, 0D, cos);

		applyCenteredTransformation(transformation);
	}

	public void rotateX(int angleDeg) {
		double angleRad = Math.toRadians(angleDeg);
		double cos = Math.cos(angleRad);
		double sin = Math.sin(angleRad);

		Matrix3d transformation = new Matrix3d(1D, 0D, 0D,
											0D, cos, -sin, 
											0D, sin, cos);

		applyCenteredTransformation(transformation);
	}

	public void rotateZ(int angleDeg) {
		double angleRad = Math.toRadians(angleDeg);
		double cos = Math.cos(angleRad);
		double sin = Math.sin(angleRad);
		
	
		Matrix3d transformation = new Matrix3d(cos, -sin, 0D, 
												sin, cos, 0D, 
												0D, 0D, 1D);

		applyCenteredTransformation(transformation);
	}

	public void scale(double factor) {
		Matrix3d transformation = new Matrix3d(factor, 0D, 0D,
												0D, factor, 0D, 
												0D, 0D, factor);
	
		applyCenteredTransformation(transformation);
	}
	
	public void translate(double x, double y, double z) {

		vec1 = vec1.addVector(x,y,z);
		vec2 = vec2.addVector(x,y,z);
		vec3 = vec3.addVector(x,y,z);
		vec4 = vec4.addVector(x,y,z);
	}	

	private void applyCenteredTransformation(Matrix3d transformation) {
		vec1 = applyCenteredTransformation(vec1, transformation);
		vec2 = applyCenteredTransformation(vec2, transformation);
		vec3 = applyCenteredTransformation(vec3, transformation);
		vec4 = applyCenteredTransformation(vec4, transformation);
	}

	private Vec3d applyCenteredTransformation(Vec3d vector, Matrix3d transformation) {
		return transform(vector.subtract(0.5, 0.5, 0.5), transformation).addVector(0.5, 0.5, 0.5);
	}

	private Vec3d transform(Vec3d vector, Matrix3d transformation) {
		double[] coords = new double[3];

		for (int i = 0; i < 3; i++) {
			coords[i] = transformation.getElement(i, 0) * vector.x + transformation.getElement(i, 1) * vector.y
					+ transformation.getElement(i, 2) * vector.z;
		}

		return new Vec3d(coords[0], coords[1], coords[2]);
	}

	public void rotateTexture90Clockwise() {
		Vec3d vec = vec1;
		vec1 = vec4;
		vec4 = vec3;
		vec3 = vec2;
		vec2 = vec;
	}
	
	

}
