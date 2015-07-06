package net.spookysquad.spookster;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.mumfrey.liteloader.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.spookysquad.spookster.event.EventManager;
import net.spookysquad.spookster.event.events.EventPacketGet;
import net.spookysquad.spookster.manager.Manager;
import net.spookysquad.spookster.mod.ModuleManager;

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

	public void upgradeSettings(String version, File configPath,
			File oldConfigPath) {

	}

	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame,
			boolean clock) {

	}

	@Override
	public void init(File configPath) {
		System.out.println("Loading up Spookster, Spooky-Squad " + this.getVersion());
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
		if(packetGet.isCancelled())
			return false;
		return true;
	}
}
