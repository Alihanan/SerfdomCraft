package pl.modding.client.gui.test;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class TestHandler implements IGuiHandler{
	public static final int PEDESTAL = 0;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case PEDESTAL:
			return new TestContainer(player.inventory);
		default:
			return null;
	}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case PEDESTAL:
			return new TestGui((Container) getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		default:
			return null;
	}
	}

}
