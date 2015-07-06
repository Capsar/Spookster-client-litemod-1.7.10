package net.spookysquad.spookster.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;

public class Wrapper {

	private Minecraft mc = Minecraft.getMinecraft();
	
	/** 
	 * TODO: Make an instance of a rapper, possibly force him to sing using nigerian folk tunes and then create a new class using his lyrics.
	 */
	
	public EntityPlayerSP getPlayer() {
		return mc.thePlayer;
	}
	
	public WorldClient getWorld() {
		return mc.theWorld;
	}
}
