package net.spookysquad.spookster.mod.mods;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventRenderNameTag;
import net.spookysquad.spookster.mod.HasValues;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.mod.HasValues.Value;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Nametag extends Module implements HasValues {

	public Nametag() {
		super(new String[] { "Nametag" }, "NAMETAGS kdshfgjkdtraw7uei56348758sdf", Type.RENDER, Keyboard.KEY_M, 0xFFCD00CD);
	}

	public void onEvent(Event event) {
		if (event instanceof EventRenderNameTag) {
			EventRenderNameTag ev = (EventRenderNameTag) event;
			if (ev.getEntity() instanceof EntityPlayer) {
				ev.cancel();

				if (ev.getEntity() == Wrapper.getPlayer()) { return; }

				drawTags(ev.getEntity(), ev.getEntity().func_145748_c_().getFormattedText(), ev.getX(), ev.getY(), ev.getZ());
			}
		}
	}

	public boolean sneak = true;
	public boolean health = true;
	public boolean friends = true;
	public boolean invisibles = true;
	public double scaleFactor = 1.6d;
	public int scaleDistance = 16;

	public void drawTags(EntityLivingBase entity, String name, double posX, double posY, double posZ) {
		/*
		 * if (spooked.friendHandler.isFriendWithEntity(entity) && friends) {
		 * name = "\247b[FR]\247r | " + name; }
		 */
		if (entity.isSneaking() && sneak) {
			name = "\247c[S]\247r | " + name;
		}

		if (health) {
			name = name + " \247a" + (int) (entity.getHealth() + entity.getAbsorptionAmount());
		}

		double theScale = scaleFactor;
		FontRenderer fontRenderer = Wrapper.getMinecraft().fontRendererObj;
		if (entity.getDistanceToEntity(Wrapper.getPlayer()) > scaleDistance) {
			theScale *= entity.getDistanceToEntity(Wrapper.getPlayer()) / scaleDistance;
		}
		double currentScale = 0.016666668F * theScale;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) posX + 0.0F, (float) posY + entity.height + 0.5F, (float) posZ);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScaled(-currentScale, -currentScale, currentScale);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		Tessellator var15 = Tessellator.instance;
		byte var16 = 0;

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		var15.startDrawingQuads();
		int var17 = fontRenderer.getStringWidth(name) / 2;
		var15.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.6F);

		if (entity.isInvisible() && invisibles) {
			var15.setColorRGBA_F(0.0F, 0.4F, 1.0F, 0.6F);
		}

		var15.addVertex((double) (-var17 - 1), (double) (-1 + var16), 0.0D);
		var15.addVertex((double) (-var17 - 1), (double) (8 + var16), 0.0D);
		var15.addVertex((double) (var17 + 1), (double) (8 + var16), 0.0D);
		var15.addVertex((double) (var17 + 1), (double) (-1 + var16), 0.0D);
		var15.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, var16, 0xffffff);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, var16, 0xffffff);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	private String SNEAK = "Show Sneak", FRIENDS = "Show Friends", HEALTH = "Show Health", INVISIBLE = "Show Invisible", SCALEFACTOR = "Scale Factor",
			SCALEDISTANCE = "Scale Distance";
	private List<Value> values = Arrays.asList(new Value[] { new Value(SNEAK, false, true), new Value(FRIENDS, false, true),
			new Value(HEALTH, false, true), new Value(INVISIBLE, false, true), new Value(SCALEFACTOR, 0.1f, 4f, 0.1f), new Value(SCALEDISTANCE, 0, 32, 1) });

	@Override
	public List<Value> getValues() {
		return values;
	}

	@Override
	public Object getValue(String n) {
		if (n.equals(SNEAK)) return sneak;
		else if (n.equals(FRIENDS)) return friends;
		else if (n.equals(HEALTH)) return health;
		else if (n.equals(INVISIBLE)) return invisibles;
		else if (n.equals(SCALEFACTOR)) return scaleFactor;
		else if (n.equals(SCALEDISTANCE)) return scaleDistance;
		return null;
	}

	@Override
	public void setValue(String n, Object v) {
		if (n.equals(SNEAK)) sneak = Boolean.parseBoolean(v.toString());
		else if (n.equals(FRIENDS)) friends = Boolean.parseBoolean(v.toString());
		else if (n.equals(HEALTH)) health = Boolean.parseBoolean(v.toString());
		else if (n.equals(INVISIBLE)) invisibles = Boolean.parseBoolean(v.toString());
		else if (n.equals(SCALEFACTOR)) scaleFactor = (Double) v;
		else if (n.equals(SCALEDISTANCE)) scaleDistance = (Integer) v;
	}
}
