package pl.kalibrov.main.customStructures.init;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pl.kalibrov.main.RPGMod;
import pl.kalibrov.main.customStructures.objects.blocks.IHasComplexHitbox;
import pl.kalibrov.main.customStructures.objects.blocks.TradePostBlock;
import pl.kalibrov.main.customStructures.objects.blocks.TradePostBlock.EnumTradePostPart;
import pl.kalibrov.main.customStructures.objects.support.SitEntity;

@EventBusSubscriber(modid = RPGMod.MODID, value=Side.CLIENT)
public class ModBlocksClient {
	
	/**
	 * Register custom blocks renderer (Client only)
	 */
	
	public static void init() {
		 
		for(int i = 0; i < TradePostBlock.tradePostBlock.length; i++) {
			registerRender(Item.getItemFromBlock(TradePostBlock.tradePostBlock[i]));
		}
		EntityRegistry.registerModEntity(new ResourceLocation(RPGMod.MODID, "sit_entity"), 
				SitEntity.class, "entity_sit", 0, RPGMod.instance, 256, 20, false);
				
	}
	
	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
		//registerRender(Item.getItemFromBlock(tradePostBlock));
	}
	
	public static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, 
				new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	@SuppressWarnings("static-access")
	@SubscribeEvent
	public static void drawBlockHighlightEvent(DrawBlockHighlightEvent event) {
		// change custom block hightlight
		EntityPlayer player = event.getPlayer();
		if (player == null) {
			return;
		}
		World world = player.world;
		if (world == null) {
			return;
		}
		
		
		RayTraceResult rayTraceResult = event.getTarget();
		if (rayTraceResult == null || 
			rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) {
			return;
		}
		
	    BlockPos targetedPos = rayTraceResult.getBlockPos();
		IBlockState iblockState = world.getBlockState(targetedPos);
		if (iblockState.getMaterial() == Material.AIR || 
			!world.getWorldBorder().contains(targetedPos)) {
			return;
		}
		
		
		
		
		if(!isCustomBlock(iblockState.getBlock())) {
			// vanilla/other mods are highlighted normally
			return;
		}
		event.setCanceled(true);
		
		IHasComplexHitbox block = (IHasComplexHitbox) iblockState.getBlock();
		
		GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(2.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        float partialTicks = event.getPartialTicks();
        double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks;
        double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks;
        double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks;
        
        
       
        
    	Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        
        double[] bbs = block.getComplexCollisionList(targetedPos, world);
        for(int i = 0; i < bbs.length / 4; i++) {
        	bufferbuilder.pos(
        			bbs[4*i]-d3, bbs[4*i + 1]-d4, bbs[4*i + 2]-d5)
        	.color(0.0F, 0.0F, 0.0F, 0.6F * (float)(bbs[4*i + 3])).endVertex();
        }
        
        
        /*
         * UNCOMMENT TO DEBUG BLOCK COLLIDERS
         * 
        List<AxisAlignedBB> bbs = block.getComplexTest(targetedPos, world);
        for(AxisAlignedBB aabb : bbs) {
        	Minecraft.getMinecraft().renderGlobal.drawSelectionBoundingBox(
            		aabb
            		.offset(-d3, -d4, -d5)
            		.grow(0.0020000000949949026D)
            		, 0.0F, 0.0F, 0.0F, 0.4F);
        }*/
        
        tessellator.draw();
        
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
		
	}
	
	private static boolean isCustomBlock(Block block) {
		if(block instanceof IHasComplexHitbox) {
			return true;
		}
		return false;
	}
	
	private static void drawBoundingBoxCustom(BufferBuilder buffer, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha)
    {
        buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        
        buffer.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
    }
}
