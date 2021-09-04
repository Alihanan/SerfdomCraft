package pl.kalibrov.main.client.camera;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemBow;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import pl.kalibrov.main.client.ClientProxy;
import pl.kalibrov.main.customStructures.objects.support.SitEntity;

public class CameraFightMode {
	
	// Entity Renderer - mainly for translated selection change
	public static CameraFMRenderer rcr;
	
	// Anti-translate fight mode camera (GL translate)
	private static float bowToThirdPerson = 0.0f;
	// Current camera distance 
	private static float bowState = 0.0f; 
	// Maximum camera distance
	private static float baseFOV = 30.0f; // 15.0f
	
	
	/**
	 * This event calculates transition of x-y coord
	 */
	public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
		/**
		 * First check if our custom entity renderer is used
		 * As stated above - to calculate translated block selection
		 */
		if(rcr == null || 
				!(ClientProxy.m.entityRenderer instanceof CameraFMRenderer)) {
			rcr = new CameraFMRenderer(Minecraft.getMinecraft());
			ClientProxy.m.entityRenderer = rcr;	
			//rcr.state = 0;
			//modelRenderer = new RPGPlayerRenderer(m.getRenderManager());
		}
		/**
		 * Tent -- turn ON first person mode
		 */
		
		if(event.getEntity().isRiding() && event.getEntity().getRidingEntity() instanceof SitEntity) {
			ClientProxy.m.gameSettings.thirdPersonView = 1;
			//event.getEntity().rotationYaw = 0.0f;
			//event.getEntity().rotationPitch = 0.0f;			
			GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
			GlStateManager.translate(0.0f, -4.0f, 0.0f);
			return;
		}
		
		/**
		 * Every translation here is calculated IN CAMERA COORDINATES!!! +x  ....  -x
		 */			
		if(event.getEntity().equals(ClientProxy.m.player) && 
				ClientProxy.m.player.getHeldItemMainhand() != null && 
				ClientProxy.m.player.getHeldItemMainhand().getItem().getClass().equals(ItemBow.class) && 
				ClientProxy.m.player.isHandActive()
				) {
			bowToThirdPerson += 0.025f;
			if(bowToThirdPerson > 0.7f * 5 / 6) bowToThirdPerson = 0.7f * 5 / 6;
		}
		else {
			bowToThirdPerson -= 0.025f;
			if(bowToThirdPerson < 0.0f) bowToThirdPerson = 0.0f;
		}
		
		if(bowToThirdPerson < 0.7f * 5 / 6) {
			ClientProxy.m.gameSettings.thirdPersonView = 1;
			GlStateManager.translate(-0.7f + bowToThirdPerson, 0.0F, bowToThirdPerson * baseFOV / 4.0f);
		}else {
			ClientProxy.m.gameSettings.thirdPersonView = 0;
		}
	}
	/**
	 * This event calculates transition of z coord = FOV of camera
	 */
	public void onEntityRenderEnd(EntityViewRenderEvent.FOVModifier event) {
		//event.setFOV(baseFOV + 5.0f);
		/*
		if(event.getEntity().equals(ClientProxy.m.player) && 
				ClientProxy.m.player.getHeldItemMainhand() != null && 
				ClientProxy.m.player.getHeldItemMainhand().getItem().getClass().equals(ItemBow.class) && 
				ClientProxy.m.player.isHandActive()) {
			
			bowState += 0.1f;
			if(bowState > 5.0f) bowState = 5.0f;
			
		}else {
			bowState -= 0.1f;
			if(bowState < 0.0f) bowState = 0.0f;
		}
		event.setFOV(baseFOV + bowState);	*/
	}
}
