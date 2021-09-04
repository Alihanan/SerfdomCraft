package pl.kalibrov.main;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import pl.kalibrov.main.customStructures.init.ModBlocksClient;
import pl.kalibrov.main.customStructures.init.ModBlocks;
import pl.kalibrov.main.customStructures.init.ModItems;
import pl.kalibrov.main.customStructures.objects.blocks.TradePostBlock;
import pl.kalibrov.main.customStructures.objects.blocks.TradePostBlock.EnumTradePostPart;
import pl.kalibrov.main.gui.RPGGuiHandler;
import pl.kalibrov.main.network.RPGNetwork;

@EventBusSubscriber(modid = RPGMod.MODID)
public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		// Register all custom packets
		RPGNetwork.init();
		// Register custom inventory gui as handler (To open on server side with containers)
		NetworkRegistry.INSTANCE.registerGuiHandler(RPGMod.instance, new RPGGuiHandler());
		ModBlocks.init();		
	}	
	
	public void Init(FMLInitializationEvent event) {		
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	public void serverStarted(FMLServerStartedEvent event) {
		
	}
	public void serverStopping(FMLServerStoppingEvent event) {
		
	}
	
	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		//event.getRegistry().registerAll(ItemInit.items.toArray(new Item[0]));
	}
	
	
	public void registerItemRenderer(Item item, int meta, String id) {
		
	}
	
	public void registerRenderers() {		
	}
	public void registerRecipes() {		
	}
	
	
	public boolean mountTent(BlockPos pos, EntityPlayer player) {
		return false;
	}
	
	public EntityPlayer getTentRider(BlockPos pos) {
		
		return null;
	}
	
	public void dismountTent(BlockPos pos) {
		
	}
}
