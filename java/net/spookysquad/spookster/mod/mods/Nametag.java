package net.spookysquad.spookster.mod.mods;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventRenderNameTag;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Nametag extends Module {

	public Nametag() {
		super(new String[] { "Nametag" }, "NAMETAGS kdshfgjkdtraw7uei56348758sdf", Type.RENDER, Keyboard.KEY_M, -1);
	}

	public void onEvent(Event event) {
		if(event instanceof EventRenderNameTag) {
			EventRenderNameTag ev = (EventRenderNameTag) event;
			if (ev.getEntity() instanceof EntityPlayer) {
				ev.cancel();

				if(ev.getEntity() == Wrapper.getPlayer()) {
					return;
				}
				
				drawTags(ev.getEntity(), ev.getEntity().func_145748_c_().getFormattedText(), ev.getX(),
						ev.getY(), ev.getZ());
			}
		}
	}
	
	public boolean sneak = true;
	public boolean health = true;
	public boolean friends = true;
	public float scaleFactor = 1.6F;
	public float scaleDistance = 16;
	
	public void drawTags(EntityLivingBase entity, String name, double posX, double posY, double posZ) {
		/*if (spooked.friendHandler.isFriendWithEntity(entity) && friends) {
			name = "\247b[FR]\247r | " + name;
		}*/
		if(entity.isSneaking() && sneak) {
			name = "\247c[S]\247r | " + name;
		}
		
		if(health) {
			name = name + " \247a" + (int) (entity.getHealth() + entity.getAbsorptionAmount());
		}
		

		FontRenderer fontRenderer = Wrapper.getFont();
		if(entity.getDistanceToEntity(Wrapper.getPlayer()) > scaleDistance) {
			scaleFactor *= entity.getDistanceToEntity(Wrapper.getPlayer()) / scaleDistance;
		}
		float currentScale = 0.016666668F * scaleFactor;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) posX + 0.0F, (float) posY + entity.height
				+ 0.5F, (float) posZ);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-currentScale, -currentScale, currentScale);
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
		
		if (entity.isInvisible()) {
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

}
