package pl.kalibrov.main.network.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pl.kalibrov.main.RPGMod;
import pl.kalibrov.main.gui.RPGGuiHandler;
import pl.kalibrov.main.network.packets.RPGInventoryPacket;

public class RPGInventoryPacketHandler implements IMessageHandler<RPGInventoryPacket, IMessage>{

		
	@Override
	public IMessage onMessage(RPGInventoryPacket message, MessageContext ctx) {
		EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
		int indexGui = message.toSend;
		
		BlockPos pos = serverPlayer.getPosition();
		serverPlayer.openGui(RPGMod.instance, indexGui, serverPlayer.world, pos.getX(), pos.getY(), pos.getZ());
				
		// No response packet
		return null;
	}

}
