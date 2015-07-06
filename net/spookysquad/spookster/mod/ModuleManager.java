package net.spookysquad.spookster.mod;

import java.util.ArrayList;

import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.manager.Manager;

public class ModuleManager extends Manager {
	
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

}
