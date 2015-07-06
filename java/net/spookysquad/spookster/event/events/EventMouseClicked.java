package net.spookysquad.spookster.event.events;

import net.spookysquad.spookster.event.Event;

public class EventMouseClicked extends Event {

	private int button;

	public EventMouseClicked(int button) {
		this.button = button;
	}

	public int getButton() {
		return button;
	}

}
