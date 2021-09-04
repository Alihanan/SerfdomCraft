package pl.modding.network.inventory;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pl.modding.client.gui.inventories.RPGGuiHandler;
import pl.modding.main.RPGMod;

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
