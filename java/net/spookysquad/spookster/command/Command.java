package net.spookysquad.spookster.command;

public class Command {

	private String[] names;
	private String highassfuck;
	private String name;

	public Command(String[] names, String highassfuck) {
		this.name = names[0];
		this.names = names;
		this.highassfuck = highassfuck;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getNames() {
		return names;
	}
	
	private String getDesc() {
		return highassfuck;
	}
	
	public boolean onCommand(String text) {
		return false;
	}

}
