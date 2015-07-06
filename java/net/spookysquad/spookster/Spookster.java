package net.spookysquad.spookster;

import java.io.File;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.spookysquad.spookster.event.EventManager;
import net.spookysquad.spookster.manager.Manager;
import net.spookysquad.spookster.mod.ModuleManager;

public class Spookster {

	private ArrayList<Manager> managers = new ArrayList<Manager>();
	public static Spookster instance;

	public EventManager eventManager;
	public ModuleManager moduleManager;

	public void init() {
		instance = this;
		managers.add(eventManager = new EventManager());
		managers.add(moduleManager = new ModuleManager());

		for (Manager manager : managers) {
			manager.init(this);
		}

		Runtime.getRuntime().addShutdownHook(new Thread("Shutdown Thread") {
			@Override
			public void run() {
				for (Manager manager : managers) {
					manager.deinit(Spookster.this);
				}
			}
		});
	}

	public String getVersion() {
		return "v0.0";
	}

	public String getName() {
		return "LiteAPI";
	}

	public void upgradeSettings(String version, File configPath, File oldConfigPath) {

	}

	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {

	}
}
