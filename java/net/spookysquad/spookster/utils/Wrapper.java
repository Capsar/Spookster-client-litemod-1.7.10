package net.spookysquad.spookster.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.Packet;
import net.spookysquad.spookster.render.external.MainWindow;
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

	public static ScaledResolution getScaledResolution() {
		return new ScaledResolution(getMinecraft(), getMinecraft().displayWidth, getMinecraft().displayHeight);
	}

	public static void logChat(MessageType type, String text) {
		MainWindow.mainConsole.addMessage(text, type);
	}
}
