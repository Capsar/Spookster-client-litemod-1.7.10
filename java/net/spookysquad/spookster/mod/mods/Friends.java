package net.spookysquad.spookster.mod.mods;

import java.util.Arrays;
import java.util.List;

import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventMouseClicked;
import net.spookysquad.spookster.event.events.EventPreMotion;
import net.spookysquad.spookster.mod.HasValues;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.mod.HasValues.Value;
import net.spookysquad.spookster.utils.PlayerUtil;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Friends extends Module {

	public Friends() {
		super(new String[] { "Friends" }, "Modules adapt to the fact there are team members.", Type.MISC, Keyboard.KEY_NONE, -1);
	}

	
	
	public void onEvent(Event event) {
		if(event instanceof EventMouseClicked) {
			
		}
	}

}
