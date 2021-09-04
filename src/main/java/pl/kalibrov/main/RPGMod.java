package pl.kalibrov.main;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = RPGMod.MODID, name = RPGMod.NAME, version = RPGMod.VERSION, acceptedMinecraftVersions = RPGMod.ACCEPTED_MINECRAFT_VERSIONS)
public class RPGMod {
	public static final String MODID = "serfdomcraft";
	public static final String NAME = "SerfdomCraft";
	public static final String VERSION = "0.0.1";
	public static final String ACCEPTED_MINECRAFT_VERSIONS = "1.12.2";
	
	public static final String CLIENT = "pl.kalibrov.main.client.ClientProxy";
	public static final String COMMON = "pl.kalibrov.main.server.ServerProxy";
	
	public static final String FOLDER = "SerfdomCraft/";
	
	
	// Config
	public static final float tentViewYawAngle = 45.0f;
	public static final float tentViewPitchAngle = 45.0f;
	
	@Instance
	public static RPGMod instance;
	
	@SidedProxy(clientSide = RPGMod.CLIENT, serverSide = RPGMod.COMMON)
	public static CommonProxy proxy;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		// Do everything in proxy
		proxy.preInit(event);				
	}	
	@EventHandler
	public static void Init(FMLInitializationEvent event) {		
		proxy.Init(event);		
	}
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);		
	}
	@EventHandler
	public static void serverStarted(FMLServerStartedEvent event) {
		proxy.serverStarted(event);
	}
	@EventHandler
	public static void serverStopping(FMLServerStoppingEvent event) {
		proxy.serverStopping(event);
	}
	
	public static String appendModID(String value) {
		return MODID + ":" + value;
	}
	
	
}
