package net.spookysquad.spookster.mod.mods;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.events.EventPreMotion;
import net.spookysquad.spookster.mod.HasValues;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.utils.PacketUtil;
import net.spookysquad.spookster.utils.PlayerUtil;
import net.spookysquad.spookster.utils.TimeUtil;

import org.lwjgl.input.Keyboard;

public class Triggerbot extends Module implements HasValues {

	public Triggerbot() {
		super(new String[] { "Triggerbot" }, "When your cursor is over an enemy, it attacks.", Type.COMBAT, Keyboard.KEY_R,
				0xFF0A9D0F);
	}

	public int aps = 5;
	public int aimspeed = 2;
	public double range = 4.2;
	private static EntityPlayer livingbase;
	TimeUtil time = new TimeUtil();

	@Override
	public void onEvent(Event event) {
		if (event instanceof EventPreMotion) {
			if (PlayerUtil.getEntityOnMouseCurser(range) != null) {
				Entity entity = PlayerUtil.getEntityOnMouseCurser(range);
				if (entity instanceof EntityPlayer) {
					livingbase = (EntityPlayer) entity;
					if (livingbase == null)
						return;
					boolean canAttack = PlayerUtil.canAttack(livingbase, range);
					boolean shouldAim = PlayerUtil.shouldAim(5, livingbase);
					if (canAttack && shouldAim)
						PlayerUtil.smoothAim(livingbase, aimspeed, false);
					if (aps != 0 && canAttack && time.hasDelayRun((1000 / aps))) {
						time.setReset(time.getCurrentTime() + (new Random()).nextInt(150));
						getPlayer().swingItem();
						PacketUtil.addPacket(new C02PacketUseEntity(livingbase, Action.ATTACK));
						PlayerUtil.attackEffectOnEntity(livingbase);
					}
				}
			} else if (livingbase != null) {
				boolean canAttack = PlayerUtil.canAttack(livingbase, 0) && getPlayer().getDistanceToEntity(livingbase) <= 4.4
						&& getPlayer().canEntityBeSeen(livingbase);
				boolean shouldStopToAim = PlayerUtil.shouldAim(60, livingbase);
				if (!canAttack || aimspeed == 0) {
					livingbase = null;
					return;
				} else if (canAttack) {
					if (shouldStopToAim) {
						livingbase = null;
						return;
					}
					PlayerUtil.smoothAim(livingbase, aimspeed, false);
				}
			}
		}
	}

	private String APS = "Attacks per second", AIMSPEED = "Aim Speed", RANGE = "Range";
	private List<Value> values = Arrays.asList(new Value[] { new Value(APS, 0, 20, 1), new Value(AIMSPEED, 0, 30, 1),
			new Value(RANGE, 3.0, 6.0, 0.1F) });

	@Override
	public List<Value> getValues() {
		return values;
	}

	@Override
	public Object getValue(String n) {
		if (n.equals(APS)) return aps;
		else if (n.equals(AIMSPEED)) return aimspeed;
		else if (n.equals(RANGE)) return range;
		return null;
	}

	@Override
	public void setValue(String n, Object v) {
		if (n.equals(APS)) aps = (Integer) v;
		else if (n.equals(AIMSPEED)) aimspeed = (Integer) v;
		else if (n.equals(RANGE)) range = (Math.round((Double) v * 10) / 10.0D);
	}
}
