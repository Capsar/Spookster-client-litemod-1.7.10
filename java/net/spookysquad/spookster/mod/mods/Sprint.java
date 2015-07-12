package net.spookysquad.spookster.mod.mods;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventGameTick;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.utils.PlayerUtil;

import org.lwjgl.input.Keyboard;

public class Sprint extends Module {

	public Sprint() {
		super("Sprint", "Does sprinting for you", Type.MOVEMENT, Keyboard.KEY_V, 0xFFaaaa00);
	}

	public void onEvent(Event event) {
		if(event instanceof EventGameTick) {
			EntityPlayerSP player = PlayerUtil.getPlayer();
			if(player.onGround && !player.movementInput.sneak && player.movementInput.moveForward >= 0.8F && !player.isSprinting() && (player.getFoodStats().getFoodLevel() > 6.0F || player.capabilities.allowFlying) && !player.isUsingItem() && !player.isPotionActive(Potion.blindness) && !player.isCollidedHorizontally) {
				player.setSprinting(true);
			}
		}
	}

}
