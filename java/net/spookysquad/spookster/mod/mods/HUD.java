package net.spookysquad.spookster.mod.mods;

import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventPostHudRender;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.render.FontUtil;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class HUD extends Module {

	public HUD() {
		super(new String[] { "HUD" }, "Displays the Heads up Display", Type.RENDER, Keyboard.KEY_H, -1);
	}

	public void onEvent(Event event) {
		if(event instanceof EventPostHudRender) {
			if(!Wrapper.getGameSettings().showDebugInfo) {
				FontUtil fontRenderer = new FontUtil();
	
				GL11.glPushMatrix();
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				fontRenderer.drawStringWithShadow("" + Spookster.clientName.charAt(0), 1, 1, 0xFFFFFFFF, 0.35F);
				GL11.glPopMatrix();
				fontRenderer.drawStringWithShadow(Spookster.clientName.substring(1, Spookster.clientName.length()), 10, 5, 0xFFFFFFFF, 0.7F);
				GL11.glPushMatrix();
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				fontRenderer.drawStringWithShadow("(§aWeed§f)", 21, 3, 0xFFFFFFFF, 1.4F);
				GL11.glPopMatrix();
				
				int posY = 14;
				for(Module mod: Spookster.instance.moduleManager.getModules()) {
					if(mod.isEnabled()) {
						if(mod.getColor() != -1) {
							fontRenderer.drawStringWithShadow(mod.getDisplay(), 2, posY, mod.getColor(), 0.7F);
							posY += 9;
						}
					}
				}
			}
		}
	}

}
