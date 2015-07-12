package net.spookysquad.spookster.mod.mods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.command.Command;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventMouseClicked;
import net.spookysquad.spookster.event.events.EventPreMotion;
import net.spookysquad.spookster.mod.HasValues;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.mod.HasValues.Value;
import net.spookysquad.spookster.render.external.console.MessageType;
import net.spookysquad.spookster.utils.PlayerUtil;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Friends extends Module implements HasValues {

	public Friends() {
		super(new String[] { "Friends" }, "Modules adapt to the fact there are team members.", Type.FRIENDS, -1, -1);
		this.toggle(false);
		Spookster.instance.commandManager.getCommands().add(new Command(new String[] { "addfriend", "addfr", "af", "friendadd", "fadd", "fa" }, "Add friends") {
			@Override
			public boolean onCommand(String text, String cmd, String[] args) {
				for (String name : getNames()) {
					if (cmd.equalsIgnoreCase(name)) {
						if (args.length > 1) {
							String username = args[1];
							String alias = (args.length > 2 ? args[2] : username);
							if (args.length > 2) {
								alias = "";
								for (int i = 2; i < args.length; i++) {
									alias += args[i] + " ";
								}
								alias = alias.substring(0, alias.length() - 1);
							}
							Friend isFriend = getFriend(username);
							if (isFriend != null) {
								friends.remove(isFriend);
								friends.add(new Friend(username, alias));
								Wrapper.logChat(MessageType.NOTIFCATION, "Changed friend " + username + ", with alias " + alias + "!");
							} else {
								friends.add(new Friend(username, alias));
								Wrapper.logChat(MessageType.NOTIFCATION, "Added friend " + username + ", with alias " + alias + "!");
							}
						} else {
							Wrapper.logChat(MessageType.NOTIFCATION, "Invalid syntax! Use:");
							Wrapper.logChat(MessageType.NOTIFCATION, cmd + " <username> [alias] - Adds friend");
						}
						return true;
					}
				}
				return super.onCommand(text, cmd, args);
			}
		});
		Spookster.instance.commandManager.getCommands().add(new Command(new String[] { "delfriend", "delfr", "df", "frienddel", "fdel", "fd", "fr", "frem", "friendremove", "rf", "removefr", "removefriend" }, "Delete friends") {
			@Override
			public boolean onCommand(String text, String cmd, String[] args) {
				for (String name : getNames()) {
					if (cmd.equalsIgnoreCase(name)) {
						if (args.length > 1) {
							String username = args[1];
							Friend isFriend = getFriend(username);
							if (isFriend != null) {
								friends.remove(isFriend);
								Wrapper.logChat(MessageType.NOTIFCATION, "Removed friend " + username + "!");
							} else {
								Wrapper.logChat(MessageType.NOTIFCATION, "You're not friends with " + username + "!");
							}
						} else {
							Wrapper.logChat(MessageType.NOTIFCATION, "Invalid syntax! Use:");
							Wrapper.logChat(MessageType.NOTIFCATION, cmd + " <username> - Removes friend");
						}
						return true;
					}
				}
				return super.onCommand(text, cmd, args);
			}
		});
	}

	public boolean onDisable() {
		return false;
	};

	public static HashSet<Friend> friends = new HashSet<Friend>();

	public void onEvent(Event event) {
		if (event instanceof EventMouseClicked) {
			if (Spookster.instance.moduleManager.getModule(MiddleMouseFriends.class).isEnabled()) {
				EventMouseClicked clicked = (EventMouseClicked) event;
				if (clicked.getButton() == 2) {
					if (PlayerUtil.getEntityOnMouseCurser(5) != null) {
						Entity entity = PlayerUtil.getEntityOnMouseCurser(5);
						if (entity instanceof EntityPlayer) {
							EntityPlayer player = (EntityPlayer) entity;
							if (getFriend(player.getCommandSenderName()) == null) friends.add(new Friend(player.getCommandSenderName(), player.getCommandSenderName()));
							else friends.remove(new Friend(player.getCommandSenderName(), player.getCommandSenderName()));
						}
					}
				}
			}
		}
	}

	public static boolean isFriend(String name) {
		return getFriend(name) == null ? false : !getFriend(name).isAttackable();
	}

	public static Friend getFriend(String name) {
		for (Friend friend : friends) {
			if (friend.getName().equalsIgnoreCase(name)) { return friend; }
		}
		for (Friend friend : friends) {
			if (friend.getAlias().equalsIgnoreCase(name)) { return friend; }
		}
		return null;
	}

	private String FRIENDS = "Friends";
	List<Value> values = Arrays.asList(new Value[] { new Value(FRIENDS, friends, Friend.class) });

	@Override
	public List<Value> getValues() {
		List<Value> tempList = new ArrayList<Value>();
		tempList.addAll(values);
		for (Friend friend : friends) {
			tempList.add(new Value(friend.getName(), false, true));
		}
		return tempList;
	}

	@Override
	public Object getValue(String n) {
		if (n.equals(FRIENDS)) {
			String s = ",";
			for (Friend friend : friends) {
				String friendData = friend.getName() + ";" + friend.getAlias();
				s += friendData + ",";
			}
			return s;
		} else {
			return getFriend(n).isAttackable();
		}
	}

	@Override
	public void setValue(String n, Object v) {
		if (n.equals(FRIENDS)) {
			friends.clear();
			String[] obj = String.valueOf(v).split(",");
			for (String s : obj) {
				if (!s.equals("")) {
					try {
						String[] friendData = s.split(";");
						Friend newFriend = new Friend(friendData[0], friendData[1]);
						friends.add(newFriend);
					} catch (Exception oi) {
						break;
					}
				}
			}
		} else {
			getFriend(n).setAttackable((Boolean) v);
		}
	}

	public class Friend {

		private String name;
		private String alias;
		private boolean attackable = false;

		public Friend(String name, String alias) {
			this.name = name;
			this.alias = alias;
		}

		public String getName() {
			return name;
		}

		public String getAlias() {
			return alias;
		}

		public boolean isAttackable() {
			return attackable;
		}

		public void setAttackable(boolean attackable) {
			this.attackable = attackable;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			Friend other = (Friend) obj;
			if (!getOuterType().equals(other.getOuterType())) return false;
			if (name == null) {
				if (other.name != null) return false;
			} else if (!name.equals(other.name)) return false;
			return true;
		}

		private Friends getOuterType() {
			return Friends.this;
		}

	}

}
