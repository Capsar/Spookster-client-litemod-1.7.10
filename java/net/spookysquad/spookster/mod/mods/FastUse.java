package net.spookysquad.spookster.mod.mods;

import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventPreMotion;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.utils.GameUtil;
import net.spookysquad.spookster.utils.PacketUtil;

public class FastUse extends Module {

	public FastUse() {
		super(new String[] { "FastUse" }, "Use items faster", Type.EXPLOITS, -1, -13845340);
	}
	
	// have a list using capsars values and make it so you can turn shit on/off

	public void onEvent(Event event) {
		if(event instanceof EventPreMotion) {
			if(getPlayer().getItemInUse() != null) {
				// check if item is valid (is a good item from the list)
				if(getPlayer().getItemInUseDuration() >= 10 * GameUtil.getGameSpeed()) {
					for(int i = 0; i < 22; i++) {
						PacketUtil.addPacket(new C03PacketPlayer(true));
					}
					
					if(!(getPlayer().getItemInUse().getItem() instanceof ItemSword)) { // temp until value list thing
						if(getPlayer().getItemInUse().getItem() instanceof ItemBow) {
							PacketUtil.addPacket(new C07PacketPlayerDigging(5, -1, -1, -1, -1));
						}
						getPlayer().stopUsingItem();
					}
				}
			}
		}
	}
	
	public boolean onEnable() {
		return super.onEnable();
	}
	
	public boolean onDisable() {
		return super.onDisable();
	}

}
