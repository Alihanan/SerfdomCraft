package pl.kalibrov.main.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class RPGInventoryPacket implements IMessage{

	public int toSend = 0;
	
	public RPGInventoryPacket() {
		
	}
	
	public RPGInventoryPacket(int msg) {
		toSend = msg;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		toSend = buf.readInt();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(toSend);
	}

}
