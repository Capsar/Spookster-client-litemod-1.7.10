package net.spookysquad.spookster.event.events;

import net.spookysquad.spookster.event.Event;

public class EventKeyPressed extends Event {

	private int key;

	public EventKeyPressed(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}
	
}
