package net.spookysquad.spookster.mod;

import org.lwjgl.input.Keyboard;

import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.event.Listener;

public abstract class Module implements Listener {
	private String name;
	private Type type;
	private String desc;
	private int color;
	private int keyCode;
	private boolean state = false;

	public Module(String name, String desc, Type type, String keybind, int color) {
		this.name = name;
		this.desc = desc;
		this.type = type;
		this.color = color;
		this.keyCode = Keyboard.getKeyIndex(keybind);
	}

	public String getName() {
		return this.name;
	}

	public Type getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

	public int getColor() {
		return color;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setName(String moduleName) {
		this.name = moduleName;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public void toggle() {
		this.state = !this.state;
		if (this.state) {
			if (onEnable()) {
				Spookster.instance.eventManager.registerListener(this);
			} else {
				this.state = false;
			}
		} else {
			Spookster.instance.eventManager.unregisterListener(this);
		}
	}

	private boolean onEnable() {
		return true;
	}

	private void onDisable() {

	}

}
