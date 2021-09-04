package pl.kalibrov.main.client.keys;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.gameevent.InputEvent.MouseInputEvent;
import pl.kalibrov.main.client.ClientProxy;
import pl.kalibrov.main.network.RPGNetwork;
import pl.kalibrov.main.network.packets.RPGHotbarPacket;

public class KeyHotbarDisabler {
	public static final int HOTBAR_KEY_5 = 0;
	public static final int HOTBAR_KEY_6 = 1;
	public static final int HOTBAR_KEY_7 = 2;
	public static final int HOTBAR_KEY_8 = 3;
	public static final int HOTBAR_KEY_9 = 4;
	private static final int[] keyValues = {
			Keyboard.KEY_5, Keyboard.KEY_6, Keyboard.KEY_7, Keyboard.KEY_8, Keyboard.KEY_9};
	private final KeyBinding[] keys;
	private static final String[] desc = {
			"key.5_slot_disable.desc", 
			"key.6_slot_disable.desc", 
			"key.7_slot_disable.desc", 
			"key.8_slot_disable.desc", 
			"key.9_slot_disable.desc"};
	
	public KeyHotbarDisabler() {
		keys = new KeyBinding[desc.length];
		for (int i = 0; i < desc.length; ++i) {
			keys[i] = new KeyBinding(desc[i], keyValues[i], "key.tutorial.category");
			ClientRegistry.registerKeyBinding(keys[i]);
		}
	}
	
	
	
	public void processKey(Event event) {
		if(keys[HOTBAR_KEY_5].isPressed() ||
				keys[HOTBAR_KEY_6].isPressed() ||
				keys[HOTBAR_KEY_7].isPressed() ||
				keys[HOTBAR_KEY_8].isPressed() ||
				keys[HOTBAR_KEY_9].isPressed()) {
			if(event.isCancelable()) event.setCanceled(true);						
		}
		RPGNetwork.DISPATCHER.sendToServer(new RPGHotbarPacket(0));
	}
	
	
	private static int previousSelectedIndex = 0;
	
	public void disableHotbar() {
		disableHotbar(10);
	}
	public void disableHotbar(int chance) {
		// Periodically remove additional items
		if(ClientProxy.m.player != null &&
				(ClientProxy.m.player.world.getTotalWorldTime() % chance == 0)) {
			RPGNetwork.DISPATCHER.sendToServer(new RPGHotbarPacket(0));
			return;
		}		
		disableHotbarKey();
	}
	
	private void disableHotbarKey() {
		for(int i = 5; i < 10; i++) {
			ClientProxy.m.gameSettings.keyBindsHotbar[i-1] = new KeyBinding("key.hotbar." + i, -i, "key.categories.inventory");
		}
		if(previousSelectedIndex > 3) {
			previousSelectedIndex = 3;
		}		
		
		if(ClientProxy.m.player.inventory.currentItem > 3) {			
			ClientProxy.m.player.inventory.changeCurrentItem(-1);
			ClientProxy.m.player.inventory.currentItem = previousSelectedIndex;
		}	
		
		previousSelectedIndex = ClientProxy.m.player.inventory.currentItem;
	}
	public static void removeHotbarSlotItems(EntityPlayer player) {
		for(int i = 4; i < 9; i++) { // Remove items from 5 - 9
			player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
		}	
	}
	
	public void worldInitClient(EntityJoinWorldEvent event) {
		disableHotbar(1);
	}
	
	public void onMouseEvent(MouseEvent event) {
		disableHotbar();
	}
	
	public void onMouseInputEvent(MouseInputEvent event) {
		disableHotbar();
	}
	
	public void onMouseInputEvent(net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent event) {
		disableHotbar();
	}
	
}
