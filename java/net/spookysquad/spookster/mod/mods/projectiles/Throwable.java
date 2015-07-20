package net.spookysquad.spookster.mod.mods.projectiles;

import net.minecraft.item.ItemStack;

public abstract interface Throwable {

	public abstract boolean checkItem(ItemStack item);
	
	public abstract float yOffset();
	
	public abstract float getPower();
	
	public abstract float getGravity();
}
