package net.spookysquad.spookster.mod.mods;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventInOpaqueBlock;
import net.spookysquad.spookster.event.events.EventPreMotion;
import net.spookysquad.spookster.event.events.EventPushOutOfBlocks;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;

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
		this.posX = getPlayer().posX;
		this.posY = getPlayer().posY;
		this.posZ = getPlayer().posZ;
		this.rotationYaw = getPlayer().rotationYaw;
		this.rotationPitch = getPlayer().rotationPitch;
		this.wasSneaking = getPlayer().isSneaking();
		
		this.freecamPlayer = new EntityOtherPlayerMP(getWorld(), getPlayer().getGameProfile());
		this.freecamPlayer.setPositionAndRotation(posX, posY - 1.5, posZ, rotationYaw, rotationPitch);
		this.freecamPlayer.rotationYaw = rotationYaw;
		this.freecamPlayer.rotationYawHead = rotationYaw;
		this.freecamPlayer.rotationPitch = rotationPitch;
		
		updateFreecam();
		
		getWorld().addEntityToWorld(-11333337, freecamPlayer);
		
		return super.onEnable();
	}
	
	public void updateFreecam() {
		freecamPlayer.setSneaking(wasSneaking);
		freecamPlayer.inventory = getPlayer().inventory;
		freecamPlayer.setHealth(getPlayer().getHealth());
	}
	
	public boolean onDisable() {
		getPlayer().capabilities.isFlying = false;
		getPlayer().noClip = false;
		this.freecamPlayer.setDead();
		this.freecamPlayer = null;
		getWorld().removeEntityFromWorld(-1333337);
		getPlayer().noClip = false;
		getPlayer().setPositionAndRotation(posX, posY, posZ, rotationYaw, rotationPitch);
		return super.onDisable();
	}

	public void onEvent(Event event) {
		if(event instanceof EventPreMotion) {
			updateFreecam();
			getPlayer().capabilities.isFlying = true;
			getPlayer().noClip = true;
			
				
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
