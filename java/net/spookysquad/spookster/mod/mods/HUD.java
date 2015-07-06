package net.spookysquad.spookster.mod.mods;

import net.minecraft.client.gui.FontRenderer;
import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventPostHudRender;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;

public class HUD extends Module {

	public HUD(Spookster spookster) {
		super(spookster, new String[] { "HUD" }, "Displays the Heads up Display", Type.RENDER, Keyboard.KEY_H, 0xFFffffff);
	}

	public void onEvent(Event event) {
		if(event instanceof EventPostHudRender) {
			FontRenderer fontRenderer = Wrapper.getMinecraft().fontRendererObj;
			
			
			fontRenderer.drawStringWithShadow("Spookster", 2, 2, 0xffffff);
			
			int posY = -2;
			
		}
	}

}
