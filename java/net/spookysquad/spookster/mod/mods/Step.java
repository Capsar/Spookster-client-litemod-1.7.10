package net.spookysquad.spookster.mod.mods;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.Event3DRender;
import net.spookysquad.spookster.event.events.EventPreMotion;
import net.spookysquad.spookster.mod.HasValues;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.mod.values.Value;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Step extends Module implements HasValues {

	public Step() {
		super(new String[] { "Step" }, "Lets you Step up blocks", Type.MOVEMENT, Keyboard.KEY_PERIOD, 0xFF8BFFA1);
	}

	private double mX, mZ;
	private int inAirTimer = 0;

	public void onEvent(Event event) {
		double size = 8;
		if (event instanceof Event3DRender) {
			if(jumpStep) {
				if (getPlayer().motionX != 0) mX = getPlayer().motionX;
				if (getPlayer().motionZ != 0) mZ = getPlayer().motionZ;
				double blockX = RenderManager.renderPosX + (mX * size);
				double blockY = RenderManager.renderPosY;
				double blockZ = RenderManager.renderPosZ + (mZ * size);
				double x = blockX - RenderManager.renderPosX;
				double y = blockY - RenderManager.renderPosY;
				double z = blockZ - RenderManager.renderPosZ;
				GL11.glPushMatrix();
				GL11.glDisable(GL11.GL_LIGHTING);
				RenderHelper.disableStandardItemLighting();
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glColor3d(0.3373, 0.0, 0.2);
				GL11.glLineWidth(5.0F);
				GL11.glBegin(GL11.GL_LINE_LOOP);
				GL11.glVertex3d(0, -1.5, 0);
				GL11.glVertex3d(x, y - 1, z);
				GL11.glEnd();
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glPopMatrix();
			}
		} else if (event instanceof EventPreMotion) {
			if (jumpStep) {
				if (getPlayer().motionX != 0) mX = getPlayer().motionX;
				if (getPlayer().motionZ != 0) mZ = getPlayer().motionZ;

				AxisAlignedBB bb = getPlayer().boundingBox.copy().offset(mX * size, 0, mZ * size);
				bb.maxY -= 1;
				if ((canJumpOnBlock(bb) || getPlayer().isCollidedHorizontally) && getPlayer().onGround) {
					getPlayer().jump();
				}
			}

			if (vanillaStep) {
				Wrapper.getPlayer().stepHeight = (float) stepHeight;
			} else {
				Wrapper.getPlayer().stepHeight = 0.6F;
			}
		}
	}

	public boolean onDisable() {
		Wrapper.getPlayer().stepHeight = 0.6F;
		return super.onDisable();
	}

	public boolean canJumpOnBlock(AxisAlignedBB bb) {
		int minX = MathHelper.floor_double(bb.minX);
		int maxX = MathHelper.floor_double(bb.maxX + 1.0D);
		int minY = MathHelper.floor_double(bb.minY);
		int maxY = MathHelper.floor_double(bb.maxY + 1.0D);
		int minZ = MathHelper.floor_double(bb.minZ);
		int maxZ = MathHelper.floor_double(bb.maxZ + 1.0D);
		for (int x = minX; x < maxX; ++x) {
			for (int y = minY; y < maxY; ++y) {
				for (int z = minZ; z < maxZ; ++z) {
					if (getWorld().getBlock(x, y + 2, z).getCollisionBoundingBoxFromPool(getWorld(), x, y + 2, z) == null && getWorld().getBlock(x, y + 1, z).getCollisionBoundingBoxFromPool(getWorld(), x, y + 1, z) == null
							&& getWorld().getBlock(x, y, z).getCollisionBoundingBoxFromPool(getWorld(), x, y, z) != null && (getWorld().getBlock(x, y, z).getBlockBoundsMaxY() - getWorld().getBlock(x, y, z).getBlockBoundsMinY() > 0.5)) { return true; }
				}
			}
		}

		return false;
	}

	public boolean vanillaStep = true;
	public boolean jumpStep = false;
	public boolean bypassStep = false;
	public double stepHeight = 0.5;

	private String STEPHEIGHT = "Step Height", VANILLA = "Vanilla Step", LEGIT = "Jump step", BYPASS = "Bypass step", STEPMODE = "Step Mode";
	private List<Value> values = Arrays.asList(new Value[] { new Value(STEPHEIGHT, 0.5D, 10D, 0.1F), new Value(STEPMODE, false, Arrays.asList(new Value(VANILLA, false, true), new Value(LEGIT, false, true), new Value(BYPASS, false, true))) });

	@Override
	public List<Value> getValues() {
		return values;
	}

	@Override
	public Object getValue(String n) {
		if (n.equals(STEPHEIGHT)) return stepHeight;
		else if (n.equals(VANILLA)) return vanillaStep;
		else if (n.equals(LEGIT)) return jumpStep;
		else if (n.equals(BYPASS)) return bypassStep;
		return null;
	}

	@Override
	public void setValue(String n, Object v) {
		if (n.equals(STEPHEIGHT)) stepHeight = (Math.round((Double) v * 10) / 10.0D);
		else if (n.equals(VANILLA)) vanillaStep = (Boolean) v;
		else if (n.equals(LEGIT)) jumpStep = (Boolean) v;
		else if (n.equals(BYPASS)) bypassStep = (Boolean) v;
	}

}
