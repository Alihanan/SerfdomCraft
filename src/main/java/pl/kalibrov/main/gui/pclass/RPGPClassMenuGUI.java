package pl.kalibrov.main.gui.pclass;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class RPGPClassMenuGUI extends GuiScreen{
	private EntityPlayer player;
	private int xWidth;
	private int yHeight;
	
	
	public RPGPClassMenuGUI(EntityPlayer player) {
		super();
		this.player = player;
		this.xWidth = 256;
		this.yHeight = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawTexturedModalRect(width/2 - xWidth/2, height/2 - yHeight/2, 0, 0, xWidth, yHeight);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	@Override
	public void initGui() {
		super.initGui();
	}
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
	}
}
