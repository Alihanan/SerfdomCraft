package pl.kalibrov.main.client.camera;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.ForgeHooks;
import pl.kalibrov.main.gui.inventory.RPGInventoryGui;

public class CameraFMRenderer extends EntityRenderer{
	private final Minecraft mc;
	//public int state = 0;
	
	public static final double scaleOffset = 0.7f; //0.7f
	public static double xRenderOffset = 0.0f;
	public static double zRenderOffset = 0.0f;
	public static double cameraSide = 1.0f;
	
	
	public static Entity origPointedEntity = null;
	
	public CameraFMRenderer(Minecraft mc) {
		super(mc, mc.getResourceManager());
		this.mc = mc;
	}

	public void renderWorld(float partialTicks, long finishTimeNano) {
		super.renderWorld(partialTicks, finishTimeNano);
	}
	
	@Override
	public void updateCameraAndRender(float partialTick, long nanoTime) {		
		super.updateCameraAndRender(partialTick, nanoTime);
	}
	
	
	@Override
	public void getMouseOver(float partialTick) {		
		super.getMouseOver(partialTick);
		origPointedEntity = mc.pointedEntity;
				
		if (mc.player == null || mc.player.isPlayerSleeping()){					
			return;
		}
		if ((Minecraft.getMinecraft().currentScreen instanceof RPGInventoryGui)) {
			return;
		}
		
		
		EntityPlayer p = mc.player;
		RayTraceResult raytrace = ForgeHooks.rayTraceEyes(p, 999);
		
		
		Vec3d vector = getEntityCameraTranslate(p);
		double xRenderOffset = vector.x;
		double zRenderOffset = vector.z;
		
		
		mc.player.posX -= xRenderOffset;
		mc.player.posZ -= zRenderOffset;
		mc.player.prevPosX = mc.player.lastTickPosX = mc.player.posX;
		mc.player.prevPosZ = mc.player.lastTickPosZ = mc.player.posZ;
		super.getMouseOver(partialTick);
		
		mc.player.posX += xRenderOffset;
		mc.player.posZ += zRenderOffset;
		mc.player.prevPosX = mc.player.lastTickPosX = mc.player.posX;
		mc.player.prevPosZ = mc.player.lastTickPosZ = mc.player.posZ;
		
		// TODO change player look direction (see github for previous attempts)
	}
	
	/**
	 * Calculate behind-right position of the camera in global coords
	 * e.g. take player look vector and some trigonometry
	 * @param player Entity of player with global position set
	 * @return Vector in the direction behind-right
	 */
	public static Vec3d getEntityCameraTranslate(EntityPlayer player) {
		float head = player.rotationYawHead;
		float body = player.rotationYaw;
		head = head - 90.0f;
		if (head > 180.0f) head -= 360.0f;
		if (head < -180.0f) head += 360.0f;
		
		double xOffset = -Math.sin(Math.toRadians(head));
		double zOffset = Math.cos(Math.toRadians(head));
		
		return new Vec3d(xOffset * scaleOffset, 0.0f, zOffset * scaleOffset);

	}
}
