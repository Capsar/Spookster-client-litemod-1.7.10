package net.spookysquad.spookster.mod.mods;

import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventPostHudRender;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.utils.FontUtil;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Nametag extends Module {

	public Nametag() {
		super(new String[] { "Nametag" }, "NAMETAGS kdshfgjkdtraw7uei56348758sdf", Type.RENDER, Keyboard.KEY_M, -1);
	}

	public void onEvent(Event event) {
		if(event instanceof EventPostHudRender) {
			
		}
	}

}
