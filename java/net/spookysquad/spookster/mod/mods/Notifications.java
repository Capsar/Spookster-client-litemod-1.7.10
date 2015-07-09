package net.spookysquad.spookster.mod.mods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventGameTick;
import net.spookysquad.spookster.event.events.EventPostHudRender;
import net.spookysquad.spookster.mod.HasValues;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.render.GuiUtil;

public class Notifications extends Module {

	public static CopyOnWriteArrayList<Entry<String, Long>> notifications = new CopyOnWriteArrayList<Entry<String, Long>>();

	public Notifications() {
		super(new String[] { "Notifications" }, "", Type.RENDER, -1, -1);
	}

	public void onEvent(Event e) {
		if (e instanceof EventGameTick) {
			for(Entry<String, Long> notification: notifications) {
				if(System.nanoTime() / 1E6 - notification.getValue() >= 5000L) {
					notifications.remove(notification);
				}
			}
		} else if (e instanceof EventPostHudRender) {
			EventPostHudRender event = (EventPostHudRender) e;
			
			ArrayList<Entry<String, Long>> notifications = new ArrayList<Entry<String, Long>>();
			
			for(Entry<String, Long> notification: notifications) {
				notifications.add(notification);
			}
			
			Collections.reverse(notifications);
			
			int count = 0;
			for(Entry<String, Long> notification: notifications) {
				GuiUtil.drawBorderedRect(event.getScreenWidth() - getFont().getStringWidth(notification.getKey()) - 4, event.getScreenHeight() - 52 + count + 10, event.getScreenWidth() + 2, event.getScreenHeight() - 54 + count, 0.3F, 0xFF000000, 0x55000000);
				GuiUtil.drawStringWithShadow(notification.getKey(), event.getScreenWidth() - getFont().getStringWidth(notification.getKey()) - 2, event.getScreenHeight() - 52 + count, 0xFFFFFFFF, 0.7F);
				count -= 14;
			}
		}
	}
}
