package pl.kalibrov.main.server;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.relauncher.Side;
import pl.kalibrov.main.CommonProxy;
import pl.kalibrov.main.RPGMod;
import pl.kalibrov.main.customStructures.init.ModBlocksClient;
import pl.kalibrov.main.customStructures.init.ModItems;

@EventBusSubscriber(modid = RPGMod.MODID, value = Side.SERVER)
public class ServerProxy extends CommonProxy{
	public static RPGClassServerSystem classSystem = null;
	
	private static final HashMap<BlockPos, EntityPlayer> occupiedTents = new HashMap<>();
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		classSystem = new RPGClassServerSystem();
		super.preInit(event);
		//ModBlocks.init();
		ModItems.init();
	}	
	@Override
	public void Init(FMLInitializationEvent event) {		
		super.Init(event);
	}
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	@Override
	public void serverStarted(FMLServerStartedEvent event) {		
		System.out.println("[RPG_MOD] Server WorldInit fired");
	}
	@Override
	public void serverStopping(FMLServerStoppingEvent event) {
		classSystem.shutdown();
	}
	@SubscribeEvent
	public static void playerJoined(PlayerLoggedInEvent event) {		
		classSystem.addNewPlayer(event.player);
	}
	
	@Override
	public boolean mountTent(BlockPos pos, EntityPlayer player) {
		EntityPlayer prev = this.getTentRider(pos);
		if(prev != null) {
			return false;
		}
		
		occupiedTents.put(pos, player);
		return true;
	}
	
	@Override
	public EntityPlayer getTentRider(BlockPos pos) {
		if(occupiedTents.containsKey(pos)) {
			return occupiedTents.get(pos);
		}
		return super.getTentRider(pos);
	}
	
	@Override
	public void dismountTent(BlockPos pos) {
		if(occupiedTents.containsKey(pos)) {
			occupiedTents.remove(pos); 
		}
		else {
			System.out.println("NO BLOCKPOS FOUND!");
			System.out.println(pos.toString());
			
			System.out.println(occupiedTents);//.get(occupiedTents.keySet().iterator().next())
		}
	}
}
