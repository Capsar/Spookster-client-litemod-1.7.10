package net.spookysquad.spookster.utils;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

public class Render3DUtil extends Wrapper {

	public static void orientCamera(float particalTick) {
		EntityLivingBase renderViewEntity = getMinecraft().renderViewEntity;
		float entityHeight = renderViewEntity.yOffset - 1.62F;
		double posX = renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * (double) particalTick;
		double posY = renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * (double) particalTick - (double) entityHeight;
		double posZ = renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * (double) particalTick;
		if (renderViewEntity.isPlayerSleeping()) {
			entityHeight = (float) ((double) entityHeight + 1.0D);
			GL11.glTranslatef(0.0F, 0.3F, 0.0F);
			if (!getMinecraft().gameSettings.debugCamEnable) {
				Block block = getMinecraft().theWorld.getBlock(MathHelper.floor_double(renderViewEntity.posX), MathHelper.floor_double(renderViewEntity.posY), MathHelper.floor_double(renderViewEntity.posZ));
				if (block == Blocks.bed) {
					int metaData = getMinecraft().theWorld.getBlockMetadata(MathHelper.floor_double(renderViewEntity.posX), MathHelper.floor_double(renderViewEntity.posY), MathHelper.floor_double(renderViewEntity.posZ));
					int facing = metaData & 3;
					GL11.glRotatef((float) (facing * 90), 0.0F, 1.0F, 0.0F);
				}
				GL11.glRotatef(renderViewEntity.prevRotationYaw + (renderViewEntity.rotationYaw - renderViewEntity.prevRotationYaw) * particalTick + 180.0F, 0.0F, -1.0F, 0.0F);
				GL11.glRotatef(renderViewEntity.prevRotationPitch + (renderViewEntity.rotationPitch - renderViewEntity.prevRotationPitch) * particalTick, -1.0F, 0.0F, 0.0F);
			}
		} else if (getMinecraft().gameSettings.thirdPersonView > 0) {
			double thridPersonDistance = 4;
			float rotationYaw = renderViewEntity.rotationYaw;
			float rotationPitch = renderViewEntity.rotationPitch;
			if (getMinecraft().gameSettings.thirdPersonView == 2) rotationPitch += 180.0F;
			double var14 = (double) (-MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI)) * thridPersonDistance;
			double var16 = (double) (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI)) * thridPersonDistance;
			double var18 = (double) (-MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI)) * thridPersonDistance;
			for (int var20 = 0; var20 < 8; ++var20) {
				float var21 = (float) ((var20 & 1) * 2 - 1);
				float var22 = (float) ((var20 >> 1 & 1) * 2 - 1);
				float var23 = (float) ((var20 >> 2 & 1) * 2 - 1);
				var21 *= 0.1F;
				var22 *= 0.1F;
				var23 *= 0.1F;
				MovingObjectPosition var24 = getMinecraft().theWorld.rayTraceBlocks(Vec3.createVectorHelper(posX + (double) var21, posY + (double) var22, posZ + (double) var23),
						Vec3.createVectorHelper(posX - var14 + (double) var21 + (double) var23, posY - var18 + (double) var22, posZ - var16 + (double) var23));
				if (var24 != null) {
					double var25 = var24.hitVec.distanceTo(Vec3.createVectorHelper(posX, posY, posZ));
					if (var25 < thridPersonDistance) {
						thridPersonDistance = var25;
					}
				}
			}
			if (getMinecraft().gameSettings.thirdPersonView == 2) GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(renderViewEntity.rotationPitch - rotationPitch, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(renderViewEntity.rotationYaw - rotationYaw, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, 0.0F, (float) (-thridPersonDistance));
			GL11.glRotatef(rotationYaw - renderViewEntity.rotationYaw, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(rotationPitch - renderViewEntity.rotationPitch, 1.0F, 0.0F, 0.0F);
		} else {
			GL11.glTranslatef(0.0F, 0.0F, -0.1F);
		}
		if (!getMinecraft().gameSettings.debugCamEnable) {
			GL11.glRotatef(renderViewEntity.prevRotationPitch + (renderViewEntity.rotationPitch - renderViewEntity.prevRotationPitch) * particalTick, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(renderViewEntity.prevRotationYaw + (renderViewEntity.rotationYaw - renderViewEntity.prevRotationYaw) * particalTick + 180.0F, 0.0F, 1.0F, 0.0F);
		}
	}

}
