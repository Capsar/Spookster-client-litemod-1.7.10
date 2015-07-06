package net.spookysquad.spookster.event;

public class Event {

	private boolean isCancelled = false;
	
	public void cancel() {
		this.isCancelled = true;
	}
	
	public boolean isCancelled() {
		return isCancelled;
	}
	
}
