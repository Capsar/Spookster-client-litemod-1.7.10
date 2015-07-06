package net.spookysquad.spookster.utils;

import net.minecraft.network.play.client.C03PacketPlayer;

public class PlayerUtil extends Wrapper {

	public static void inflictDamage(int blocks) {
		double amount = 1.0D;
		double fallDistance = 0;
		do {
			getPlayer().swingItem();
			PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(getPlayer().posX, getPlayer().boundingBox.minY + amount, getPlayer().posY + amount, getPlayer().posZ, false));
			PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(getPlayer().posX, getPlayer().boundingBox.minY, getPlayer().posY, getPlayer().posZ, false));
			if((fallDistance += amount) >= blocks) break;
		} while (true);
	}
	
}
