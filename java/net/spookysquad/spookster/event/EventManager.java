package net.spookysquad.spookster.event;

import java.util.ArrayList;

import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.manager.Manager;

public class EventManager extends Manager {

	private ArrayList<Listener> listeners = new ArrayList<Listener>();
	
	public void init(Spookster spookster) {
		
	}
	
	public void deinit(Spookster spookster) {
		
	}
	
	public void registerListener(Listener listener) {
		listeners.add(listener);
	}
	
	public void unregisterListener(Listener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Sends the event to all listeners
	 * @param event
	 */
	public void callEvent(Event event) {
		for(Listener listener: listeners) {
			listener.onEvent(event);
		}
	}
	
	/**
	 * Gets a listener by class (Pretty useless)
	 * @param listenerClass
	 * @return
	 */
	public Listener getListener(Class<? extends Listener> listenerClass) {
		for(Listener listener: listeners) {
			if(listener.getClass() == listenerClass) {
				return listener;
			}
		}
		return null;
	}
}
