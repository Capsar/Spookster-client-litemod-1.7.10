package net.spookysquad.spookster.mod.mods;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventPacketSend;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;

import org.lwjgl.input.Keyboard;

public class NoFall extends Module {

	public NoFall() {
		super(new String[] { "NoFall" }, "Prevent taking fall damage when falling off of a high distance.", Type.EXPLOITS, Keyboard.KEY_N, 0xFF13C422);
	}

	@Override
	public boolean onEnable() {
		// TODO: Take Damage
		return true;
	}
	
	@Override
	public void onEvent(Event e) {
		if(e instanceof EventPacketSend) {
			EventPacketSend event = (EventPacketSend) e;
			
			if(event.getPacket() instanceof C03PacketPlayer) {
				C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();
				
				if(packet.func_149466_j()) {
					event.cancel();
					
				}
			}
		}
	}	
}
