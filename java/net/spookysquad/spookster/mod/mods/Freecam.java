package net.spookysquad.spookster.mod.mods;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventInOpaqueBlock;
import net.spookysquad.spookster.event.events.EventPreMotion;
import net.spookysquad.spookster.event.events.EventPushOutOfBlocks;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.utils.PlayerUtil;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;

public class Freecam extends Module {

	public EntityOtherPlayerMP freecamPlayer;
	private double posX, posY, posZ;
	private float rotationYaw, rotationPitch;
	private boolean wasSneaking;
	
	public Freecam() {
		super(new String[] { "Freecam" }, "\"Deattach\" your camera from your body", Type.RENDER, Keyboard.KEY_COMMA, 0xFFa7d5aa);
	}
	
	public boolean onEnable() {
		this.posX = PlayerUtil.getPlayer().posX;
		this.posY = PlayerUtil.getPlayer().posY;
		this.posZ = PlayerUtil.getPlayer().posZ;
		this.rotationYaw = PlayerUtil.getPlayer().rotationYaw;
		this.rotationPitch = PlayerUtil.getPlayer().rotationPitch;
		this.wasSneaking = PlayerUtil.getPlayer().isSneaking();
		
		this.freecamPlayer = new EntityOtherPlayerMP(Wrapper.getWorld(), PlayerUtil.getPlayer().getGameProfile());
		this.freecamPlayer.setPositionAndRotation(posX, posY - 1.5, posZ, rotationYaw, rotationPitch);
		this.freecamPlayer.rotationYaw = rotationYaw;
		this.freecamPlayer.rotationYawHead = rotationYaw;
		this.freecamPlayer.rotationPitch = rotationPitch;
		
		updateFreecam();
		
		Wrapper.getWorld().addEntityToWorld(-11333337, freecamPlayer);
		
		return super.onEnable();
	}
	
	public void updateFreecam() {
		freecamPlayer.setSneaking(wasSneaking);
		freecamPlayer.inventory = PlayerUtil.getPlayer().inventory;
		freecamPlayer.setHealth(PlayerUtil.getPlayer().getHealth());
	}
	
	public boolean onDisable() {
		PlayerUtil.getPlayer().capabilities.isFlying = false;
		PlayerUtil.getPlayer().noClip = false;
		this.freecamPlayer.setDead();
		this.freecamPlayer = null;
		PlayerUtil.getWorld().removeEntityFromWorld(-1333337);
		PlayerUtil.getPlayer().noClip = false;
		PlayerUtil.getPlayer().setPositionAndRotation(posX, posY, posZ, rotationYaw, rotationPitch);
		return super.onDisable();
	}

	public void onEvent(Event event) {
		if(event instanceof EventPreMotion) {
			updateFreecam();
			PlayerUtil.getPlayer().capabilities.isFlying = true;
			PlayerUtil.getPlayer().noClip = true;
			
				
			event.cancel();
		}
		
		if(event instanceof EventPushOutOfBlocks) {
			event.cancel();
		}
		
		if(event instanceof EventInOpaqueBlock) {
			event.cancel();
		}
	}

}
