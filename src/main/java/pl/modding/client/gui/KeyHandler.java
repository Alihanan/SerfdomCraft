package pl.modding.client.gui;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import pl.modding.client.gui.inventories.RPGGuiHandler;
import pl.modding.client.gui.inventories.RPGInventoryGui;
import pl.modding.client.gui.test.TestGui;
import pl.modding.client.gui.test.TestHandler;
import pl.modding.main.RPGMod;
import pl.modding.network.inventory.RPGHotbarPacket;
import pl.modding.network.inventory.RPGInventoryPacket;
import pl.modding.network.inventory.RPGNetwork;

//@EventBusSubscriber
public class KeyHandler // note that before we extended KeyHandler, but that class no longer exists
{
	/** Key index for easy handling */
	public static final int CUSTOM_INV = 0;
	public static final int VIEW_CHANGE = 1;
	public static final int HOTBAR_KEY_5 = 2;
	/** Key descriptions; use a language file to localize the description later */
	private static final String[] desc = {"key.tut_inventory.desc", "key.tut_change.desc", 
			"key.tut_change.desc"};
	/** Default key values */
	private static final int[] keyValues = {Keyboard.KEY_P, Keyboard.KEY_F5, 
			Keyboard.KEY_5, Keyboard.KEY_6, Keyboard.KEY_7, Keyboard.KEY_8, Keyboard.KEY_9};
	private final KeyBinding[] keys;
	
	public KeyHandler() {
		keys = new KeyBinding[desc.length];
		for (int i = 0; i < desc.length; ++i) {
			keys[i] = new KeyBinding(desc[i], keyValues[i], "key.tutorial.category");
			ClientRegistry.registerKeyBinding(keys[i]);
		}
	}
	
	/**
	* KeyInputEvent is in the FML package, so we must register to the FML event bus
	* 
	* 
	*/
	
	public int onKeyInput(KeyInputEvent event) {
		// FMLClientHandler.instance().getClient().inGameHasFocus
		//
		if (keys[CUSTOM_INV].isPressed()) {
			//Main.packetPipeline.sendToServer(new OpenGuiPacket(TutorialMain.GUI_CUSTOM_INV));
			Minecraft m = Minecraft.getMinecraft();
			
			if (!FMLClientHandler.instance().isGUIOpen(TestGui.class)) {
				//m.gameSettings.thirdPersonView = 0;
				
				//m.displayGuiScreen(new CharacterScreen());
				//m.displayGuiScreen(new RPGInventoryGui(p));
				BlockPos pos = m.player.getPosition();
				//m.player.openGui(RPGMod.instance, RPGGuiHandler.RPG_GUI_CONTAINER_ID, m.world, pos.getX(), pos.getY(), pos.getZ());
				//m.player.openGui(RPGMod.instance, TestHandler.PEDESTAL, m.player.world, pos.getX(), pos.getY(), pos.getZ());
				
				System.out.println("[CLIENT]PACKET SENT");
				RPGNetwork.DISPATCHER.sendToServer(new RPGInventoryPacket(RPGGuiHandler.RPG_INVENTORY_ID));
				
				
				
				return 0;
			}	
		}else if(keys[VIEW_CHANGE].isPressed()) {
			//event.setCanceled(true);
			//Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
		}else if(keys[HOTBAR_KEY_5].isPressed()) {
			//event.setCanceled(true);			
		}
		RPGNetwork.DISPATCHER.sendToServer(new RPGHotbarPacket(0));
		
		//Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
		return 1;
		
	}
	public int onKeyInput(KeyboardInputEvent.Pre event) {
		// FMLClientHandler.instance().getClient().inGameHasFocus
		//
		if (keys[CUSTOM_INV].isPressed()) {
			//Main.packetPipeline.sendToServer(new OpenGuiPacket(TutorialMain.GUI_CUSTOM_INV));
			Minecraft m = Minecraft.getMinecraft();
			
			if (!FMLClientHandler.instance().isGUIOpen(TestGui.class)) {
				//m.gameSettings.thirdPersonView = 0;
				
				//m.displayGuiScreen(new CharacterScreen());
				//m.displayGuiScreen(new RPGInventoryGui(p));
				BlockPos pos = m.player.getPosition();
				//m.player.openGui(RPGMod.instance, RPGGuiHandler.RPG_GUI_CONTAINER_ID, m.world, pos.getX(), pos.getY(), pos.getZ());
				//m.player.openGui(RPGMod.instance, TestHandler.PEDESTAL, m.player.world, pos.getX(), pos.getY(), pos.getZ());
				
				System.out.println("[CLIENT]PACKET SENT");
				RPGNetwork.DISPATCHER.sendToServer(new RPGInventoryPacket(RPGGuiHandler.RPG_INVENTORY_ID));
				
				
				
				return 0;
			}	
		}else if(keys[VIEW_CHANGE].isPressed()) {
			//event.setCanceled(true);
			//Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
		}else if(keys[HOTBAR_KEY_5].isPressed()) {
			event.setCanceled(true);			
		}
		RPGNetwork.DISPATCHER.sendToServer(new RPGHotbarPacket(0));
		
		//Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
		return 1;
		
	}
	
	private static int previousSelectedIndex = 0;
	public static void removeAdditionalHotbar() {
		for(int i = 5; i < 10; i++) {
			RPGMod.m.gameSettings.keyBindsHotbar[i-1] = new KeyBinding("key.hotbar." + i, -i, "key.categories.inventory");
		}
		if(previousSelectedIndex > 3) {
			previousSelectedIndex = 3;
		}		
		
		if(RPGMod.m.player.inventory.currentItem > 3) {			
			RPGMod.m.player.inventory.changeCurrentItem(-1);
			RPGMod.m.player.inventory.currentItem = previousSelectedIndex;
		}	
		
		previousSelectedIndex = RPGMod.m.player.inventory.currentItem;
		
			
	}
	public static void removeHotbarSlots(EntityPlayer player) {
		for(int i = 4; i < 9; i++) { // Remove items from 5 - 9
			player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
		}	
	}
}