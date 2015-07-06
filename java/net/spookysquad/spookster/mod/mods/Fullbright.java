package net.spookysquad.spookster.mod.mods;

import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventPostMotion;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;

public class Fullbright extends Module {

	private final float[] tempBrightnessTable = new float[16];
	
	public Fullbright() {
		super(new String[] { "Fullbright" }, "", Type.RENDER, Keyboard.KEY_B, 0xFFE1E1C8);
	}
	
	public boolean onEnable() {
		System.arraycopy(Wrapper.getWorld().provider.lightBrightnessTable, 0, tempBrightnessTable, 0, 16);
		return true;
	}
	
	public boolean onDisable() {
		System.arraycopy(tempBrightnessTable, 0, Wrapper.getWorld().provider.lightBrightnessTable, 0, 16);
		return true;
	}

	public void onEvent(Event event) {
		if(event instanceof EventPostMotion) {
			for(int i = 0; i < 16; i++) {
				Wrapper.getWorld().provider.lightBrightnessTable[i] = 0.7F;
			}
		}
	}

}
