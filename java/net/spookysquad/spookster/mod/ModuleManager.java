package net.spookysquad.spookster.mod;

import java.util.ArrayList;

import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.Listener;
import net.spookysquad.spookster.event.events.EventKeyPressed;
import net.spookysquad.spookster.event.events.EventMouseClicked;
import net.spookysquad.spookster.manager.Manager;

public class ModuleManager extends Manager implements Listener {
	
	private ArrayList<Module> modules = new ArrayList<Module>();
	
	public void init(Spookster spookster) {
		
	}
	
	public void deinit(Spookster spookster) {
		
	}
	
	/**
	 * Returns the modules ArrayList
	 * @return
	 */
	public ArrayList<Module> getModules() {
		return modules;
	}
	
	/**
	 * Returns a module by class
	 * @param moduleClass
	 * @return
	 */
	public Module getModule(Class<? extends Module> moduleClass) {
		for(Module mod: modules) {
			if(mod.getClass() == moduleClass) {
				return mod;
			}
		}
		
		return null;
	}

	@Override
	public void onEvent(Event event) {
		if(event instanceof EventKeyPressed) {
			EventKeyPressed pressed = (EventKeyPressed) event;
			
		} else if(event instanceof EventMouseClicked) {
			EventMouseClicked pressed = (EventMouseClicked) event;
			
		}
	}

}
