package pl.kalibrov.main.gui;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import pl.kalibrov.main.gui.inventory.RPGInventoryContainer;
import pl.kalibrov.main.gui.inventory.RPGInventoryGui;

public class RPGGuiHandler implements IGuiHandler {

	public static final int RPG_INVENTORY_ID = 0;
		
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		System.out.println(player.toString());
		switch (ID) {
	        case RPG_INVENTORY_ID:
	            return new RPGInventoryContainer(player.inventory);
	        default: return null;
		}
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
        case RPG_INVENTORY_ID:
            return new RPGInventoryGui((RPGInventoryContainer) getServerGuiElement(ID, player, world, x, y, z), player.inventory);
        default: return null;
    }
	}

}
