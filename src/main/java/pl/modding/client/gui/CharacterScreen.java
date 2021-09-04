package pl.modding.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mojang.authlib.GameProfile;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.util.FakePlayerFactory;
import pl.modding.reference.Reference;

public class CharacterScreen extends GuiScreen{
	
	ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/gui/book.png");
	ResourceLocation ptexture = new ResourceLocation(Reference.MODID, "textures/gui/test_skin.png");
	int guiWidth = 175;
    int guiHeight = 228;
    
    GuiButton exitButton;
    final int EXITBUTTON = 0;
    long currTime = -1;
    
    
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if ( currTime == -1) {
			currTime = System.currentTimeMillis();
		}
		
		drawDefaultBackground();
		
		int startX = (width / 2) - guiWidth/2;
		int startY = (height / 2) - guiHeight/2;
		
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(startX, startY, 0, 0, guiWidth, guiHeight);
		
		drawString(fontRenderer, "Tutorial", startX, startY, 0x6028ff);
		
		startX = (width / 2) - 8;
		startY = (height / 2) - 8;
		
		ItemStack is = new ItemStack(Item.getItemById(278));
		is.addEnchantment(Enchantment.getEnchantmentByID(16), 2);
		mc.getRenderItem().renderItemAndEffectIntoGUI(is, startX, startY);
		
		
		//EntityVillager villager = new EntityVillager(Minecraft.getMinecraft().world);
		EntityPlayer player = Minecraft.getMinecraft().player;
		//EntityPlayer copy = player.getEntity
		long curr = System.currentTimeMillis();
        int time = (int) (curr - currTime) / 4;
		//System.out.println(time);
        float rotateAngle = (float)(int) (time % 360);
        
		GlStateManager.pushMatrix();
        {        	
        	GlStateManager.translate(width / 2 - 50, height / 2 - 20, 0);    
            GlStateManager.rotate(180, 0, 0, 1);
            GlStateManager.rotate(rotateAngle, 0, 1, 0);
            GlStateManager.scale(40, 40, 40);
            //Minecraft.getMinecraft().getRenderManager().renderEntity(villager, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
            
            mc.getRenderManager().renderEntity(player, 0, 0, 0, 0, 0.0f, false);
        }
        GlStateManager.popMatrix();
		
		
		//net.minecraft.client.gui.inventory.GuiInventory
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		if(isBetween(mouseX, width/2 - 50, width/2 + 50) && 
				isBetween(mouseY, height/2 + guiHeight/2 - 25, height/2 + guiHeight/2 - 5)) {
			List<String> tooltip = new ArrayList<String>();
			tooltip.add(TextFormatting.GREEN + "" + TextFormatting.BOLD + "Подсказка для тупых");
			tooltip.add(TextFormatting.GRAY + "" + TextFormatting.ITALIC + "Подсказка для вумных");
			
			drawHoveringText(tooltip, mouseX, mouseY);
		}
		
		
	}
	/*
	private static void renderInventoryStyle() {
		float oldMouseX = (float)mouseX;
        float oldMouseY = (float)mouseY;
        int posX = (width/2 - 50);
        int posY = (height/2 - 10);
        
		drawEntityOnScreen(posX + 51, posY + 75, 30, (float)(posX + 51) - oldMouseX, (float)(posY + 75 - 50) - oldMouseY, villager);
	}
	
	
	public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
	*/
	
	
	@Override
	public void initGui() {
		/**
		 * When initialized or window resized
		 */
		Minecraft.getMinecraft().setRenderViewEntity(Minecraft.getMinecraft().player);
		buttonList.clear();
		initExitButton();
		super.initGui();
	}
	private void initExitButton() {
		int xStart = width/2 - 50;
		int yStart = height/2 + guiHeight/2 - 25;
		exitButton = new GuiButton(EXITBUTTON, xStart, yStart, 100, 20, "Close editor");
		buttonList.add(exitButton);
	}
	
	
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		/**
		 * Button press
		 */
		switch(button.id) {
			case EXITBUTTON:
				Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
				break;
			default:
				break;
		}
		
		
		super.actionPerformed(button);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		/**
		 * For single player, False - no pause.
		 */
		return super.doesGuiPauseGame();
	}
	
	@Override
	public void onGuiClosed() {
		/**
		 * When gui closes
		 */
		Minecraft m = Minecraft.getMinecraft();
		m.gameSettings.thirdPersonView = 1;
		/*
		FlyingCamera pl = new FlyingCamera(m.world);
		//pl.setInvisible(true);
		m.world.spawnEntity(pl);
		pl.setPositionAndRotation(m.player.posX, m.player.posY, m.player.posZ, m.player.rotationYaw, m.player.rotationPitch);
		pl.prevPosX = m.player.prevPosX;
		pl.prevPosY = m.player.prevPosY;
		pl.prevPosZ = m.player.prevPosZ;
		m.setRenderViewEntity(pl);*/
		
		
		super.onGuiClosed();
	}
	
	
	private boolean isBetween(int value, int min, int max) {
		return (value >= min) && (value <= max);
	}
	
}
