package net.spookysquad.spookster.command;

public class Command {

	private String[] names;
	private String description;
	private String name;

	public Command(String[] names, String description) {
		this.name = names[0];
		this.names = names;
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getNames() {
		return names;
	}
	
	private String getDesc() {
		return description;
	}
	
	public boolean onCommand(String text, String cmd, String[] args) {
		return false;
	}

}
