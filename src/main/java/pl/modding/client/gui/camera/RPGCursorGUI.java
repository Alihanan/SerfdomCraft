package pl.modding.client.gui.camera;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pl.modding.main.RPGMod;
import pl.modding.reference.Reference;

public class RPGCursorGUI extends Gui{
	private final ResourceLocation texture0 = new ResourceLocation(Reference.MODID, "textures/gui/cursor.png");
	public static int cursorXOffset = 0;//RPGMod.m.displayWidth * 7 / 10 / 2 / 4
	
	
	@SubscribeEvent
	public void RenderOverlay(RenderGameOverlayEvent event) {
		/**
		 * NOTE: For whatever reason .displayWidth returns true resolution,
		 * but drawTexturedModalRect places it in 2x coordinates - 
		 * e.g. [50, 60] is [25, 30] for modalRect
		 */
		if(event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
			int width = RPGMod.m.displayWidth;
	        int height = RPGMod.m.displayHeight;
	        GlStateManager.pushMatrix();
	        {
	            GlStateManager.enableAlpha();
	            GlStateManager.enableBlend();
	            GlStateManager.color(1.0f, 1.0f, 1.0f, 0.75f);
	            RPGMod.m.renderEngine.bindTexture(texture0);
	            
	            int x = width/4;
	            int y = height/4;
	            /*
	            if(RPGCameraRenderer.origPointedEntity != null) {
	            	Vec3d target = RPGCameraRenderer.origPointedEntity.getPositionEyes(0.0f);
	            	x = (int) ((target.x + 1.0f) * 0.5f * (double)(width/2));
		            y = (int) ((target.y + 1.0f) * 0.5f * (double)(height/2));
	            }  */          
	            
	            drawTexturedModalRect(x - 8, y - 8, 0, 0, 16, 16);
	        }
	        GlStateManager.popMatrix();
		}
		
        
	}
}
