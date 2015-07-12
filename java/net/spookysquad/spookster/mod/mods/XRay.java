package net.spookysquad.spookster.mod.mods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlowstone;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.init.Blocks;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.mod.HasValues;
import net.spookysquad.spookster.mod.Module;
import net.spookysquad.spookster.mod.Type;
import net.spookysquad.spookster.mod.values.Value;
import net.spookysquad.spookster.utils.Wrapper;

import org.lwjgl.input.Keyboard;

public class XRay extends Module implements HasValues {

	public static ArrayList<Block> blocks = new ArrayList<Block>();
	private int smoothLighting;
	private float brightness;

	public XRay() {
		super(new String[] { "X-Ray", "XRay" }, "Magic shit Nigguh", Type.RENDER, Keyboard.KEY_X, 0xFFB2B2B2);
		Iterator<Block> list = Block.blockRegistry.iterator();
		while (list.hasNext()) {
			Block b = list.next();
			if (b instanceof BlockOre || b instanceof BlockRedstoneOre || b instanceof BlockGlowstone || b instanceof BlockSign || b instanceof BlockPistonBase || b instanceof BlockPistonExtension || b == Blocks.iron_door
					|| b instanceof BlockTripWire || b instanceof BlockTripWireHook || b instanceof BlockMobSpawner) {
				blocks.add(b);
			}
		}
		this.smoothLighting = 2;
	}

	public void onEvent(Event event) {
	}

	public boolean onEnable() {
		this.smoothLighting = Wrapper.getGameSettings().ambientOcclusion;
		this.brightness = getGameSettings().gammaSetting;
		Wrapper.getGameSettings().ambientOcclusion = 0;
		getGameSettings().gammaSetting = 100;
		if (!rerenderLag) {
			getWorld().markBlockRangeForRenderUpdate((int) getPlayer().posX - 200, (int) getPlayer().posY - 200, (int) getPlayer().posZ - 200, (int) getPlayer().posX + 200, (int) getPlayer().posY + 200, (int) getPlayer().posZ + 200);
		} else {
			Wrapper.getMinecraft().renderGlobal.loadRenderers();
		}

		return super.onEnable();
	}

	public boolean onDisable() {
		Wrapper.getGameSettings().ambientOcclusion = this.smoothLighting;
		getGameSettings().gammaSetting = this.brightness;
		if (!rerenderLag) {
			getWorld().markBlockRangeForRenderUpdate((int) getPlayer().posX - 200, (int) getPlayer().posY - 200, (int) getPlayer().posZ - 200, (int) getPlayer().posX + 200, (int) getPlayer().posY + 200, (int) getPlayer().posZ + 200);
		} else {
			Wrapper.getMinecraft().renderGlobal.loadRenderers();
		}

		return super.onDisable();
	}

	public boolean rerenderLag = false;
	private String XRAY_RENDERER = "Rerender Lag";
	private List<Value> values = Arrays.asList(new Value[] { new Value(XRAY_RENDERER, false, true) });

	public List<Value> getValues() {
		return values;
	}

	public Object getValue(String n) {
		if (n.equals(XRAY_RENDERER)) return rerenderLag;
		return null;
	}

	public void setValue(String n, Object v) {
		if (n.equals(XRAY_RENDERER)) rerenderLag = (Boolean) v;
	}

}
