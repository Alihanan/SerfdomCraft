package pl.modding.network.inventory;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pl.modding.client.gui.KeyHandler;
import pl.modding.main.RPGMod;

public class RPGHotbarPacketHandler implements IMessageHandler<RPGHotbarPacket, IMessage> {

	@Override
	public IMessage onMessage(RPGHotbarPacket message, MessageContext ctx) {
		EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
		int indexGui = message.toSend;
		
		KeyHandler.removeHotbarSlots(serverPlayer);
		
		// No response packet
		return null;
	}

}
