package net.spookysquad.spookster.mod.mods;

import java.util.Arrays;
import java.util.List;

import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventPreMotion;
import net.spookysquad.spookster.mod.HasValues;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.mod.HasValues.Value;
import net.spookysquad.spookster.utils.PlayerUtil;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Teams extends Module {

	public Teams() {
		super(new String[] { "Teams" }, "Allows Triggerbot to not attack teammates", Type.MISC, Keyboard.KEY_NONE, -1);
	}

	public void onEvent(Event event) {
		if(event instanceof EventPreMotion) {
			
		}
	}

}
