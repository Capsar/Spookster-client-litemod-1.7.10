package net.spookysquad.spookster.command;

import java.util.ArrayList;

import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.manager.Manager;
import net.spookysquad.spookster.render.external.console.MessageType;
import net.spookysquad.spookster.utils.Wrapper;

public class CommandManager extends Manager {

	private ArrayList<Command> commands = new ArrayList<Command>();

	@Override
	public void init(Spookster spookster) {
	}

	@Override
	public void deinit(Spookster spookster) {
	}

	public boolean onCommand(String message) {
		for (Command command : commands) {
			if (command.onCommand(message)) {
				Wrapper.logChat(MessageType.NOTIFCATION, "Im high af right now, but you did a command with the name: " + command.getName());
				return false;
			}
		}
		return true;
	}

	public ArrayList<Command> getCommands() {
		return commands;
	}

	public void setCommands(ArrayList<Command> commands) {
		this.commands = commands;
	}

}
