package net.spookysquad.spookster;

import java.util.ArrayList;

import net.spookysquad.spookster.event.EventManager;
import net.spookysquad.spookster.manager.Manager;
import net.spookysquad.spookster.mod.ModuleManager;

public class Spookster {

	private ArrayList<Manager> managers = new ArrayList<Manager>();
	public static Spookster instance;
	
	public EventManager eventManager;
	public ModuleManager moduleManager;
	
	public void init() {
		instance = this;
		managers.add(eventManager = new EventManager());
		managers.add(moduleManager = new ModuleManager());
		
		for(Manager manager: managers) {
			manager.init(this);
		}
		
		
		Runtime.getRuntime().addShutdownHook(new Thread("Shutdown Thread") {
			@Override
			public void run() {
				for(Manager manager: managers) {
					manager.deinit(Spookster.this);
				}
			}
		});
	}
}
