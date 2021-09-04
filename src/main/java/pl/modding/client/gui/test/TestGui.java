package pl.modding.client.gui.test;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import pl.modding.main.RPGMod;
import pl.modding.reference.Reference;

public class TestGui extends GuiContainer{
	
	private InventoryPlayer playerInv;
	//private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/inventory.png");
	
	public TestGui(Container inventorySlotsIn, InventoryPlayer playerInv) {
		super(inventorySlotsIn);
		this.playerInv = playerInv;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 0, 1);
		//mc.getTextureManager().bindTexture(BG_TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = I18n.format("Something");
		fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
		fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
	}
	
	

}
