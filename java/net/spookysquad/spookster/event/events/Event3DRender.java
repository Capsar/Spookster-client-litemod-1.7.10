package net.spookysquad.spookster.event.events;

import net.spookysquad.spookster.event.Event;

public class Event3DRender extends Event {

	private float partialTicks;

	public Event3DRender(float partialTicks) {
		this.partialTicks = partialTicks;
	}

	public float getPartialTicks() {
		return partialTicks;
	}
	
}
