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
import net.spookysquad.spookster.utils.AngleUtil;
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
	public int aimSpeed = 2;
	public int aimAssistSpeed = 2;
	public double attackRange = 4.2;
	public double smoothRange = 4.4;
	public boolean swordOnly = true;
	public boolean smoothAssist = true;
	private EntityPlayer entityTarget;
	TimeUtil time = new TimeUtil();

	@Override
	public void onEvent(Event event) {
		if (event instanceof EventPreMotion) {
			if (PlayerUtil.getEntityOnMouseCurser(attackRange) != null) {
				Entity entity = PlayerUtil.getEntityOnMouseCurser(attackRange);
				if (entity instanceof EntityPlayer) {
					entityTarget = (EntityPlayer) entity;
					if (entityTarget == null) return;
					boolean canAttack = PlayerUtil.canAttack(entityTarget, attackRange, swordOnly);
					boolean shouldAim = AngleUtil.shouldAim(5, entityTarget);
					if (canAttack && shouldAim) AngleUtil.smoothAim(entityTarget, aimSpeed, false);
					if (aps != 0 && canAttack && time.hasDelayRun((1000 / aps))) {
						time.setReset(time.getCurrentTime() + (new Random()).nextInt(150));
						getPlayer().swingItem();
						PacketUtil.addPacket(new C02PacketUseEntity(entityTarget, Action.ATTACK));
						PlayerUtil.attackEffectOnEntity(entityTarget);
					}
				}
			} else if (entityTarget != null) {
				if(smoothAssist) {
					boolean canAttack = PlayerUtil.canAttack(entityTarget, 0, swordOnly)
							&& getPlayer().getDistanceToEntity(entityTarget) <= smoothRange && getPlayer().canEntityBeSeen(entityTarget);
					boolean shouldStopToAim = AngleUtil.shouldAim(60, entityTarget);
					if (!canAttack || aimAssistSpeed == 0) {
						entityTarget = null;
						return;
					} else if (canAttack) {
						if (shouldStopToAim) {
							entityTarget = null;
							return;
						}
						AngleUtil.smoothAim(entityTarget, aimAssistSpeed, false);
					}
				}
			}
		}
	}

	private String APS = "Attacks per second", AIMSPEED = "Aim Speed", AIMASSISTSPEED = "Aim Assist Speed", ATTACKRANGE = "Attack Range", SMOOTHRANGE = "Smooth Range", SWORDONLY = "Swords only", SMOOTHAIM = "Smooth aim assist";
	private List<Value> values = Arrays.asList(new Value[] { new Value(APS, 0, 20, 1), new Value(AIMSPEED, 0, 30, 1), new Value(AIMASSISTSPEED, 0, 30, 1),
			new Value(ATTACKRANGE, 3.0, 6.0, 0.1F), new Value(SMOOTHRANGE, 3.0, 6.0, 0.1F), new Value(SWORDONLY, false, true), new Value(SMOOTHAIM, false, true) });

	@Override
	public List<Value> getValues() {
		return values;
	}

	@Override
	public Object getValue(String n) {
		if (n.equals(APS)) return aps;
		else if (n.equals(AIMSPEED)) return aimSpeed;
		else if (n.equals(AIMASSISTSPEED)) return aimAssistSpeed;
		else if (n.equals(ATTACKRANGE)) return attackRange;
		else if (n.equals(SMOOTHRANGE)) return smoothRange;
		else if (n.equals(SWORDONLY)) return swordOnly;
		else if (n.equals(SMOOTHAIM)) return smoothAssist;
		return null;
	}

	@Override
	public void setValue(String n, Object v) {
		if (n.equals(APS)) aps = (Integer) v;
		else if (n.equals(AIMSPEED)) aimSpeed = (Integer) v;
		else if (n.equals(AIMASSISTSPEED)) aimAssistSpeed = (Integer) v;
		else if (n.equals(ATTACKRANGE)) attackRange = (Math.round((Double) v * 10) / 10.0D);
		else if (n.equals(SMOOTHRANGE)) smoothRange = (Math.round((Double) v * 10) / 10.0D);
		else if (n.equals(SWORDONLY)) swordOnly = Boolean.parseBoolean(v.toString());
		else if (n.equals(SMOOTHAIM)) smoothAssist = Boolean.parseBoolean(v.toString());
	}
}
