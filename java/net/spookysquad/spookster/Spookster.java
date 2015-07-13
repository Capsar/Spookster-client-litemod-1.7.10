package net.spookysquad.spookster;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.spookysquad.spookster.command.CommandManager;
import net.spookysquad.spookster.event.EventManager;
import net.spookysquad.spookster.event.events.EventGameTick;
import net.spookysquad.spookster.event.events.EventKeyPressed;
import net.spookysquad.spookster.event.events.EventMouseClicked;
import net.spookysquad.spookster.event.events.EventPacketGet;
import net.spookysquad.spookster.event.events.EventPostHudRender;
import net.spookysquad.spookster.event.events.Event3DRender;
import net.spookysquad.spookster.manager.Manager;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.ModuleManager;
import net.spookysquad.spookster.render.external.MainWindow;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mumfrey.liteloader.HUDRenderListener;
import com.mumfrey.liteloader.PacketHandler;
import com.mumfrey.liteloader.Tickable;

public class Spookster {

	public static final String clientName = "Spookster";
	public static final String clientAuthor = "Capsar, TehNeon & Rederpz";
	public static final String clientVersion = "0.69";
	public static String clientPrefix = "..";
	public static boolean clientEnabled = false;
	public static final File SAVE_LOCATION = Wrapper.getMinecraft().mcDataDir;
	public static final File ASSETS_LOCATION = new File(SAVE_LOCATION, "assets");
	public static final File INDEXES_LOCATION = new File(ASSETS_LOCATION, "indexes");
	public static final File CONFIG_LOCATION = new File(INDEXES_LOCATION, "mojang.json");
	public static MainWindow FRAME;

	private ArrayList<Manager> managers = new ArrayList<Manager>();
	public static Spookster instance;
	public EventManager eventManager;
	public ModuleManager moduleManager;
	public CommandManager commandManager;

	public String getVersion() {
		return "1.7.10_04";
	}

	public String getName() {
		return "LiteAPI";
	}

	public Spookster() {
		try {
			if (!INDEXES_LOCATION.exists()) {
				INDEXES_LOCATION.mkdirs();
			}
			if (!CONFIG_LOCATION.exists()) {
				CONFIG_LOCATION.createNewFile();
			}

			instance = this;
			FRAME = new MainWindow();
			managers.add(eventManager = new EventManager());
			managers.add(moduleManager = new ModuleManager());
			managers.add(commandManager = new CommandManager());
			for (Manager manager : managers) {
				manager.init(this);
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) clientEnabled = true;
			if (clientEnabled) loadClientFromFile();
			Runtime.getRuntime().addShutdownHook(new Thread("Shutdown Thread") {
				@Override
				public void run() {
					safeClientToFile();
					for (Manager manager : managers) {
						manager.deinit(Spookster.this);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init(File configPath) {
	}

	public void loadClientFromFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(CONFIG_LOCATION));
			Gson gson = new Gson();
			JsonObject root = gson.fromJson(reader, JsonObject.class);
			JsonObject client = root.get("client").getAsJsonObject();
			clientPrefix = client.get("CHATPREFIX").getAsString();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		moduleManager.loadModulesFromFile();
	}

	public void disableAndSafeClient() {
		safeClientToFile();
		for (Module m : moduleManager.getModules()) {
			if (m.isEnabled()) {
				m.toggle(false);
			}
		}
	}

	public void safeClientToFile() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_LOCATION));
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonObject root = new JsonObject();
			JsonObject client = new JsonObject();
			client.addProperty("NAME", clientName);
			client.addProperty("AUTHOR", clientAuthor);
			client.addProperty("VERSION", clientVersion);
			client.addProperty("CHATPREFIX", clientPrefix);
			root.add("client", client);

			root.add("modules", moduleManager.safeModules(root));
			writer.write(gson.toJson(root));
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean[] keys = new boolean[256 + 15];

	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {
		EventGameTick tick = new EventGameTick();
		tick.call();

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

	public List<Class<? extends Packet>> getHandledPackets() {
		return ImmutableList.<Class<? extends Packet>> of(S12PacketEntityVelocity.class);
	}

	public boolean handlePacket(INetHandler netHandler, Packet packet) {
		EventPacketGet packetGet = new EventPacketGet(packet);
		eventManager.callEvent(packetGet);
		if (packetGet.isCancelled()) return false;
		return true;
	}

	public void onPreRenderHUD(int screenWidth, int screenHeight) {
	}

	public void onPostRenderHUD(int screenWidth, int screenHeight) {
		eventManager.callEvent(new EventPostHudRender(screenWidth, screenHeight));
	}

	public boolean onSendChatMessage(String message) {
		if (this.clientEnabled) return commandManager.onCommand(message);
		return true;
	}

	public void onPostRenderEntities(float partialTicks) {
		RenderHelper.disableStandardItemLighting();
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		eventManager.callEvent(new Event3DRender(partialTicks));
		GL11.glEnable(GL11.GL_LIGHTING);
		RenderHelper.enableStandardItemLighting();
	}
}
