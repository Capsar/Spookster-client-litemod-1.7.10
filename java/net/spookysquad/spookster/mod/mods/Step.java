package net.spookysquad.spookster.mod.mods;

import java.util.Arrays;
import java.util.List;

import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventPreMotion;
import net.spookysquad.spookster.mod.HasValues;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.mod.HasValues.Value;
import net.spookysquad.spookster.utils.PlayerUtil;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;

public class Step extends Module implements HasValues {

	public Step() {
		super(new String[] { "Step" }, "Lets you Step up blocks", Type.MOVEMENT, Keyboard.KEY_PERIOD, 0xFF8BFFA1);
	}

	public void onEvent(Event event) {
		if(event instanceof EventPreMotion) {
			if(LegitStep) {
				if(PlayerUtil.getPlayer().isCollidedHorizontally) {
					if(PlayerUtil.getPlayer().onGround) {
						PlayerUtil.getPlayer().jump();
					}
				}
			}
			
			if(VanillaStep) {
				Wrapper.getPlayer().stepHeight = (float) stepHeight;
			} else {
				Wrapper.getPlayer().stepHeight = 0.5F;
			}
		}
	}
	
	public boolean onDisable() {
		Wrapper.getPlayer().stepHeight = 0.5F;
		return super.onDisable();
	}

	public boolean VanillaStep = true;
	public boolean LegitStep = false;
	public boolean BypassStep = false;
	public double stepHeight = 0.5;
	
	private String STEPHEIGHT = "Step Height", VANILLA = "Vanilla Step", LEGIT = "Legit step", BYPASS = "Bypass step (NOT IMPLEMENTED)", STEPMODE = "Step Mode";
	private List<Value> values = Arrays.asList(new Value[] { new Value(STEPHEIGHT, 0.5D, 10D, 0.1F),
			new Value(STEPMODE, false, Arrays.asList(new Value(VANILLA, false, true), new Value(LEGIT, false, true), new Value(BYPASS, false, true))) });

	@Override
	public List<Value> getValues() {
		return values;
	}

	@Override
	public Object getValue(String n) {
		if (n.equals(STEPHEIGHT)) return stepHeight;
		else if (n.equals(VANILLA)) return VanillaStep;
		else if (n.equals(LEGIT)) return LegitStep;
		else if (n.equals(BYPASS)) return BypassStep;
		return null;
	}

	@Override
	public void setValue(String n, Object v) {
		if (n.equals(STEPHEIGHT)) stepHeight = (Math.round((Double) v * 10) / 10.0D);
		else if (n.equals(VANILLA)) VanillaStep = (Boolean) v;
		else if (n.equals(LEGIT)) LegitStep = (Boolean) v;
		else if (n.equals(BYPASS)) BypassStep = (Boolean) v;
	}

}
