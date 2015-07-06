package net.spookysquad.spookster.mod;

import java.util.ArrayList;

import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.Listener;
import net.spookysquad.spookster.event.events.EventKeyPressed;
import net.spookysquad.spookster.event.events.EventMouseClicked;
import net.spookysquad.spookster.manager.Manager;
import net.spookysquad.spookster.mod.mods.HUD;
import net.spookysquad.spookster.mod.mods.Triggerbot;

public class ModuleManager extends Manager implements Listener {
	
	private Spookster spookster;
	private ArrayList<Module> modules = new ArrayList<Module>();
	
	public void init(Spookster spookster) {
		this.spookster = spookster;
		
		spookster.eventManager.registerListener(this);
		
		registerModule(new HUD());
		registerModule(new Triggerbot());
	}
	
	public void deinit(Spookster spookster) {
		spookster.eventManager.unregisterListener(this);
	}
	
	public void registerModule(Module module) {
		modules.add(module);
	}
	
	public void unregisterModule(Module module) {
		modules.remove(module);
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
			for(Module m : getModules()) {
				if(m.getKeyCode() == pressed.getKey()) {
					m.toggle();
				}
			}
		} else if(event instanceof EventMouseClicked) {
			EventMouseClicked pressed = (EventMouseClicked) event;
			for(Module m : getModules()) {
				if(m.getKeyCode() + 256 == pressed.getButton()) {
					m.toggle();
				}
			}
		}
	}

}
