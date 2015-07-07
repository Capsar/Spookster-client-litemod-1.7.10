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

public class Title extends Module {

	public Title() {
		super(new String[] { "Title" }, "Shows active hacks in the title of the window", Type.MISC, Keyboard.KEY_GRAVE, -1);
	}

	public void onEvent(Event event) {
		if(event instanceof EventPreMotion) {
			String theTitle = "";
			for(Module mod: Spookster.instance.moduleManager.getModules()) {
				if(mod.isEnabled()) {
					theTitle = theTitle + mod.getDisplay() + ", ";
				}
			}
			
			Display.setTitle(theTitle.substring(0, theTitle.length() - 2));
		}
	}
	
	public boolean onDisable() {
		Display.setTitle("Minecraft 1.7.10");
		
		return super.onDisable();
	}

}
