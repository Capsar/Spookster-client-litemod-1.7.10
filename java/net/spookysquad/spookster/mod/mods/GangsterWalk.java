package net.spookysquad.spookster.mod.mods;

import java.util.Arrays;
import java.util.List;

import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventPreMotion;
import net.spookysquad.spookster.mod.HasValues;
import net.spookysquad.spookster.mod.HasValues.Value;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;

import org.lwjgl.input.Keyboard;

public class GangsterWalk extends Module implements HasValues {

	public GangsterWalk() {
		super(new String[] { "GangsterWalk" }, "Look like a gangster", Type.RENDER, Keyboard.KEY_APOSTROPHE, -1);
	}

	public void onEvent(Event event) {
		if(event instanceof EventPreMotion) {
			getPlayer().cameraYaw -= cameraOffset;
		}
	}
	
	public double cameraOffset = 0.5;
	private String CAMERAOFFSET = "Camera Offset";
	private List<Value> values = Arrays.asList(new Value[] { new Value(CAMERAOFFSET, 0.1D, 5D, 0.1F) });

	@Override
	public List<Value> getValues() {
		return values;
	}

	@Override
	public Object getValue(String n) {
		if (n.equals(CAMERAOFFSET)) return cameraOffset;
		return null;
	}

	@Override
	public void setValue(String n, Object v) {
		if (n.equals(CAMERAOFFSET)) cameraOffset = (Math.round((Double) v * 10) / 10.0D);
	}

}
