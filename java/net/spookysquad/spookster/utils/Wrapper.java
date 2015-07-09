package net.spookysquad.spookster.utils;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.spookysquad.spookster.mod.mods.Notifications;
import net.spookysquad.spookster.render.external.console.MessageType;

public class Wrapper {

	private static Minecraft mc = Minecraft.getMinecraft();

	/**
	 * TODO: Make an instance of a rapper, possibly force him to sing using
	 * nigerian folk tunes and then create a new class using his lyrics.
	 */

	public static Minecraft getMinecraft() {
		return mc;
	}

	public static EntityPlayerSP getPlayer() {
		return mc.thePlayer;
	}

	public static WorldClient getWorld() {
		return mc.theWorld;
	}

	public static GameSettings getGameSettings() {
		return mc.gameSettings;
	}

	public static PlayerControllerMP getController() {
		return mc.playerController;
	}

	public static FontRenderer getFont() {
		return mc.fontRendererObj;
	}

	public static ScaledResolution getSRes() {
		return new ScaledResolution(getMinecraft(), getMinecraft().displayWidth, getMinecraft().displayHeight);
	}

	public static void logChat(MessageType type, String text) {
//		MainWindow.mainConsole.addMessage(text, type);
		Notifications.notifications.add((Entry<String, Long>) getEntry(text, (long) (System.nanoTime() / 1E6)));
	}

	public static Entry getEntry(Object o, Object o2) {
		SimpleEntry entry = new SimpleEntry(o, o2);
		return entry;
	}
}
