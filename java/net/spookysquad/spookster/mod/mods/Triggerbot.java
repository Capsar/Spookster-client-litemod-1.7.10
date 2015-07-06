package net.spookysquad.spookster.mod.mods;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventPreMotion;
import net.spookysquad.spookster.mod.HasValues;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.utils.TimeUtil;

import org.lwjgl.input.Keyboard;

public class Triggerbot extends Module implements HasValues {

	public Triggerbot() {
		super(new String[] { "Triggerbot" }, "When your cursor is over an enemy, it attacks.", Type.COMBAT, Keyboard.KEY_R, 0xFF0A9D0F);
	}

	public int aps = 5;
	public int aimspeed = 2;
	public int aimspeedbody = 3;
	public double range = 4.2;
	private static EntityPlayer livingbase;
	TimeUtil time = new TimeUtil();
	
	@Override
	public void onEvent(Event event) {
		if(event instanceof EventPreMotion) {
			
		}
	}

	@Override
	public List<Value> getValues() {
		return null;
	}

	@Override
	public Object getValue(String n) {
		return null;
	}

	@Override
	public void setValue(String n, Object v) {
		
	}
}
