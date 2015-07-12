package net.spookysquad.spookster.mod.mods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.command.Command;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventMouseClicked;
import net.spookysquad.spookster.event.events.EventPreMotion;
import net.spookysquad.spookster.mod.HasValues;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.mod.HasValues.Value;
import net.spookysquad.spookster.render.external.console.MessageType;
import net.spookysquad.spookster.utils.PlayerUtil;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class MiddleMouseFriends extends Module {

	public MiddleMouseFriends() {
		super(new String[] { "Mouse Add" }, "Allows you to middlemouse click to add friends.", Type.FRIENDS, -1, -1);
		this.toggle(false);
	}

	@Override
	public void onEvent(Event event) {
		// TODO Auto-generated method stub
		
	}
}
