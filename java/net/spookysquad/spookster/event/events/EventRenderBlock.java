package net.spookysquad.spookster.event.events;

import net.minecraft.block.Block;
import net.spookysquad.spookster.event.Event;

public class EventRenderBlock extends Event {

	private Block block;

	public EventRenderBlock(Block block) {
		this.block = block;
	}

}
