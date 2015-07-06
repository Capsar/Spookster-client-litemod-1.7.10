package net.spookysquad.spookster;

import java.io.File;

import net.minecraft.client.Minecraft;

import com.mumfrey.liteloader.Tickable;

public class SpooksterLitemod implements Tickable {

	private Spookster spookster;
	
	// Useless
	public String getVersion() {
		return null;
	}
	
	// Useless
	public String getName() {
		return null;
	}

	public void init(File configPath) {	
		spookster = new Spookster();
		spookster.init();
	}

	public void upgradeSettings(String version, File configPath, File oldConfigPath) {	
		
	}


	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {
		
	}
}
