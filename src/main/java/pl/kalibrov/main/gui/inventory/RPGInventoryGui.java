package pl.kalibrov.main.gui.inventory;


import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import pl.kalibrov.main.RPGMod;

public class RPGInventoryGui extends GuiContainer{
	
	EntityPlayer player = null;
	int currTime = 0;
	
	public RPGInventoryGui(Container cont, InventoryPlayer inv) {
		super(cont);
		this.player = inv.player;
		this.xSize = 256;
		this.ySize = 166;
		//this.guiLeft = Main.m.displayWidth/4 - this.xSize/2;
		//this.guiTop = Main.m.displayHeight/4 - this.ySize/2;

	}
	public void initGui() {
		super.initGui();
		//player.openContainer = this.inventorySlots;
	}
	
	

	private final ResourceLocation itexture = new ResourceLocation(RPGMod.MODID, "textures/gui/inventory.png");
	

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        //GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(itexture);       
        
        GlStateManager.pushMatrix();
        {        	
        	GlStateManager.translate(0.0f, 0.0f, -30.0f);    
        	this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        	//this.drawTexturedModalRect(Main.m.displayWidth/4 - this.xSize/2, Main.m.displayHeight/4 - this.ySize/2, 0, 0, this.xSize, this.ySize);
        }
        GlStateManager.popMatrix();
        
        //drawEntityOnScreen(i + 51, j + 75, 30, (float)(i + 51) - this.oldMouseX, (float)(j + 75 - 50) - this.oldMouseY, this.mc.player);
        long curr = System.currentTimeMillis();
        int time = (int) (curr - currTime) / 4;
		//System.out.println(time);
        float rotateAngle = (float)(int) (time % 360);
        
		GlStateManager.pushMatrix();
        {        	
        	GlStateManager.translate(width / 2 - 60, height / 2 + 40, 10.0f);    
            GlStateManager.rotate(180, 0, 0, 1);
            GlStateManager.rotate(rotateAngle, 0, 1, 0);
            GlStateManager.scale(40, 40, 40);
            
            float rot = player.rotationPitch;
            float prevrot = player.prevRotationPitch;
            player.rotationPitch = 0.0f;
            player.prevRotationPitch = player.rotationPitch;
            mc.getRenderManager().renderEntity(player, 0, 0, 0.0f, 0, 0.0f, false);
            player.rotationPitch = rot;
            player.prevRotationPitch = prevrot;
        }
        GlStateManager.popMatrix();
        //System.out.println(mouseX + ", " + mouseY);
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
	}

}
