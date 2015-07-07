package net.spookysquad.spookster.mod.mods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventMouseClicked;
import net.spookysquad.spookster.event.events.EventPreMotion;
import net.spookysquad.spookster.mod.HasValues;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.mod.HasValues.Value;
import net.spookysquad.spookster.utils.PlayerUtil;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Friends extends Module implements HasValues {

	public Friends() {
		super(new String[] { "Friends" }, "Modules adapt to the fact there are team members.", Type.MISC, -1, -1);
		this.toggle(false);
	}

	public boolean onDisable() {
		return false;
	};
	
	private static HashSet<Friend> friends = new HashSet<Friend>();

	public void onEvent(Event event) {
		if (event instanceof EventMouseClicked) {
			EventMouseClicked clicked = (EventMouseClicked) event;
			if (clicked.getButton() == 2) {
				if (PlayerUtil.getEntityOnMouseCurser(5) != null) {
					Entity entity = PlayerUtil.getEntityOnMouseCurser(5);
					if (entity instanceof EntityPlayer) {
						EntityPlayer player = (EntityPlayer) entity;
						friends.add(new Friend(player.getCommandSenderName(), player.getCommandSenderName()));
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
			if (friend.getName().equals(name)) { return friend; }
		}
		for (Friend friend : friends) {
			if (friend.getAlias().equals(name)) { return friend; }
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
