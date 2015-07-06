package net.spookysquad.spookster;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.spookysquad.spookster.event.EventManager;
import net.spookysquad.spookster.event.events.EventKeyPressed;
import net.spookysquad.spookster.event.events.EventMouseClicked;
import net.spookysquad.spookster.event.events.EventPacketGet;
import net.spookysquad.spookster.manager.Manager;
import net.spookysquad.spookster.mod.ModuleManager;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.google.common.collect.ImmutableList;
import com.mumfrey.liteloader.PacketHandler;

public class Spookster implements PacketHandler {

	private ArrayList<Manager> managers = new ArrayList<Manager>();
	public static Spookster instance;

	public EventManager eventManager;
	public ModuleManager moduleManager;

	public String getVersion() {
		return "v0.0";
	}

	public String getName() {
		return "LiteAPI";
	}

	public void upgradeSettings(String version, File configPath, File oldConfigPath) {

	}

	private boolean[] keys = new boolean[256 + 15];
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {
		if (inGame && minecraft.inGameHasFocus) {
			for (int i = 0; i < 256 + 15; i++) {
				if (i < 256) {
					if (Keyboard.isKeyDown(i) != keys[i]) {
						keys[i] = !keys[i];

						if (keys[i]) {
							EventKeyPressed event = new EventKeyPressed(i);
							eventManager.callEvent(event);
						}
					}
				} else {
					if (Mouse.isButtonDown(i - 256) != keys[i]) {
						keys[i] = !keys[i];
						if (keys[i]) {
							EventMouseClicked event = new EventMouseClicked(i - 256);
							eventManager.callEvent(event);
						}
					}
				}
			}
		}
	}

	@Override
	public void init(File configPath) {
		System.out.println(configPath.getAbsolutePath());

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

	@Override
	public List<Class<? extends Packet>> getHandledPackets() {
		return ImmutableList.<Class<? extends Packet>> of(S12PacketEntityVelocity.class);
	}

	@Override
	public boolean handlePacket(INetHandler netHandler, Packet packet) {
		EventPacketGet packetGet = new EventPacketGet(packet);
		eventManager.callEvent(packetGet);
		if (packetGet.isCancelled()) return false;
		return true;
	}
}
