package net.spookysquad.spookster.utils;

import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.Packet;

public class PacketUtil {

	public static void addPacket(Packet packet) {
		Wrapper.getMinecraft().getNetHandler().addToSendQueue(packet);
	}
	
	public static void sendPacket(Packet packet) {
		Wrapper.getMinecraft().getNetHandler().getNetworkManager().scheduleOutboundPacket(packet, new GenericFutureListener[0]);
	}
	
}
