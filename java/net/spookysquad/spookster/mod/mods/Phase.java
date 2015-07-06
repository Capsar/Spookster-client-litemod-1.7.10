package net.spookysquad.spookster.mod.mods;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventBoundingBox;
import net.spookysquad.spookster.event.events.EventGameTick;
import net.spookysquad.spookster.event.events.EventInOpaqueBlock;
import net.spookysquad.spookster.event.events.EventPreMotion;
import net.spookysquad.spookster.event.events.EventPushOutOfBlocks;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.utils.PacketUtil;
import net.spookysquad.spookster.utils.PlayerUtil;

import org.lwjgl.input.Keyboard;

public class Phase extends Module {

	public Phase() {
		super(new String[] { "Phase" }, "Collide with blocks and pass through them.", Type.EXPLOITS,
				Keyboard.KEY_P, 0xFFA4A4A4);
	}
	
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventPushOutOfBlocks) {
			EventPushOutOfBlocks event = (EventPushOutOfBlocks) e;
			
			event.cancel();
		}else if (e instanceof EventInOpaqueBlock) {
			EventInOpaqueBlock event = (EventInOpaqueBlock) e;
			
			event.cancel();
		}else if (e instanceof EventBoundingBox) {
			EventBoundingBox event = (EventBoundingBox) e;
			
			if(PlayerUtil.isInsideBlock() && event.getY() > getPlayer().boundingBox.minY - 0.001F) {
				event.setBoundingBox(null);
			}
		}else if (e instanceof EventPreMotion) {			
			if(getPlayer().isCollidedHorizontally && getPlayer().onGround && !PlayerUtil.isInsideBlock()) {
				float dir = getPlayer().rotationYaw;
				
				if(getPlayer().moveForward < 0.0F) {
					dir += 180.0F;
		        }
				
		        if(getPlayer().moveStrafing > 0.0F) {
		        	dir -= 90.0F * (getPlayer().moveForward < 0.0F ? -0.5F : getPlayer().moveForward > 0.0F ? 0.5F : 1.0F);
		        }
		       
		        if(getPlayer().moveStrafing < 0.0F) {
		        	dir += 90.0F * (getPlayer().moveForward < 0.0F ? -0.5F : getPlayer().moveForward > 0.0F ? 0.5F : 1.0F);
		        }
		        
		        float hOff = 0.2F;
		        float vOff = 0.000000001F;
		        float xD = (float)Math.cos((dir + 90.0F) * 3.141592653589793D / 180.0D) * hOff;
		        float yD = vOff;
		        float zD = (float)Math.sin((dir + 90.0F) * 3.141592653589793D / 180.0D) * hOff;
		        
		        double[] topkek = {
		          -0.025000000372529D, 
		          -0.02857142899717604D, 
		          -0.0333333338300387D, 
		          -0.04000000059604645D, 
		          -0.0500000007450581D, 
		          -0.06666666766007741D, 
		          -0.1000000014901161D };
		    
		        for(int i = 0; i < topkek.length; i++) {
		        	PacketUtil.addPacket(new C03PacketPlayer.C04PacketPlayerPosition(getPlayer().posX, getPlayer().boundingBox.minY + topkek[i] * yD, getPlayer().posY + topkek[i] * yD, getPlayer().posZ, getPlayer().onGround));
		         	PacketUtil.addPacket(new C03PacketPlayer.C04PacketPlayerPosition(getPlayer().posX + xD * i, getPlayer().boundingBox.minY, getPlayer().posY, getPlayer().posZ + zD * i, getPlayer().onGround));
		        }
		        
		        getPlayer().setPosition(getPlayer().posX + xD, getPlayer().posY, getPlayer().posZ + zD);
			}
		}
	}
}
