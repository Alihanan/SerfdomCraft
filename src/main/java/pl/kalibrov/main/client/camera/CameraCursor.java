package pl.kalibrov.main.client.camera;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pl.kalibrov.main.RPGMod;
import pl.kalibrov.main.client.ClientProxy;

public class CameraCursor extends Gui{
	private final ResourceLocation texture0 = new ResourceLocation(RPGMod.MODID, "textures/gui/cursor.png");
	
	public void onGameOverLay(RenderGameOverlayEvent.Pre event) {
		
		if(event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
			event.setCanceled(true);
			
			/**
			 * NOTE: For whatever reason .displayWidth returns true resolution,
			 * but drawTexturedModalRect places it in 2x coordinates - 
			 * e.g. [50, 60] is [25, 30] for modalRect
			 */
			int width = ClientProxy.m.displayWidth;
	        int height = ClientProxy.m.displayHeight;
	        GlStateManager.pushMatrix();
	        {
	            GlStateManager.enableAlpha();
	            GlStateManager.enableBlend();
	            GlStateManager.color(1.0f, 1.0f, 1.0f, 0.75f);
	            ClientProxy.m.renderEngine.bindTexture(texture0);
	            
	            int x = width/4;
	            int y = height/4;        
	            
	            drawTexturedModalRect(x - 8, y - 8, 0, 0, 16, 16);
	        }
	        GlStateManager.popMatrix();
		} 
	}
}
