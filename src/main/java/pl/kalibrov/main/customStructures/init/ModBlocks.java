package pl.kalibrov.main.customStructures.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import pl.kalibrov.main.RPGMod;
import pl.kalibrov.main.customStructures.objects.blocks.TradePostBlock;
import pl.kalibrov.main.customStructures.objects.blocks.TradePostBlock.EnumTradePostPart;
import pl.kalibrov.main.customStructures.objects.support.SitEntity;

@EventBusSubscriber(modid = RPGMod.MODID)
public class ModBlocks {
	
	/**
	 * Register custom blocks (Client + Server)
	 * NO RENDERER ALLOWED!
	 */
	
	public static void init() {
		
		initTradePostBlocks();
				
	}
	
	private static void initTradePostBlocks() {
		// Register all 6 possible parts
		for(int i = 0; i < TradePostBlock.tradePostBlock.length; i++) {
			TradePostBlock.tradePostBlock[i] = new TradePostBlock(Material.CLOTH, 
					"tradepost_block_" + (EnumTradePostPart.values())[i].getName(), 
					(EnumTradePostPart.values())[i], i);
			TradePostBlock.tradePostBlock[i].setHardness(0.2F);
			String regname = "tradepost_block_" + (EnumTradePostPart.values())[i].getName();
			System.out.println("[SERFDOMCRAFT]: registering block: " + regname);
		}
		EntityRegistry.registerModEntity(new ResourceLocation(RPGMod.MODID, "sit_entity"), 
				SitEntity.class, "entity_sit", 0, RPGMod.instance, 256, 20, false);
	}
	
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		// String is OK for 1.12.2
		//GameRegistry.registerTileEntity(UpdaterTileEntity.class, "serfdomcraft:updater_tile_entity");
		event.getRegistry().registerAll(TradePostBlock.tradePostBlock);	
	}
	
	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
		for(int i = 0; i < TradePostBlock.tradePostBlock.length; i++) {
			TradePostBlock bl = TradePostBlock.tradePostBlock[i];
			TradePostBlock.tradePostBlock_item[i] = new ItemBlock(bl).
					setRegistryName(bl.getRegistryName());
		}
		
		event.getRegistry().registerAll(TradePostBlock.tradePostBlock_item);
	}
	
	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
		//registerRender(Item.getItemFromBlock(tradePostBlock));
	}
}
