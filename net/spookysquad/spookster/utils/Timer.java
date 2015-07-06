package net.spookysquad.spookster.utils;

/**
 * Basic Timer class used for delays and such.
 * @author Halalaboos
 */
public class Timer {

	private long lastCheck = getSystemTime();

	/**
	 * @return The current system time (in milliseconds).
	 */
	private long getSystemTime() {
		return System.nanoTime() / (long) (1E6);
	}

	/**
	 * @return The amount of time (in milliseconds) since the <code>lastCheck</code>.
	 */
	private long getTimePassed() {
		return getSystemTime() - lastCheck;
	}

	/**
	 * Checks if the specified amount of second(s) has passed.
	 *
	 * @param seconds	The specified seconds since last check.
	 * @return			<code>true</code> if the time has passed, else <code>false</code>.
	 */
	public boolean hasReach(float seconds) {
		return getTimePassed() >= (seconds * 1000);
	}
	
	/**
	 * Checks if the specified amount of millisecond(s) has passed.
	 * 
	 * @param milliseconds	The specified seconds since last check.
	 * @return				<code>true</code> if the time has passed, else <code>false</code>.
	 * @author TehNeon
	 */
	public boolean hasReachMS(float milliseconds) {
		return getTimePassed() >= milliseconds;
	}

	/**
	 * Resets the <code>lastCheck</code>
	 */
	public void reset() {
		lastCheck = getSystemTime();
	}
}