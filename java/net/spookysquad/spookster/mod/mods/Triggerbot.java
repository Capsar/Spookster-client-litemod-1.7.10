package net.spookysquad.spookster.mod.mods;

import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;

public class Triggerbot extends Module {

	public Triggerbot() {
		super("Triggerbot", "When your cursor is over an enemy, it attacks.", Type.COMBAT, "R", 0xFF0A9D0F);
	}

	@Override
	public void onEvent(Event event) {
		
	}

	
	
}
