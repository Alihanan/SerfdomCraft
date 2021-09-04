package pl.kalibrov.main.customStructures.init;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pl.kalibrov.main.RPGMod;
import pl.kalibrov.main.customStructures.objects.blocks.TradePostBlock;
import pl.kalibrov.main.customStructures.objects.items.ItemBasic;
import pl.kalibrov.main.customStructures.objects.items.TradePostItem;

@Mod.EventBusSubscriber(modid=RPGMod.MODID)
public class ModItems {
	
	static Item tradePostItem;
	
	public static void init() {
		tradePostItem = new TradePostItem("tradepost_item", TradePostBlock.tradePostBlock[0]);
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(tradePostItem);
	}
	
	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
		registerRender(tradePostItem);
	}
	
	public static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, 
				new ModelResourceLocation( item.getRegistryName(), "inventory"));
	}
	
	public static Item getTradePostItem() {
		return tradePostItem;
	}
}
