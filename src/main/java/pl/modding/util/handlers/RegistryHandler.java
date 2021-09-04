package pl.modding.util.handlers;


import com.google.common.math.IntMath;
import com.mojang.authlib.GameProfile;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.MouseInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pl.modding.client.gui.CharacterScreen;
import pl.modding.client.gui.KeyHandler;
import pl.modding.client.gui.camera.FlyingCamera;
import pl.modding.client.gui.camera.RPGCameraRenderer;
import pl.modding.client.gui.camera.RPGCursorGUI;
import pl.modding.client.gui.camera.RPGPlayerRenderer;
import pl.modding.client.gui.inventories.RPGGuiHandler;
import pl.modding.client.gui.inventories.RPGInventoryGui;
import pl.modding.init.BlockInit;
import pl.modding.init.ItemInit;
import pl.modding.main.RPGMod;
import pl.modding.network.inventory.RPGHotbarPacket;
import pl.modding.network.inventory.RPGInventoryPacket;
import pl.modding.network.inventory.RPGNetwork;
import pl.modding.objects.items.ItemBase;


@EventBusSubscriber
public class RegistryHandler {
	// Handlers for a particular events
	private static KeyHandler keyHandler = new KeyHandler();
	private final static Minecraft mc = Minecraft.getMinecraft();
	private static Entity camera = null;
	public static RPGPlayerRenderer modelRenderer;
	public static RPGCameraRenderer rcr;
	public static float playerYawRotation = 0.0f;
	
	@SubscribeEvent
	public static void worldInit(WorldEvent.Load event) {
		//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");		
		//RPGMod.m.gameSettings.thirdPersonView = 1;
		if(RPGMod.m.player != null) {
			KeyHandler.removeAdditionalHotbar();
		}		
	}
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		/*event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));*/
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		/*
		for(Item item : ItemInit.ITEMS) {
			RPGMod.proxy.registerItemRenderer(item, 0, "inventory");
		}
		
		for(Block block : BlockInit.BLOCKS) {
			RPGMod.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");
		}*/
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
	}

	public static void preInitRegistries() {
	}
	public static void initRegistries() {
		//RPGMod.proxy.render();
	}
	public static void postInitRegistries() {
		MinecraftForge.EVENT_BUS.register(new RPGCursorGUI());
	}
	public static void serverRegistries() {
	}
	
	@SubscribeEvent
	public static void onKeyInput(KeyInputEvent event) {
		//Minecraft.getMinecraft().gameSettings.thirdPersonView = 

		
		keyHandler.onKeyInput(event);	
		keyHandler.removeAdditionalHotbar();
		RPGNetwork.DISPATCHER.sendToServer(new RPGHotbarPacket(0));
	}	
	@SubscribeEvent
	public static void onKeyGuiInput(KeyboardInputEvent.Pre event) {
		
	}	
	@SubscribeEvent
	public static void onKeyGuiInput(KeyboardInputEvent.Post event) {
		
	}	
	@SubscribeEvent
	public static void onMouseEvent(MouseEvent event) {
		/*
		System.out.println("##################################################");
		System.out.println("DX:" + event.getDx());
		System.out.println("DY:" + event.getDy());
		System.out.println("DWheel:" + event.getDwheel());
		System.out.println("##################################################");*/
		keyHandler.removeAdditionalHotbar();
		RPGNetwork.DISPATCHER.sendToServer(new RPGHotbarPacket(0));
		keyHandler.removeAdditionalHotbar();
	}
	@SubscribeEvent
	public static void onMouseInputEvent(MouseInputEvent event) {
		
		/*
		System.out.println("##################################################");
		System.out.println("DX:" + event.getDx());
		System.out.println("DY:" + event.getDy());
		System.out.println("DWheel:" + event.getDwheel());
		System.out.println("##################################################");*/
		
		if(RPGMod.m.player == null) {
			return;
		}
		keyHandler.removeAdditionalHotbar();
		RPGNetwork.DISPATCHER.sendToServer(new RPGHotbarPacket(0));
		keyHandler.removeAdditionalHotbar();
	}
	@SubscribeEvent
	public static void onMouseInputEvent(net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent event) {
		/*
		System.out.println("##################################################");
		System.out.println("DX:" + event.getDx());
		System.out.println("DY:" + event.getDy());
		System.out.println("DWheel:" + event.getDwheel());
		System.out.println("##################################################");*/
		if(RPGMod.m.player == null) {
			return;
		}
		keyHandler.removeAdditionalHotbar();
		RPGNetwork.DISPATCHER.sendToServer(new RPGHotbarPacket(0));
		keyHandler.removeAdditionalHotbar();
	}
	
	@SubscribeEvent
	public static void onPlayerRender(RenderPlayerEvent.Pre event) {		
		RenderPlayer rp = event.getRenderer();
		camera = RPGMod.m.getRenderViewEntity();
		if (modelRenderer == null) {
			modelRenderer = new RPGPlayerRenderer(rp.getRenderManager());
		}
		/*
		if (event.getEntityPlayer() instanceof FlyingCamera) {
			event.setCanceled(true);
		}*/
		if (event.getEntityPlayer().equals(RPGMod.m.player)) {
			
			if(!(rp instanceof RPGPlayerRenderer)) {
				/*
				AbstractClientPlayer acp = (AbstractClientPlayer)(RPGMod.m.player);	
				acp.posZ += RPGCameraRenderer.xRenderOffset;
				acp.posX += RPGCameraRenderer.zRenderOffset;
				acp.prevPosX = acp.lastTickPosX = acp.posX;
				acp.prevPosY = acp.lastTickPosY = acp.posY;
				acp.prevPosZ = acp.lastTickPosZ = acp.posZ;
				event.setCanceled(true);*/
				/*
				RPGMod.m.setRenderViewEntity(acp);
				double xRenderOffset = 0.0f;
				double zRenderOffset = 0.0f;*/
				
				//!(Minecraft.getMinecraft().currentScreen instanceof CharacterScreen) &&
				/*
				if(
				   !(Minecraft.getMinecraft().currentScreen instanceof RPGInventoryGui)) {
					Vec3d vector = RPGCameraRenderer.getEntityCameraTranslate(acp);
					xRenderOffset = vector.x;
					zRenderOffset = vector.z;
				}*/
				//mc.setRenderViewEntity(acp);
				
				//modelRenderer.doRender(acp, -3.0f, 0.0f, 0.0f, event.getEntityPlayer().rotationYaw, 0.0f); 
				//event.getX() 
				//event.getY()
				//event.getZ()
				//- xRenderOffset
				//- zRenderOffset
				//modelRenderer.doRender(acp, acp.posX, acp.posY, acp.posZ, acp.rotationYaw, 0.0f); 
				//RPGMod.m.setRenderViewEntity(camera);
				//modelRenderer.doRender(acp, -xRenderOffset, 0.0f, -zRenderOffset, event.getEntityPlayer().rotationYaw, 0.0f); */
				//modelRenderer.doRender(acp, event.getX(),event.getY(), event.getZ(), event.getEntityPlayer().rotationYaw, 0.0f); 
			}
		}			
	}
	@SubscribeEvent
	public static void postPlayerRender(RenderPlayerEvent.Post event) {
		/*
		AbstractClientPlayer acp = (AbstractClientPlayer)(RPGMod.m.player);	
		double xRenderOffset = 0.0f;
		double zRenderOffset = 0.0f;
		Vec3d vector = RPGCameraRenderer.getEntityCameraTranslate(acp);
		xRenderOffset = vector.x;
		zRenderOffset = vector.z;
		GlStateManager.translate(xRenderOffset, 0.0F, zRenderOffset);*/
		/*
		if (event.getEntityPlayer().equals(RPGMod.m.player) && camera != null) {
			RPGMod.m.setRenderViewEntity(camera);
		}*/
		//mc.setRenderViewEntity(camera);
	}
	
	@SubscribeEvent
	public static void onEntityHandRender(RenderHandEvent event) {
		
	}
	

	private static float bowRotateYawChange = 0.0f;
	private static float bowRotatePitchChange = 0.0f;
	
	private static float bowToThirdPerson = 0.0f;
	
	@SubscribeEvent
	public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {			
		/**
		 * IN CAMERA COORDINATES!!! +x  ....  -x
		 */
		if(rcr == null || !(RPGMod.m.entityRenderer instanceof RPGCameraRenderer)) {
			rcr = new RPGCameraRenderer(Minecraft.getMinecraft());
			RPGMod.m.entityRenderer = rcr;	
			rcr.state = 0;
			RegistryHandler.modelRenderer = new RPGPlayerRenderer(RPGMod.m.getRenderManager());
		}
		AbstractClientPlayer acp = (AbstractClientPlayer)(RPGMod.m.player);	
		double xRenderOffset = 0.0f;
		double zRenderOffset = 0.0f;
		Vec3d vector = RPGCameraRenderer.getEntityCameraTranslate(acp);
		xRenderOffset = vector.x;
		zRenderOffset = vector.z;
		
		
		
		//GlStateManager.translate(xRenderOffset, 0.0F, zRenderOffset);
		//acp.cameraYaw = 15.0f;
		
		
		
		/*
		while(rotationYawHead > 90.0f) {
			rotationYawHead -= 90.0f;
		}
		while(rotationYawHead < -90.0f) {
			rotationYawHead += 90.0f;
		}*/
		//event.setYaw(event.getYaw() - 0.5f);
		if(event.getEntity().equals(RPGMod.m.player) && RPGMod.m.player.getHeldItemMainhand() != null 
				&& RPGMod.m.player.getHeldItemMainhand().getItem().getClass().equals(ItemBow.class) && RPGMod.m.player.isHandActive()) {
			bowToThirdPerson += 0.025f;
			if(bowToThirdPerson > 0.7f * 5 / 6) bowToThirdPerson = 0.7f * 5 / 6;
		}
		else {
			bowToThirdPerson -= 0.025f;
			if(bowToThirdPerson < 0.0f) bowToThirdPerson = 0.0f;
		}
		
		if(bowToThirdPerson < 0.7f * 5 / 6) {
			mc.gameSettings.thirdPersonView = 1;
			GlStateManager.translate(-0.7f + bowToThirdPerson, 0.0F, bowToThirdPerson * baseFOV / 4.0f);
		}else {
			mc.gameSettings.thirdPersonView = 0;
		}
		
		//GlStateManager.rotate((float) rotationYawHead, (float)rotVec.x, (float)rotVec.y, (float)rotVec.z);
		//event.setYaw((float) (event.getYaw() + 5.0f));
		
		/*
		if(event.getEntity().equals(RPGMod.m.player) && RPGMod.m.player.getHeldItemMainhand() != null 
				&& RPGMod.m.player.getHeldItemMainhand().getItem().getClass().equals(ItemBow.class) && RPGMod.m.player.isHandActive()) {
			bowRotateYawChange += 0.25f;
			bowRotatePitchChange += 0.1f;
			
			if(bowRotateYawChange > 7.0f) {
				bowRotateYawChange = 7.0f;
			}
			
			if(bowRotatePitchChange > 2.0f) {
				bowRotatePitchChange = 2.0f;
			}
				
		}else {
			bowRotateYawChange -= 0.25f;
			bowRotatePitchChange -= 0.1f;
			if(bowRotateYawChange < 0.0f) {
				bowRotateYawChange = 0.0f;
			}
			if(bowRotatePitchChange < 0.0f) {
				bowRotatePitchChange = 0.0f;
			}
			
		}
		
		GlStateManager.rotate(-bowRotateYawChange, 0.0F, 1.0F, 0.0F);	*/	
		//GlStateManager.rotate(2.0f - bowRotatePitchChange, 1.0F, 0.0F, 0.0F);	
		//acp.rotationYaw += playerYawRotation;
		//event.setYaw(event.getYaw() + (float) rotationYawHead);
		/*
		if (event.getEntityPlayer().equals(RPGMod.m.player) && camera != null) {
			RPGMod.m.setRenderViewEntity(camera);
		}*/
		//GlStateManager.translate(5.0f, 0.0F, 0.0f);

	}
	
	
	private static int power = 0; 
	@SubscribeEvent
	public static void onArrowEvent(ArrowLooseEvent event) {
		if(event.getEntityPlayer().equals(mc.player)) {
			
			//Item bow = event.getEntityPlayer().getHeldItemMainhand().getItem();
			power = event.getCharge();
			
			
			/*
			System.out.println("SHOOTING");
			Vec3d look = mc.player.getLookVec();
			look = new Vec3d(look.x, 0.0f, look.z);
			Vec3d real = new Vec3d(look.x - 0.7f, 0.0f, look.z);
			
			if(mc.pointedEntity != null) {
				real = mc.pointedEntity.getPositionEyes(1.0F).subtract(mc.player.getPositionEyes( 1.0F));
				real = new Vec3d(real.x, 0.0f, real.z); 
			}
			System.out.println(look.toString());
			System.out.println(real.toString());
			
			
			double prod = look.dotProduct(real);
			double length = Math.sqrt(look.lengthSquared()) * Math.sqrt(real.lengthSquared());
			prod = prod / length;
			System.out.println(prod);
			double angle = Math.toDegrees(Math.acos(prod));
			System.out.println(angle);
			//double rotationYawHead = -Math.atan2(real.x, real.z) * 180.0F / (float)Math.PI;
			
			//Vec3d
			
			playerYawRotation = (float) angle;*/
			
			//event.getEntityPlayer().rotationYaw += 4.0f;*/
			
		}
	}
	
	
	private static float bowState = 0.0f;
	private static float baseFOV = 30.0f; // 15.0f
	@SubscribeEvent
	public static void onEntityRenderEnd(EntityViewRenderEvent.FOVModifier event) {
		
		if(event.getEntity().equals(RPGMod.m.player) && RPGMod.m.player.getHeldItemMainhand() != null 
				&& RPGMod.m.player.getHeldItemMainhand().getItem().getClass().equals(ItemBow.class) && RPGMod.m.player.isHandActive()) {
			bowState += 0.1f;
			if(bowState > 5.0f) bowState = 5.0f;
			
		}else {
			bowState -= 0.1f;
			if(bowState < 0.0f) bowState = 0.0f;
		}
		event.setFOV(baseFOV + bowState);
		
		
	}
	
	@SubscribeEvent
	public static void onGuiOpenEvent(GuiOpenEvent event) {
		if(event.getGui() instanceof GuiInventory) {
			//RPGMod.m.getIntegratedServer().
			//System.out.println(Minecraft.isR);
		}
	}
	
	
	
	@SubscribeEvent
	public static void onGameOverLay(RenderGameOverlayEvent.Pre event) {
		if(event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
	        //event.setCanceled(true);
	    }
	}
	
	@SubscribeEvent
	public static void onArrowSpawn(EntityJoinWorldEvent event) {
		/*
		if(event.getEntity() instanceof EntityArrow) {
			EntityArrow arrow = (EntityArrow)(event.getEntity());
			if(arrow.shootingEntity != null && arrow.shootingEntity.equals(mc.player)) {
				//arrow.motionX += 1.0f;
				float f = ItemBow.getArrowVelocity(power);
				Minecraft m = Minecraft.getMinecraft();

				Vec3d vector = RPGCameraRenderer.getEntityCameraTranslate(m.player);
				double xRenderOffset = vector.x;
				double zRenderOffset = vector.z;
				
				mc.player.posX -= xRenderOffset;
				mc.player.posZ -= zRenderOffset;
				mc.player.prevPosX = mc.player.lastTickPosX = mc.player.posX;
				mc.player.prevPosY = mc.player.lastTickPosY = mc.player.posY;
		        mc.player.prevPosZ = mc.player.lastTickPosZ = mc.player.posZ;		
				
		        
		        
				arrow.shoot(mc.player, mc.player.rotationPitch, mc.player.rotationYaw + 4.0f, 0.0F, f * 3.0F, 1.0F);
			}			
		}*/
	}
	
	
}
