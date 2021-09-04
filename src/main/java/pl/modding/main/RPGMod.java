package pl.modding.main;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import pl.modding.client.gui.camera.RPGCameraRenderer;
import pl.modding.client.gui.camera.RPGPlayerRenderer;
import pl.modding.client.gui.inventories.RPGGuiHandler;
import pl.modding.client.gui.test.TestGui;
import pl.modding.client.gui.test.TestHandler;
import pl.modding.network.inventory.RPGNetwork;
import pl.modding.proxy.CommonProxy;
import pl.modding.reference.Reference;
import pl.modding.util.handlers.RegistryHandler;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_MINECRAFT_VERSIONS)
public class RPGMod {

	
	public static Minecraft m = Minecraft.getMinecraft();
	
	@Instance
	public static RPGMod instance;
	
	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
	public static CommonProxy proxy;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		//NetworkRegistry.INSTANCE.registerGuiHandler(RPGMod.instance, new TestHandler());
		
		// Register all custom packets
		RPGNetwork.init();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new RPGGuiHandler());
		RegistryHandler.preInitRegistries();
		
		for(int i = 5; i < 10; i++) {
			m.gameSettings.keyBindsHotbar[i-1] = new KeyBinding("key.hotbar." + i, -i, "key.categories.inventory");
		}
		
		/*
		for(int i = 5; i < 10; i++) {
			KeyBinding.setKeyBindState(i + 1, false);
		}*/
		
	}
	
	
	@EventHandler
	public static void Init(FMLInitializationEvent event) {		
		RegistryHandler.initRegistries();		
	}
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		RegistryHandler.postInitRegistries();
	}
	@EventHandler
	public static void serverInit(FMLServerStartingEvent event) {
		RegistryHandler.serverRegistries();
	}
	
	
}
