package pl.kalibrov.main.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import pl.kalibrov.main.RPGMod;
import pl.kalibrov.main.network.handlers.RPGHotbarPacketHandler;
import pl.kalibrov.main.network.handlers.RPGInventoryPacketHandler;
import pl.kalibrov.main.network.packets.RPGHotbarPacket;
import pl.kalibrov.main.network.packets.RPGInventoryPacket;

public class RPGNetwork {
			
	public static final SimpleNetworkWrapper DISPATCHER = NetworkRegistry.INSTANCE.newSimpleChannel(
			RPGMod.MODID);
	private static int id = 0;
	
	public static void init() {
		// We will only send to server
		// IDs are used to classify custom packets
		DISPATCHER.registerMessage(RPGInventoryPacketHandler.class, RPGInventoryPacket.class, id++, Side.SERVER);
		DISPATCHER.registerMessage(RPGHotbarPacketHandler.class, RPGHotbarPacket.class, id++, Side.SERVER);
	}
}
