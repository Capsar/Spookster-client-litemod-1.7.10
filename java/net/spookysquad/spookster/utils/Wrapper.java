package net.spookysquad.spookster.utils;

import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import net.spookysquad.spookster.injection.ObfuscationTable;
import net.spookysquad.spookster.mod.mods.Notifications;
import net.spookysquad.spookster.render.external.MainWindow;
import net.spookysquad.spookster.render.external.console.MessageType;

public class Wrapper {

	private static Minecraft mc = Minecraft.getMinecraft();

	/**
	 * TODO: Make an instance of a rapper, possibly force him to sing using
	 * nigerian folk tunes and then create a new class using his lyrics.
	 */

	public static Minecraft getMinecraft() {
		return mc;
	}

	public static EntityPlayerSP getPlayer() {
		return mc.thePlayer;
	}

	public static WorldClient getWorld() {
		return mc.theWorld;
	}

	public static GameSettings getGameSettings() {
		return mc.gameSettings;
	}

	public static PlayerControllerMP getController() {
		return mc.playerController;
	}

	public static FontRenderer getFont() {
		return mc.fontRendererObj;
	}

	public static ScaledResolution getSRes() {
		return new ScaledResolution(getMinecraft(), getMinecraft().displayWidth, getMinecraft().displayHeight);
	}

	public static void logChat(MessageType type, String text) {
		MainWindow.mainConsole.addMessage(text, type);
		Notifications.notifications.add((Entry<String, Long>) getEntry(text, (long) (System.nanoTime() / 1E6)));
	}

	public static Entry getEntry(Object o, Object o2) {
		SimpleEntry entry = new SimpleEntry(o, o2);
		return entry;
	}
	
	public static float orientCamera(float particalTick)
    {
		try {
			Method theMethod = null;
			for(Method method: EntityRenderer.class.getDeclaredMethods()) {
				if(method.getName().equals("orientCamera")) {
					theMethod = method;
					break;
				}
			}
			if(theMethod != null) {
				theMethod.setAccessible(true);
				theMethod.invoke(getMinecraft().entityRenderer, particalTick);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
        /*EntityLivingBase var2 = getMinecraft().renderViewEntity;
        float var3 = var2.yOffset - 1.62F;
        double var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * (double)p_78467_1_;
        double var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * (double)p_78467_1_ - (double)var3;
        double var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * (double)p_78467_1_;
        GL11.glRotatef(getMinecraft().entityRenderer.prevCamRoll + (getMinecraft().entityRenderer.camRoll - getMinecraft().entityRenderer.prevCamRoll) * p_78467_1_, 0.0F, 0.0F, 1.0F);

        if (var2.isPlayerSleeping())
        {
            var3 = (float)((double)var3 + 1.0D);
            GL11.glTranslatef(0.0F, 0.3F, 0.0F);

            if (!getGameSettings().debugCamEnable)
            {
                Block var10 = getWorld().getBlock(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));

                if (var10 == Blocks.bed)
                {
                    int var11 = getWorld().getBlockMetadata(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
                    int var12 = var11 & 3;
                    GL11.glRotatef((float)(var12 * 90), 0.0F, 1.0F, 0.0F);
                }

                GL11.glRotatef(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * p_78467_1_ + 180.0F, 0.0F, -1.0F, 0.0F);
                GL11.glRotatef(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * p_78467_1_, -1.0F, 0.0F, 0.0F);
            }
        }
        else if (getGameSettings().thirdPersonView > 0)
        {
            double var27 = (double)(getMinecraft().entityRenderer.thirdPersonDistanceTemp + (getMinecraft().entityRenderer.thirdPersonDistance - getMinecraft().entityRenderer.thirdPersonDistanceTemp) * p_78467_1_);
            float var13;
            float var28;

            if (getGameSettings().debugCamEnable)
            {
                var28 = getMinecraft().entityRenderer.prevDebugCamYaw + (getMinecraft().entityRenderer.debugCamYaw - getMinecraft().entityRenderer.prevDebugCamYaw) * p_78467_1_;
                var13 = getMinecraft().entityRenderer.prevDebugCamPitch + (getMinecraft().entityRenderer.debugCamPitch - getMinecraft().entityRenderer.prevDebugCamPitch) * p_78467_1_;
                GL11.glTranslatef(0.0F, 0.0F, (float)(-var27));
                GL11.glRotatef(var13, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(var28, 0.0F, 1.0F, 0.0F);
            }
            else
            {
                var28 = var2.rotationYaw;
                var13 = var2.rotationPitch;

                if (getGameSettings().thirdPersonView == 2)
                {
                    var13 += 180.0F;
                }

                double var14 = (double)(-MathHelper.sin(var28 / 180.0F * (float)Math.PI) * MathHelper.cos(var13 / 180.0F * (float)Math.PI)) * var27;
                double var16 = (double)(MathHelper.cos(var28 / 180.0F * (float)Math.PI) * MathHelper.cos(var13 / 180.0F * (float)Math.PI)) * var27;
                double var18 = (double)(-MathHelper.sin(var13 / 180.0F * (float)Math.PI)) * var27;

                for (int var20 = 0; var20 < 8; ++var20)
                {
                    float var21 = (float)((var20 & 1) * 2 - 1);
                    float var22 = (float)((var20 >> 1 & 1) * 2 - 1);
                    float var23 = (float)((var20 >> 2 & 1) * 2 - 1);
                    var21 *= 0.1F;
                    var22 *= 0.1F;
                    var23 *= 0.1F;
                    MovingObjectPosition var24 = getWorld().rayTraceBlocks(Vec3.createVectorHelper(var4 + (double)var21, var6 + (double)var22, var8 + (double)var23), Vec3.createVectorHelper(var4 - var14 + (double)var21 + (double)var23, var6 - var18 + (double)var22, var8 - var16 + (double)var23));

                    if (var24 != null)
                    {
                        double var25 = var24.hitVec.distanceTo(Vec3.createVectorHelper(var4, var6, var8));

                        if (var25 < var27)
                        {
                            var27 = var25;
                        }
                    }
                }

                if (getGameSettings().thirdPersonView == 2)
                {
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                }

                GL11.glRotatef(var2.rotationPitch - var13, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(var2.rotationYaw - var28, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(0.0F, 0.0F, (float)(-var27));
                GL11.glRotatef(var28 - var2.rotationYaw, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(var13 - var2.rotationPitch, 1.0F, 0.0F, 0.0F);
            }
        }
        else
        {
            GL11.glTranslatef(0.0F, 0.0F, -0.1F);
        }

        if (!getGameSettings().debugCamEnable)
        {
            GL11.glRotatef(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * p_78467_1_, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * p_78467_1_ + 180.0F, 0.0F, 1.0F, 0.0F);
        }

        GL11.glTranslatef(0.0F, var3, 0.0F);
        var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * (double)p_78467_1_;
        var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * (double)p_78467_1_ - (double)var3;
        var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * (double)p_78467_1_;
        getMinecraft().entityRenderer.cloudFog = getMinecraft().renderGlobal.hasCloudFog(var4, var6, var8, p_78467_1_);*/
        
        return particalTick;
    }
}
