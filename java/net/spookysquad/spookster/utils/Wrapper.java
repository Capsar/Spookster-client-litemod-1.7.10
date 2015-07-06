package net.spookysquad.spookster.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;

public class Wrapper {

	private static Minecraft mc = Minecraft.getMinecraft();
	
	/** 
	 * TODO: Make an instance of a rapper, possibly force him to sing using nigerian folk tunes and then create a new class using his lyrics.
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
}
