package pl.kalibrov.main;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class MathAlikhan {
	
	public static float clampAngleInterval(float angle, float center, float radius)
	{
		// convert everything to <0, 360.0f>
		float angleNorm = normalizeAngleToCircle(angle);
		float centerNorm = normalizeAngleToCircle(center);
		
		// get two arcs as their lengths
		float leftArc = centerNorm - angleNorm;
		if(leftArc < 0.0f) {
			leftArc += 360.0f;
		}
		float rightArc = angleNorm - centerNorm;
		if(rightArc < 0.0f) {
			rightArc += 360.0f;
		}
		
		// if shortest of arcs is close - it is inside 
		if(Math.min(leftArc, rightArc) <= radius) {
			return angleNorm;
		}
		
		if(leftArc < rightArc) {
			return centerNorm - radius;
		}
		if(leftArc > rightArc) {
			return centerNorm + radius;
		}
		
		return angleNorm;
	}
	
	
	public static float getMinArc(float angle, float center, float radius) {
		float angleNorm = normalizeAngleToCircle(angle);
		float centerNorm = normalizeAngleToCircle(center);
		
		// get two arcs as their lengths
		float leftArc = centerNorm - angleNorm;
		if(leftArc < 0.0f) {
			leftArc += 360.0f;
		}
		float rightArc = angleNorm - centerNorm;
		if(rightArc < 0.0f) {
			rightArc += 360.0f;
		}
		
		// if shortest of arcs is close - it is inside 
		return Math.min(leftArc, rightArc);
	}
	
	
	public static float normalizeAngleToCircle(float angle) {
		float ret = angle;
		while(ret < 0.0f) {
			ret += 360.0f;
		}
		while(ret > 360.0f) {
			ret -= 360.0f;
		}
		return ret;
	}
	
	
	/**
	 * Rotate a vertex around a specific axis - euler gimbal
	 * @param input = vertex to be rotated 
	 * @param x_angle = rotation angle around x
	 * @param y_angle = rotation angle around y
	 * @param z_angle = rotation angle around z
	 * @return rotated vertex
	 */
	private static double rot = 0.0D;
	public static Vec3d rotateVertexAroundAxis(Vec3d input, double x_angle, double y_angle, double z_angle, boolean debug) {
		double x_ret = input.x;
		double y_ret = input.y;
		double z_ret = input.z;
		
		if(x_angle != 0.0D) {
			/*
			y_ret = Math.cos(Math.toRadians(x_angle)) * y_ret - Math.sin(Math.toRadians(x_angle)) * z_ret;
			z_ret = Math.sin(Math.toRadians(x_angle)) * y_ret + Math.cos(Math.toRadians(x_angle)) * z_ret;*/
		}
		
		if(y_angle != 0.0D) {
			if(debug) {
				System.out.println(x_ret + " = " + Math.cos(Math.toRadians(y_angle)) + "*" +  input.x + " - " + 
			Math.sin(Math.toRadians(y_angle)) + "*" + input.z);
			}
			double x_ret2 = Math.cos(Math.toRadians(y_angle)) * x_ret - Math.sin(Math.toRadians(y_angle)) * z_ret;
			double z_ret2 = Math.sin(Math.toRadians(y_angle)) * x_ret + Math.cos(Math.toRadians(y_angle)) * z_ret;
			x_ret = x_ret2;
			z_ret = z_ret2;
		}
		
		if(z_angle != 0.0D) {
			/*x_ret = Math.cos(Math.toRadians(z_angle)) * x_ret - Math.sin(Math.toRadians(z_angle)) * y_ret;
			y_ret = Math.sin(Math.toRadians(z_angle)) * x_ret + Math.cos(Math.toRadians(z_angle)) * y_ret;*/
		}
		
		
		return new Vec3d(x_ret, y_ret, z_ret);
	}
	
	/**
	 * Addition of two vec3d
	 * @param one = vector3d
	 * @param two = vector3d
	 * @return sum = vector3d of added x, y, z
	 */
	public static Vec3d add(Vec3d one, Vec3d two) {
		return new Vec3d(
				one.x + two.x,
				one.y + two.y, 
				one.z + two.z);
	}
	

	/**
	 * EASIER VERSION of rotation, face -> horizontal angle
	 * uses a coordinates sign/order change
	 * @param input = position to be rotated
	 * @param face = facing as angle representation
	 * @return rotated input
	 */
	public static Vec3d easyRotate(Vec3d input, EnumFacing face) {
		double x_ret = input.x;
		double y_ret = input.y;
		double z_ret = input.z;
		
		switch(face) {
			case NORTH:
				x_ret = -input.x;
				z_ret = -input.z;
				break;
			case EAST:
				x_ret = input.z;
				z_ret = -input.x;
				break;
			case WEST:
				x_ret = -input.z;
				z_ret = input.x;
				break;
			case SOUTH:
			default:
				break;
		}
		return new Vec3d(x_ret, y_ret, z_ret);
	}
	
	public static Vec3d translationOriginBlock(EnumFacing faceID) {		
		double x = 0.0D;
		double y = 0.0D;
		double z = 0.0D;
		
		
		switch(faceID) {
		case NORTH:
			Vec3i east = faceID.rotateYCCW().getDirectionVec();
			Vec3i north = faceID.getDirectionVec();
			x += east.getX() * 1.0D + north.getX() * 1.0D;
			z += east.getZ() * 1.0D + north.getZ() * 1.0D;
			break;
		case EAST:			
			Vec3i south = faceID.getOpposite().getDirectionVec();
			x += south.getX() * 1.0D;
			z += south.getZ() * 1.0D;
			break;
		case WEST:
			Vec3i north2 = faceID.rotateY().getDirectionVec();
			x += north2.getX() * 1.0D;
			z += north2.getZ() * 1.0D;
			break;
		case SOUTH:
		default:
			break;
		}

		return new Vec3d(x, 0.0D, z);
	}
	

}

