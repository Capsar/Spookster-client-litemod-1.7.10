package net.spookysquad.spookster.mod.mods;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.Event3DRender;
import net.spookysquad.spookster.event.events.EventRenderNameTag;
import net.spookysquad.spookster.mod.HasValues;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.mod.mods.Friends.Friend;
import net.spookysquad.spookster.render.FontUtil;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Tracers extends Module {

	public Tracers() {
		super(new String[] { "Tracers" }, "Swag yolo tracers.", Type.RENDER, Keyboard.KEY_I, 0xFFA01FA0);
	}

	public void onEvent(Event event) {
		if (event instanceof Event3DRender) {
			Event3DRender render = (Event3DRender) event;
			for (EntityPlayer player : (List<EntityPlayer>) getWorld().playerEntities) {
				if (player.getHealth() > 0 && !player.isDead && !player.getCommandSenderName().equals(getPlayer().getCommandSenderName())) {
					double eposX = player.lastTickPosX + (player.posX - player.lastTickPosX) * render.getPartialTicks();
					double eposY = player.lastTickPosY + (player.posY - player.lastTickPosY) * render.getPartialTicks();
					double eposZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * render.getPartialTicks();
					double x = eposX - RenderManager.renderPosX;
					double y = eposY - RenderManager.renderPosY;
					double z = eposZ - RenderManager.renderPosZ;
					drawLines(player, x, y, z);
				}
			}
		}
	}
	
	public void drawLines(EntityLivingBase entity, double x, double y, double z) {
		
		GL11.glPushMatrix();
		
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(false);
		
		
		
		GL11.glLineWidth(1f);
		GL11.glColor4f(1, 1, 1, 1);
		
		
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		
		GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2d(0.0D, 0);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
		
		
		
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		
		GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glPopMatrix();
		
	}
}
