package net.spookysquad.spookster.mod;

import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.event.Listener;

public abstract class Module implements Listener {
	private Spookster spookster;
	private String moduleName;
	
	public Module(Spookster spookster, String moduleName) {
		this.spookster = spookster;
		this.moduleName = moduleName;
	}
	
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getModuleName() {
		return this.moduleName;
	}
}
