package net.spookysquad.spookster.mod.mods;

import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;

public class XRay extends Module {

	public XRay() {
		super(new String[] { "X-Ray", "XRay" }, "Magic shit Nigguh", Type.RENDER, Keyboard.KEY_X, 0xFFB2B2B2);
	}

	public void onEvent(Event event) {
		
	}
	
	public boolean onEnable() {
		Wrapper.getMinecraft().renderGlobal.loadRenderers();
		return true;
	}

	public boolean onDisable() {
		Wrapper.getMinecraft().renderGlobal.loadRenderers();
		return true;
	}

}
