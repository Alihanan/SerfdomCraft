package pl.kalibrov.main.client;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.MouseInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import pl.kalibrov.main.CommonProxy;
import pl.kalibrov.main.RPGMod;
import pl.kalibrov.main.client.camera.CameraFMRenderer;
import pl.kalibrov.main.client.camera.CameraPlayerRenderer;
import pl.kalibrov.main.client.keys.KeyHandler;
import pl.kalibrov.main.customStructures.init.ModBlocksClient;
import pl.kalibrov.main.customStructures.init.ModItems;
import pl.kalibrov.main.client.camera.Camera;
import pl.kalibrov.main.client.camera.CameraCursor;
import pl.kalibrov.main.network.RPGNetwork;
import pl.kalibrov.main.network.packets.RPGHotbarPacket;

@EventBusSubscriber(modid = RPGMod.MODID, value = Side.CLIENT)
public class ClientProxy extends CommonProxy{
	public static final Minecraft m = Minecraft.getMinecraft();
	
	// Different client subsystems
	private static Camera camera = new Camera();	
	private static RPGClassPlayerSystem playerClassSystem = null;
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		// Turn ON obj models
		OBJLoader.INSTANCE.addDomain(RPGMod.MODID);
		ModBlocksClient.init();
		ModItems.init();
	}	
	@Override
	public void Init(FMLInitializationEvent event) {		
		super.Init(event);
	}
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		camera.postInit(event);			
	}	
	
	@SubscribeEvent
	public static void worldInitClient(EntityJoinWorldEvent event) {
		// Check to ensure that it fires once (as far as I checked)
		if(m.player == null || m.player != event.getEntity()) {
			return;
		}
		// Disable hotbars
		keyHandler.worldInitClient(event);
		playerClassSystem = new RPGClassPlayerSystem();
		System.out.println("[RPG_MOD] Client WorldInit fired");
	}
	
	
	/**
	 * All functions below are used for custom structures - blocks, items, etc
	 */
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, 
				new ModelResourceLocation(item.getRegistryName(), id));
	}
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {

	}
	
	/**
	 * All functions below are used for key handling
	 */
	private static KeyHandler keyHandler = new KeyHandler();
	
	@SubscribeEvent
	public static void onKeyInput(KeyInputEvent event) {		
		keyHandler.onKeyInput(event);	
	}	
	@SubscribeEvent
	public static void onMouseEvent(MouseEvent event) {
		keyHandler.onMouseEvent(event);
	}
	@SubscribeEvent
	public static void onMouseInputEvent(MouseInputEvent event) {		
		if(m.player == null) {
			return;
		}
		keyHandler.onMouseInputEvent(event);
	}
	@SubscribeEvent
	public static void onMouseInputEvent(net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent event) {		
		if(m.player == null) {
			return;
		}
		keyHandler.onMouseInputEvent(event);
	}
	
	/**
	 * All methods below are used for the rendering changes (camera classes)
	 */
	@SubscribeEvent
	public static void onPlayerRender(RenderPlayerEvent.Pre event) {		
		camera.onPlayerRender(event, playerClassSystem);
	}

	@SubscribeEvent
	public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {					
		camera.onCameraSetup(event);
	}
	
	
	@SubscribeEvent
	public static void onGuiOpenEvent(GuiOpenEvent event) {
		if(event.getGui() instanceof GuiInventory) {
			// TODO event.setCancelled(true); ...
		}
	}		
	
	@SubscribeEvent
	public static void onGameOverLay(RenderGameOverlayEvent.Pre event) {		
		camera.onGameOverLay(event);
	}
	
}
