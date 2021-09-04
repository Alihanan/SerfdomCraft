package pl.modding.client.gui.camera;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import pl.modding.reference.Reference;

public class RPGPlayerRenderer extends RenderPlayer{
	
	final ResourceLocation ptexture = new ResourceLocation(Reference.MODID, "textures/gui/test_skin.png");	
	
	
	public RPGPlayerRenderer(RenderManager renderManager) {
		super(renderManager);
		this.bindTexture(ptexture);
	}
	
	public ResourceLocation getEntityTexture(AbstractClientPlayer acp) {
		return ptexture;
	}
	
	public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks) {
		float pit = entity.rotationPitch;
		float yaw = entity.rotationYaw;		
		
		
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		entity.rotationPitch = pit;
		entity.rotationYaw = yaw;
	}
	
	
}
