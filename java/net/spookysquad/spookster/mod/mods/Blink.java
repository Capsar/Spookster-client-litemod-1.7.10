package net.spookysquad.spookster.mod.mods;

import java.util.ArrayList;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventPacketGet;
import net.spookysquad.spookster.event.events.EventPacketSend;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;

import org.lwjgl.input.Keyboard;

public class Blink extends Module {

	private ArrayList<C03PacketPlayer> packets = new ArrayList<C03PacketPlayer>();
	
	public Blink() {
		super(new String[] { "Blink" }, "POOF! You're gone", Type.MOVEMENT, Keyboard.KEY_LBRACKET, 0xFFaa676a);
	}
	
	public boolean onDisable() {
		for(C03PacketPlayer packet: packets) {
			// TODO: Not sure how to send this with the EventPacket stuffs
		}
		
		packets.clear();
		return super.onDisable();
	}

	@Override
	public void onEvent(Event event) {
		if(event instanceof EventPacketSend) {
			EventPacketSend packetSend = (EventPacketSend) event;
			if(packetSend.getPacket() instanceof C03PacketPlayer) {
				packets.add((C03PacketPlayer) packetSend.getPacket());
				event.cancel();
			}
		}
	}

}
