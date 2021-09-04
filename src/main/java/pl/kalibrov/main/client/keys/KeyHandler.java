package pl.kalibrov.main.client.keys;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.MouseInputEvent;
import pl.kalibrov.main.client.ClientProxy;
import pl.kalibrov.main.gui.RPGGuiHandler;
import pl.kalibrov.main.gui.inventory.RPGInventoryGui;
import pl.kalibrov.main.gui.pclass.RPGPClassMenuGUI;
import pl.kalibrov.main.network.RPGNetwork;
import pl.kalibrov.main.network.packets.RPGHotbarPacket;
import pl.kalibrov.main.network.packets.RPGInventoryPacket;

//@EventBusSubscriber
public class KeyHandler // note that before we extended KeyHandler, but that class no longer exists
{
	/** Key index for easy handling */
	public static final int CUSTOM_INV = 0;
	public static final int VIEW_CHANGE = 1;

	
	/** Key descriptions; use a language file to localize the description later */
	private static final String[] desc = {"key.tut_inventory.desc", "key.tut_changeView.desc"};
	/** Default key values */
	private static final int[] keyValues = {Keyboard.KEY_P, Keyboard.KEY_F5};
	private final KeyBinding[] keys;
	
	public KeyHandler() {
		keys = new KeyBinding[desc.length];
		for (int i = 0; i < desc.length; ++i) {
			keys[i] = new KeyBinding(desc[i], keyValues[i], "key.tutorial.category");
			ClientRegistry.registerKeyBinding(keys[i]);
		}
	}
	
	
	KeyHotbarDisabler hotbarDisabler = new KeyHotbarDisabler();
	
	/**
	* KeyInputEvent is in the FML package, so we must register to the FML event bus
	*/	
	public void onKeyInput(KeyInputEvent event) {
		keyProcess(event);
		
	}
	public void onKeyInput(KeyboardInputEvent.Pre event) {		
		keyProcess(event);
	}
	
	private void keyProcess(Event event) {
		if (keys[CUSTOM_INV].isPressed()) {			
			// TODO optimize
			if (!FMLClientHandler.instance().isGUIOpen(RPGInventoryGui.class)) {				
				RPGNetwork.DISPATCHER.sendToServer(new RPGInventoryPacket(RPGGuiHandler.RPG_INVENTORY_ID));
			}	
		}else if(keys[VIEW_CHANGE].isPressed()) {
			//TODO event.setCanceled(true);
		}
		
		hotbarDisabler.processKey(event);
	}
	
	public void worldInitClient(EntityJoinWorldEvent event) {
		hotbarDisabler.worldInitClient(event);
	}
	
	public void onMouseEvent(MouseEvent event) {
		hotbarDisabler.onMouseEvent(event);
	}
	
	public void onMouseInputEvent(MouseInputEvent event) {
		hotbarDisabler.onMouseInputEvent(event);
	}
	
	public void onMouseInputEvent(net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent event) {
		hotbarDisabler.onMouseInputEvent(event);
	}

}