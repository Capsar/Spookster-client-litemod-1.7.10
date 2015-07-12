package net.spookysquad.spookster.mod;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import net.spookysquad.spookster.Spookster;
import net.spookysquad.spookster.event.Event;
import net.spookysquad.spookster.event.Listener;
import net.spookysquad.spookster.event.events.EventKeyPressed;
import net.spookysquad.spookster.event.events.EventMouseClicked;
import net.spookysquad.spookster.manager.Manager;
import net.spookysquad.spookster.mod.HasValues.Value;
import net.spookysquad.spookster.mod.mods.ArmorSwitch;
import net.spookysquad.spookster.mod.mods.Blink;
import net.spookysquad.spookster.mod.mods.ClickGUI;
import net.spookysquad.spookster.mod.mods.ExternalGUI;
import net.spookysquad.spookster.mod.mods.Fly;
import net.spookysquad.spookster.mod.mods.Freecam;
import net.spookysquad.spookster.mod.mods.Friends;
import net.spookysquad.spookster.mod.mods.Fullbright;
import net.spookysquad.spookster.mod.mods.GangsterWalk;
import net.spookysquad.spookster.mod.mods.HUD;
import net.spookysquad.spookster.mod.mods.MobFarm;
import net.spookysquad.spookster.mod.mods.Nametag;
import net.spookysquad.spookster.mod.mods.NoFall;
import net.spookysquad.spookster.mod.mods.Notifications;
import net.spookysquad.spookster.mod.mods.Phase;
import net.spookysquad.spookster.mod.mods.PotionThrower;
import net.spookysquad.spookster.mod.mods.Speed;
import net.spookysquad.spookster.mod.mods.Sprint;
import net.spookysquad.spookster.mod.mods.Step;
import net.spookysquad.spookster.mod.mods.Title;
import net.spookysquad.spookster.mod.mods.Tracers;
import net.spookysquad.spookster.mod.mods.Triggerbot;
import net.spookysquad.spookster.mod.mods.XRay;
import net.spookysquad.spookster.utils.ValueUtil;

import org.lwjgl.input.Keyboard;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ModuleManager extends Manager implements Listener {

	private Spookster spookster;
	private ArrayList<Module> modules = new ArrayList<Module>();

	public void init(Spookster spookster) {
		this.spookster = spookster;
		spookster.eventManager.registerListener(this);
		this.modules.addAll(Arrays.asList(new ArmorSwitch(), new Blink(), new ClickGUI(), new ExternalGUI(), new Fly(), new Freecam(), new Friends(), new Fullbright(), new GangsterWalk(), new HUD(), new MobFarm(),
				new Nametag(), new Notifications(), new NoFall(), new Phase(), new PotionThrower(), new Speed(), new Sprint(), new Step(), new Title(), new Tracers(), new Triggerbot(), new XRay()));
	}

	public void deinit(Spookster spookster) {
		spookster.eventManager.unregisterListener(this);
	}

	/**
	 * Returns the modules ArrayList
	 * 
	 * @return
	 */
	public ArrayList<Module> getModules() {
		return modules;
	}

	/**
	 * Returns a module by class
	 * 
	 * @param moduleClass
	 * @return
	 */
	public Module getModule(Class<? extends Module> moduleClass) {
		for (Module mod : modules) {
			if (mod.getClass() == moduleClass) { return mod; }
		}

		return null;
	}

	public ArrayList<Module> getModulesWithType(Type type) {
		ArrayList<Module> tempList = new ArrayList<Module>();
		for (Module m : getModules()) {
			if (m.getType() == type) tempList.add(m);
		}
		return tempList;
	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof EventKeyPressed) {
			EventKeyPressed pressed = (EventKeyPressed) event;
			if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) && pressed.getKey() == Keyboard.KEY_UP) {
				Spookster.clientEnabled = !Spookster.clientEnabled;
				if (Spookster.clientEnabled) {
					Spookster.instance.loadClientFromFile();
				} else {
					Spookster.instance.disableAndSafeClient();
				}
				return;
			}

			if (Spookster.clientEnabled) {
				for (Module m : getModules()) {
					if (m.getKeyCode() == pressed.getKey()) {
						m.toggle(true);
					}
				}
			}

		} else if (event instanceof EventMouseClicked) {
			EventMouseClicked pressed = (EventMouseClicked) event;
			if (Spookster.clientEnabled) {
				for (Module m : getModules()) {
					if (m.getKeyCode() - 256 == pressed.getButton()) {
						m.toggle(true);
					}
				}
			}
		}
	}

	public void loadModulesFromFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(Spookster.CONFIG_LOCATION));
			Gson gson = new Gson();
			JsonObject root = gson.fromJson(reader, JsonObject.class);
			JsonArray modulesArray = root.get("modules").getAsJsonArray();
			for (Object moduleObject : modulesArray) {
				JsonObject moduleCup = (JsonObject) moduleObject;
				for (Map.Entry<String, JsonElement> entry : moduleCup.entrySet()) {
					Module mod = null;
					for (Module m : modules) {
						if (m.getDisplay().equalsIgnoreCase(entry.getKey())) {
							mod = m;
							break;
						}
					}
					if (mod != null) {
						JsonObject settings = entry.getValue().getAsJsonObject();
						for (Map.Entry<String, JsonElement> setting : settings.entrySet()) {
							try {
								if (setting.getKey().equals("KEY")) {
									mod.setKeyCode(Integer.valueOf(setting.getValue().getAsString()));
								} else if (setting.getKey().equals("STATE")) {
									if (setting.getValue().getAsBoolean()) mod.toggle(true);
								} else if (setting.getKey().equals("VISIBLE")) {
									mod.setVisible(setting.getValue().getAsBoolean());
								} else if (setting.getKey().equals("COLOR")) {
									mod.setColor(setting.getValue().getAsInt());
								} else if (setting.getKey().equals("DESC")) {
									mod.setDesc(setting.getValue().getAsString());
								} else if (setting.getKey().equals("TYPE")) {
									mod.setType(Type.getValueOf(setting.getValue().getAsString().toUpperCase()));
								} else if (setting.getKey().equals("VALUES")) {
									if (mod instanceof HasValues) {
										HasValues hep = (HasValues) mod;
										JsonObject values = setting.getValue().getAsJsonObject();
										for (Map.Entry<String, JsonElement> value : values.entrySet()) {
											hep.setValue(value.getKey(), ValueUtil.getValue(value.getValue()));
										}
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JsonArray safeModules(JsonObject root) {
		JsonArray modules = new JsonArray();
		for (Module m : getModules()) {
			JsonObject moduleObject = new JsonObject();
			JsonObject dataObject = new JsonObject();
			dataObject.addProperty("KEY", m.getKeyCode());
			dataObject.addProperty("STATE", m.isEnabled());
			dataObject.addProperty("COLOR", m.getColor());
			dataObject.addProperty("DESC", m.getDesc());
			dataObject.addProperty("TYPE", m.getType().getName());
			dataObject.addProperty("VISIBLE", m.isVisible());
			if (m instanceof HasValues) {
				HasValues hep = (HasValues) m;
				JsonObject newDataObject = new JsonObject();
				for (Value v : hep.getValues()) {
					if (!v.hasExtraValues()) {
						newDataObject.addProperty(v.getName(), String.valueOf(hep.getValue(v.getName())));
					} else {
						for (Value extraV : v.getOtherValues()) {
							newDataObject.addProperty(extraV.getName(), String.valueOf(hep.getValue(extraV.getName())));
						}
					}
				}
				dataObject.add("VALUES", newDataObject);
			}
			moduleObject.add(m.getDisplay().toLowerCase(), dataObject);
			modules.add(moduleObject);
		}
		return modules;
	}

}
