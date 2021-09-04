package pl.modding.client.gui.inventories;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class RPGGuiHandler implements IGuiHandler {

	public static final int RPG_INVENTORY_ID = 0;
		
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
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
