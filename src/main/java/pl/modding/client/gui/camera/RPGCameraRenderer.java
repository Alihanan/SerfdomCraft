package pl.modding.client.gui.camera;

import java.util.UUID;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;
import com.mojang.realmsclient.dto.RealmsServer.McoServerComparator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.FakePlayer;
import pl.modding.client.gui.CharacterScreen;
import pl.modding.main.RPGMod;
import pl.modding.util.handlers.RegistryHandler;
import scala.tools.nsc.typechecker.SuperAccessors.SuperAccTransformer;

public class RPGCameraRenderer extends EntityRenderer{
	private final Minecraft mc;
	private float offsetY = 0.0F; // just for testing, should be based on actual render size
	private float offsetX = 0.0F; // just for testing, should be based on actual render size
	public int state = 0;
	
	public static final double scaleOffset = 0.7f; //0.7f
	public static double xRenderOffset = 0.0f;
	public static double zRenderOffset = 0.0f;
	public static double cameraSide = 1.0f;
	
	
	public static Entity origPointedEntity = null;
	
	public RPGCameraRenderer(Minecraft mc) {
		super(mc, mc.getResourceManager());
		this.mc = mc;
		System.out.println("Constructor");
	}

	public void renderWorld(float partialTicks, long finishTimeNano) {
		FlyingCamera fakePlayer = null;
		Minecraft m = Minecraft.getMinecraft();
		
		/*
		if(!(Minecraft.getMinecraft().currentScreen instanceof CharacterScreen)) {
			
			fakePlayer = new FlyingCamera(mc.world, mc.player.getGameProfile());
			//m.player.getRidingEntity();
			//pl.setInvisible(true);
			//m.world.spawnEntity(pl);
			fakePlayer.setPositionAndRotation(m.player.posX + 2.0f, m.player.posY, m.player.posZ, m.player.rotationYaw, m.player.rotationPitch);
			//pl.prevPosX = m.player.prevPosX;
			//pl.prevPosY = m.player.prevPosY;
			//pl.prevPosZ = m.player.prevPosZ;
			fakePlayer.prevPosX = fakePlayer.lastTickPosX = fakePlayer.posX;
	        fakePlayer.prevPosY = fakePlayer.lastTickPosY = fakePlayer.posY;
	        fakePlayer.prevPosZ = fakePlayer.lastTickPosZ = fakePlayer.posZ;

	        fakePlayer.prevRotationPitch = fakePlayer.rotationPitch;
	        fakePlayer.prevRotationYaw = fakePlayer.rotationYaw;
	        fakePlayer.prevRotationYawHead = fakePlayer.rotationYawHead;
	        //m.world.spawnEntity(fakePlayer);
			m.setRenderViewEntity(fakePlayer);
		}*/
		//Minecraft.getMinecraft().getRenderManager().playerViewX -= 2.0f;
		//m.world.loadedEntityList.add(m.player);
		/*
		double posX = m.getRenderViewEntity().posX;
		m.getRenderViewEntity().posX = posX + 2.0f;
		m.getRenderViewEntity().prevPosX = posX + 2.0f;*/
		//System.out.println("22tick");
		//GlStateManager.translate(50.0f, 0.0F, 0.0f);
		super.renderWorld(partialTicks, finishTimeNano);
		/*m.getRenderViewEntity().posX = posX;
		m.getRenderViewEntity().prevPosX = posX ;*/
		//Minecraft.getMinecraft().getRenderManager().playerViewX += 2.0f;
		//super.render
		//m.setRenderViewEntity(m.player);
		//m.getRenderManager().renderViewEntity = m.player;
		//m.getRenderManager().renderEntityStatic(m.player, partialTicks, false);
		//m.getRenderManager().renderEntity(m.player, m.player.posX, m.player.posY, m.player.posZ, m.player.rotationYaw, 0.0f, false);
		
	}
	
	@Override
	public void updateCameraAndRender(float partialTick, long nanoTime) {		
		if (mc.player == null || mc.player.isPlayerSleeping()){
			super.updateCameraAndRender(partialTick, nanoTime);
			return;
		}
		/*
		if ((Minecraft.getMinecraft().currentScreen instanceof CharacterScreen)) {
			super.updateCameraAndRender(partialTick, nanoTime);
			return;
		}*/
		Minecraft m = Minecraft.getMinecraft();
		
				
		//Vec3d vector = getEntityCameraTranslate(m.player);
		//double xRenderOffset = vector.x;
		//double zRenderOffset = vector.z;
		
		/*
		if(!(Minecraft.getMinecraft().currentScreen instanceof CharacterScreen)) {
			EntityArmorStand fakePlayer = null;
			//GameProfile gp = new GameProfile(UUID.randomUUID(), "FakePlayer");
			
			fakePlayer = new EntityArmorStand(mc.world, m.player.posX, m.player.posY, m.player.posZ);
			//m.player.getRidingEntity();
			//pl.setInvisible(true);
			//m.world.spawnEntity(pl);
			//fakePlayer.setPositionAndRotation(m.player.posX + xRenderOffset, m.player.posY, m.player.posZ + zRenderOffset, 
					//m.player.rotationYaw, m.player.rotationPitch);
			fakePlayer.setPositionAndRotation(m.player.posX + 3.0f, m.player.posY, m.player.posZ, 
					m.player.rotationYaw, m.player.rotationPitch);
			//pl.prevPosX = m.player.prevPosX;
			//pl.prevPosY = m.player.prevPosY;
			//pl.prevPosZ = m.player.prevPosZ;
			fakePlayer.prevPosX = fakePlayer.lastTickPosX = fakePlayer.posX;
	        fakePlayer.prevPosY = fakePlayer.lastTickPosY = fakePlayer.posY;
	        fakePlayer.prevPosZ = fakePlayer.lastTickPosZ = fakePlayer.posZ;

	        fakePlayer.prevRotationPitch = fakePlayer.rotationPitch;
	        fakePlayer.prevRotationYaw = fakePlayer.rotationYaw;
	        fakePlayer.prevRotationYawHead = fakePlayer.rotationYawHead;
	        //m.world.spawnEntity(fakePlayer);
			m.setRenderViewEntity(fakePlayer);
		}*/
		
		
		
		//GlStateManager.translate(xRenderOffset, 0.0F, zRenderOffset);
		/*
		Vec3d vector = getEntityCameraTranslate((EntityPlayer) m.getRenderViewEntity());
		xRenderOffset = vector.x;
		zRenderOffset = vector.z;
		
		m.getRenderViewEntity().posX -= xRenderOffset;
		m.getRenderViewEntity().posZ -= zRenderOffset;
		m.getRenderViewEntity().prevPosX = m.getRenderViewEntity().lastTickPosX = m.getRenderViewEntity().posX;
		m.getRenderViewEntity().prevPosY = m.getRenderViewEntity().lastTickPosY = m.getRenderViewEntity().posY;
        m.getRenderViewEntity().prevPosZ = m.getRenderViewEntity().lastTickPosZ = m.getRenderViewEntity().posZ;				
        
        xPosCurrent = m.getRenderViewEntity().posX;
        zPosCurrent = m.getRenderViewEntity().posZ;*/
		//System.out.println("tick");
		//GlStateManager.translate(50.0F, 0.0F, 0.0F);
		//mc.player.rotationYaw -= RegistryHandler.playerYawRotation;
		super.updateCameraAndRender(partialTick, nanoTime);
		//mc.player.rotationYaw += RegistryHandler.playerYawRotation;
		
		//GlStateManager.translate(-xRenderOffset, 0.0F, -zRenderOffset);
		
		/*m.getRenderViewEntity().posZ += xRenderOffset;
		m.getRenderViewEntity().posX += zRenderOffset;
		m.getRenderViewEntity().prevPosX = m.getRenderViewEntity().lastTickPosX = m.getRenderViewEntity().posX;
		m.getRenderViewEntity().prevPosY = m.getRenderViewEntity().lastTickPosY = m.getRenderViewEntity().posY;
        m.getRenderViewEntity().prevPosZ = m.getRenderViewEntity().lastTickPosZ = m.getRenderViewEntity().posZ;*/
        
        
        
        //GuiScreen.drawRect(width/2 - 5, state, state, state, state);
	}
	
	
	@Override
	public void getMouseOver(float partialTick) {		
		super.getMouseOver(partialTick);
		origPointedEntity = mc.pointedEntity;
				
		if (mc.player == null || mc.player.isPlayerSleeping()){					
			return;
		}
		if ((Minecraft.getMinecraft().currentScreen instanceof CharacterScreen)) {
			return;
		}
		/*
		double dist = 10.0f;
		if(mc.pointedEntity != null) {
			dist = mc.pointedEntity.getPosition().distanceSq(mc.player.getPosition());
			dist = Math.sqrt(dist);
		}		
		double offset = scaleOffset;
		double tan = offset / dist;
		double angle = Math.toDegrees(Math.atan(tan));
		mc.player.rotationYaw += angle;
		super.getMouseOver(partialTick);
		mc.player.rotationYaw -= angle;*/
		
		
		
		EntityPlayer p = mc.player;
		RayTraceResult raytrace = ForgeHooks.rayTraceEyes(p, 999);
		
		
		Vec3d vector = getEntityCameraTranslate(p);
		double xRenderOffset = vector.x;
		double zRenderOffset = vector.z;
		
		
		mc.player.posX -= xRenderOffset;
		mc.player.posZ -= zRenderOffset;
		mc.player.prevPosX = mc.player.lastTickPosX = mc.player.posX;
		mc.player.prevPosZ = mc.player.lastTickPosZ = mc.player.posZ;
		super.getMouseOver(partialTick);
		
		mc.player.posX += xRenderOffset;
		mc.player.posZ += zRenderOffset;
		mc.player.prevPosX = mc.player.lastTickPosX = mc.player.posX;
		mc.player.prevPosZ = mc.player.lastTickPosZ = mc.player.posZ;
		// TODO change player look direction
		
		/*
		Vec3d look = mc.player.getLookVec();
		Vec3d real = new Vec3d(look.x + 0.7f, look.y, look.z);
		
		if(mc.pointedEntity != null) {
			real = mc.pointedEntity.getPositionVector().subtract(mc.player.getPositionVector());
		}
		double rotationYawHead = -Math.atan2(real.x, real.z) * 180.0F / (float)Math.PI;
		*/
		//mc.player.cameraYaw = (float) rotationYawHead;
		
		
		
		
		/*
		mc.player.getLook(partialTick)
		
		
		Vec3d look2D = new Vec3d(look.x, 0.0f, look.z);
		look2D = look2D.scale(Math.sqrt(look2D.lengthSquared())); // normalize
		Vec3d real2D = new Vec3d(real.x, 0.0f, real.z);
		real2D = real2D.scale(Math.sqrt(real2D.lengthSquared())); // normalize
		double prod = look2D.dotProduct(real2D);
		double angle = Math.toDegrees(Math.acos(prod));
		System.out.println(angle);*/
		/*
		if (angle > 1.0f) {
			mc.player.rotationYaw += (float) angle;
		}*/
		
		
		/*
		EntityPlayer p = mc.player;
		Vec3d vector = getEntityCameraTranslate(p);
		double xRenderOffset = vector.x;
		double zRenderOffset = vector.z;
		
		if(!(Minecraft.getMinecraft().currentScreen instanceof CharacterScreen)) {
			FlyingCamera fakePlayer = null;
			GameProfile gp = new GameProfile(UUID.randomUUID(), "FakePlayer");
			
			fakePlayer = new FlyingCamera(mc.world, gp);
			//m.player.getRidingEntity();
			//pl.setInvisible(true);
			//m.world.spawnEntity(pl);
			fakePlayer.setPositionAndRotation(p.posX + xRenderOffset, p.posY, p.posZ + zRenderOffset, 
					p.rotationYaw, p.rotationPitch);
			fakePlayer.setPositionAndRotation(p.posX - 0.7f, p.posY, p.posZ, 
					p.rotationYaw, p.rotationPitch);
			//pl.prevPosX = m.player.prevPosX;
			//pl.prevPosY = m.player.prevPosY;
			//pl.prevPosZ = m.player.prevPosZ;
			fakePlayer.prevPosX = fakePlayer.lastTickPosX = fakePlayer.posX;
	        fakePlayer.prevPosY = fakePlayer.lastTickPosY = fakePlayer.posY;
	        fakePlayer.prevPosZ = fakePlayer.lastTickPosZ = fakePlayer.posZ;

	        fakePlayer.prevRotationPitch = fakePlayer.rotationPitch;
	        fakePlayer.prevRotationYaw = fakePlayer.rotationYaw;
	        fakePlayer.prevRotationYawHead = fakePlayer.rotationYawHead;
	        //m.world.spawnEntity(fakePlayer);
			mc.setRenderViewEntity(fakePlayer);
		}
		*/
		
		
		
		
		
		
		/*
		Minecraft m = Minecraft.getMinecraft();
		float f = ((EntityPlayer)(m.getRenderViewEntity())).cameraYaw;
		xRenderOffset = Math.sin(Math.toRadians(f));
		zRenderOffset = Math.cos(Math.toRadians(f));
		
		double posZ = m.getRenderViewEntity().posZ;
		double posX = m.getRenderViewEntity().posX;
		m.getRenderViewEntity().posZ = posZ + zRenderOffset * scaleOffset;
		m.getRenderViewEntity().posX = posX + xRenderOffset * scaleOffset;
		m.getRenderViewEntity().prevPosX = m.getRenderViewEntity().lastTickPosX = m.getRenderViewEntity().posX;
		m.getRenderViewEntity().prevPosY = m.getRenderViewEntity().lastTickPosY = m.getRenderViewEntity().posY;
        m.getRenderViewEntity().prevPosZ = m.getRenderViewEntity().lastTickPosZ = m.getRenderViewEntity().posZ;		
		*/
		
		
		
		

		/*m.getRenderViewEntity().posZ = posZ;
		m.getRenderViewEntity().posX = posX;
		m.getRenderViewEntity().prevPosX = m.getRenderViewEntity().lastTickPosX = m.getRenderViewEntity().posX;
		m.getRenderViewEntity().prevPosY = m.getRenderViewEntity().lastTickPosY = m.getRenderViewEntity().posY;
        m.getRenderViewEntity().prevPosZ = m.getRenderViewEntity().lastTickPosZ = m.getRenderViewEntity().posZ;		*/
	
	}
	
	public static Vec3d getEntityCameraTranslate(EntityPlayer player) {
		float head = player.rotationYawHead;
		float body = player.rotationYaw;
		head = head - 90.0f;
		if (head > 180.0f) head -= 360.0f;
		if (head < -180.0f) head += 360.0f;
		
		double xOffset = -Math.sin(Math.toRadians(head));
		double zOffset = Math.cos(Math.toRadians(head));
		
		return new Vec3d(xOffset * scaleOffset, 0.0f, zOffset * scaleOffset);

	}
}
