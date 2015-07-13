package net.spookysquad.spookster.mod.mods;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.Event3DRender;
import net.spookysquad.spookster.mod.HasValues;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.mod.values.Value;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Tracers extends Module implements HasValues {

	public Tracers() {
		super(new String[] { "Tracers" }, "Swag yolo tracers.", Type.RENDER, Keyboard.KEY_I, 0xFFA01FA0);
	}

	public void onEvent(Event event) {
		if (event instanceof Event3DRender) {
			Event3DRender render = (Event3DRender) event;
			for (EntityPlayer player : (List<EntityPlayer>) getWorld().playerEntities) {
				if (player.getHealth() > 0 && !player.isDead && !player.getCommandSenderName().equals(getPlayer().getCommandSenderName())) {
					float particalTicks = Wrapper.orientCamera(render.getPartialTicks());
					double eposX = player.lastTickPosX + (player.posX - player.lastTickPosX) * particalTicks;
					double eposY = player.lastTickPosY + (player.posY - player.lastTickPosY) * particalTicks;
					double eposZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * particalTicks;
					double x = eposX - RenderManager.renderPosX - particalTicks;
					double y = eposY - RenderManager.renderPosY - particalTicks;
					double z = eposZ - RenderManager.renderPosZ - particalTicks;
					drawLines(player, x, y, z);
				}
			}
		}
	}

	public void drawLines(EntityLivingBase entity, double x, double y, double z) {

		GL11.glPushMatrix();

		RenderHelper.disableStandardItemLighting();
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(false);

		GL11.glLineWidth((float) tracerWidth);
		if(Friends.isFriend(entity.getCommandSenderName())) {
			GL11.glColor4f(0, 1, 1, 1);
		} else {
			GL11.glColor4f(1, 1, 1, 1);
		}

		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2d(0, 0);
		GL11.glVertex3d(x, y, z);
		GL11.glEnd();

		if(spine) {
			GL11.glLineWidth((float) spineWidth);
			GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex3d(x, y, z);
			GL11.glVertex3d(x, y + 2, z);
			GL11.glEnd();
		}

		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();

	}
	
	public double tracerWidth = 1f;
	public double spineWidth = 1.5f;
	public boolean spine = true;
	
	private String TRACERWIDTH = "Tracer Width", SPINEWIDTH = "Spine Width", DRAWSPINES = "Draw Spine";
	private List<Value> values = Arrays.asList(new Value[] { new Value(TRACERWIDTH, 0.5, 5.0, 0.1F),
			new Value(SPINEWIDTH, 0.5, 5.0, 0.1F), new Value(DRAWSPINES, false, true) });
	
	public List<Value> getValues() {
		return values;
	}

	@Override
	public Object getValue(String n) {
		if (n.equals(TRACERWIDTH)) return tracerWidth;
		else if (n.equals(SPINEWIDTH)) return spineWidth;
		else if (n.equals(DRAWSPINES)) return spine;
		return null;
	}

	@Override
	public void setValue(String n, Object v) {
		if (n.equals(TRACERWIDTH)) tracerWidth = (Math.round((Double) v * 10) / 10.0D);
		else if (n.equals(SPINEWIDTH)) spineWidth = (Math.round((Double) v * 10) / 10.0D);
		else if (n.equals(DRAWSPINES)) spine = (Boolean) v;
	}
}
