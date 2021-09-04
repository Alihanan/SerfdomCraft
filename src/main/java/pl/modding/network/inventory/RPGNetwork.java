package pl.modding.network.inventory;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import pl.modding.reference.Reference;

public class RPGNetwork {
		
	
	public static final SimpleNetworkWrapper DISPATCHER = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
	private static int id = 0;
	
	public static void init() {
		// We will only send to server
		// IDs are used to classify custom packets
		DISPATCHER.registerMessage(RPGInventoryPacketHandler.class, RPGInventoryPacket.class, id++, Side.SERVER);
		DISPATCHER.registerMessage(RPGHotbarPacketHandler.class, RPGHotbarPacket.class, id++, Side.SERVER);
	}
}
