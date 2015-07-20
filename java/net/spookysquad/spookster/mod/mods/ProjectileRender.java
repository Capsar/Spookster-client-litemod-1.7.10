package net.spookysquad.spookster.mod.mods;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.util.Vec3;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.Event3DRender;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class ProjectileRender extends Module {

	public ProjectileRender() {
		super(new String[] { "Projectile Render" }, "Holy niggers.", Type.RENDER, Keyboard.KEY_NONE, 0xFFA06FA3);
	}

	public void onEvent(Event event) {
		if (event instanceof Event3DRender) {
			Event3DRender render = (Event3DRender) event;
			for (Object obj : Wrapper.getWorld().loadedEntityList) {
				if(obj instanceof IProjectile) {
					Entity projectile = (Entity) obj;
					
					if(isAllowed(projectile)) {
						renderProjectile(projectile);
					}
				}
			}
		}
	}

	private boolean isAllowed(Entity ent) {
		return ent != null && !ent.onGround && !ent.isCollided && !ent.isDead && Wrapper.getWorld().loadedEntityList.contains(ent) && !ent.isCollidedHorizontally;
	}

	private void renderProjectile(Entity projectile) {

		double x = projectile.posX, y = projectile.posY, z = projectile.posZ;
		double motionX = projectile.motionX, motionY = projectile.motionY, motionZ = projectile.motionZ;
		int vertexCounter = 0;

		GL11.glPushMatrix();

		//GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(3042);
		GL11.glDisable(3553);

		GL11.glLineWidth(3.5F);
		if(projectile instanceof EntityArrow) {
			GL11.glColor3d(1, 0.6, 0);
		} else if (projectile instanceof EntityEnderPearl) {
			GL11.glColor3d(0, 0.5, 0.5);
		} else if (projectile instanceof EntityPotion) {
			GL11.glColor3d(0.5, 0.5, 0.5);
		} else {
			GL11.glColor3d(1, 1, 1);
		}
		
		
		
		
		GL11.glBegin(GL11.GL_LINE_STRIP);

		while (vertexCounter++ < 200) {
			GL11.glVertex3d(x * 1.0D - RenderManager.renderPosX, y * 1.0D - RenderManager.renderPosY, z * 1.0D - RenderManager.renderPosZ);

			x += motionX;
			y += motionY;
			z += motionZ;
			motionX *= 0.99D;
			motionY *= 0.99D;
			motionZ *= 0.99D;
			motionY -= 0.05D;

			if (Wrapper.getWorld().rayTraceBlocks(Vec3.createVectorHelper(projectile.posX, projectile.posY, projectile.posZ), Vec3.createVectorHelper(x, y, z), true) != null) {
				break;
			}
		}

		GL11.glEnd();

		GL11.glDisable(3042);
		GL11.glEnable(3553);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		//GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glPopMatrix();
	}

}
