package pl.kalibrov.main.client.camera;

import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import pl.kalibrov.main.client.RPGClassPlayerSystem;

public class Camera {
	// Change default cursor in camera mode
	CameraCursor cameraCursor = new CameraCursor();
	CameraRPGClass cameraRPG = new CameraRPGClass();
	CameraFightMode cameraFM = new CameraFightMode();
	
	public void postInit(FMLPostInitializationEvent event) {		

	}
	
	public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
		cameraFM.onCameraSetup(event);
	}
	
	public void onEntityRenderEnd(EntityViewRenderEvent.FOVModifier event) {
		cameraFM.onEntityRenderEnd(event);
	}
	
	public void onPlayerRender(RenderPlayerEvent.Pre event, final RPGClassPlayerSystem subsystem) {
		cameraRPG.onPlayerRender(event, subsystem);
	}
	public void onGameOverLay(RenderGameOverlayEvent.Pre event) {
		cameraCursor.onGameOverLay(event);
	}
}
