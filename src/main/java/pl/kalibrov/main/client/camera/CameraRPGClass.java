package pl.kalibrov.main.client.camera;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import pl.kalibrov.main.client.ClientProxy;
import pl.kalibrov.main.client.RPGClassPlayerSystem;

public class CameraRPGClass {
	public CameraPlayerRenderer modelRenderer;
	
	
	public void onPlayerRender(RenderPlayerEvent.Pre event, final RPGClassPlayerSystem subsystem) {
		RenderPlayer rp = event.getRenderer();

		if (modelRenderer == null) {
			modelRenderer = new CameraPlayerRenderer(rp.getRenderManager());
		}

		if (event.getEntityPlayer().equals(ClientProxy.m.player)) {
			
			if(!(rp instanceof CameraPlayerRenderer)) {
				/*
				AbstractClientPlayer acp = (AbstractClientPlayer)(m.player);	
				acp.posZ += RPGCameraRenderer.xRenderOffset;
				acp.posX += RPGCameraRenderer.zRenderOffset;
				acp.prevPosX = acp.lastTickPosX = acp.posX;
				acp.prevPosY = acp.lastTickPosY = acp.posY;
				acp.prevPosZ = acp.lastTickPosZ = acp.posZ;
				event.setCanceled(true);*/
				/*
				m.setRenderViewEntity(acp);
				double xRenderOffset = 0.0f;
				double zRenderOffset = 0.0f;*/
				
				//!(Minecraft.getMinecraft().currentScreen instanceof CharacterScreen) &&
				/*
				if(
				   !(Minecraft.getMinecraft().currentScreen instanceof RPGInventoryGui)) {
					Vec3d vector = RPGCameraRenderer.getEntityCameraTranslate(acp);
					xRenderOffset = vector.x;
					zRenderOffset = vector.z;
				}*/
				//mc.setRenderViewEntity(acp);
				
				//modelRenderer.doRender(acp, -3.0f, 0.0f, 0.0f, event.getEntityPlayer().rotationYaw, 0.0f); 
				//event.getX() 
				//event.getY()
				//event.getZ()
				//- xRenderOffset
				//- zRenderOffset
				//modelRenderer.doRender(acp, acp.posX, acp.posY, acp.posZ, acp.rotationYaw, 0.0f); 
				//m.setRenderViewEntity(camera);
				//modelRenderer.doRender(acp, -xRenderOffset, 0.0f, -zRenderOffset, event.getEntityPlayer().rotationYaw, 0.0f); */
				//modelRenderer.doRender(acp, event.getX(),event.getY(), event.getZ(), event.getEntityPlayer().rotationYaw, 0.0f); 
			}
		}			
	}
}
