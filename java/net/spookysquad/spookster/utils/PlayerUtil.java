package net.spookysquad.spookster.utils;

import net.minecraft.block.Block;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class PlayerUtil extends Wrapper {

	public static void inflictDamage(int blocks) {
		double amount = 1.0D;
		double fallDistance = 0;
		do {
			getPlayer().swingItem();
			PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(getPlayer().posX, getPlayer().boundingBox.minY
					+ amount, getPlayer().posY + amount, getPlayer().posZ, false));
			PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(getPlayer().posX,
					getPlayer().boundingBox.minY, getPlayer().posY, getPlayer().posZ, false));
			if ((fallDistance += amount) >= blocks) break;
		} while (true);
	}

	public static boolean isMoving() {
		return getMinecraft().gameSettings.keyBindLeft.getIsKeyPressed()
				|| getMinecraft().gameSettings.keyBindRight.getIsKeyPressed()
				|| getMinecraft().gameSettings.keyBindForward.getIsKeyPressed()
				|| getMinecraft().gameSettings.keyBindBack.getIsKeyPressed();
	}

    public static Block getBlock(double offset) {
    	return BlockUtil.getBlock(getPlayer().boundingBox.copy().offset(0.0D, offset, 0.0D));
    }
}
