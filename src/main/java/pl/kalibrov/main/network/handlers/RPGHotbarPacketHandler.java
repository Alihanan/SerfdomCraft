package pl.kalibrov.main.network.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pl.kalibrov.main.network.packets.RPGHotbarPacket;

public class RPGHotbarPacketHandler implements IMessageHandler<RPGHotbarPacket, IMessage> {

	@Override
	public IMessage onMessage(RPGHotbarPacket message, MessageContext ctx) {
		EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
		
		int indexGui = message.toSend;
		
		for(int i = 4; i < 9; i++) { // Remove items from 5 - 9
			serverPlayer.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
		}	
		
		// No response packet
		return null;
	}

}
