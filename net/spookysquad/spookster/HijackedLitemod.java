package net.spookysquad.spookster;

import java.io.File;

import net.minecraft.client.Minecraft;

import com.mumfrey.liteloader.Tickable;

public class HijackedLitemod implements Tickable {

	private SpooksterLitemod spookster;
	
	public String getVersion() {
		return spookster.getVersion();
	}

	public void init(File configPath) {	
		spookster.init(configPath);
	}

	public void upgradeSettings(String version, File configPath, File oldConfigPath) {		
		spookster.upgradeSettings(version, configPath, oldConfigPath);
	}

	public String getName() {
		return spookster.getName();
	}

	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {
		spookster.onTick(minecraft, partialTicks, inGame, clock);
	}
	
}
