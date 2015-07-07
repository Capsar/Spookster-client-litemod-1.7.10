package net.spookysquad.spookster.mod.mods;

import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventPreMotion;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;

import org.lwjgl.input.Keyboard;

public class GangsterWalk extends Module {

	public GangsterWalk() {
		super(new String[] { "GangsterWalk" }, "Look like a gangster", Type.RENDER, Keyboard.KEY_APOSTROPHE, -1);
	}

	public void onEvent(Event event) {
		if(event instanceof EventPreMotion) {
			getPlayer().cameraYaw -= 0.5F;
		}
	}

}
