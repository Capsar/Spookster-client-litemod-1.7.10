package net.spookysquad.spookster.utils;

import net.minecraft.util.MathHelper;

import com.google.gson.JsonElement;

public class ValueUtil extends Wrapper {

	public static Object getValue(JsonElement jsonElement) {
		Object obj = null;
		try {
			obj = jsonElement.getAsInt();
			if (Integer.class.isAssignableFrom(obj.getClass())) { return (Integer) obj; }
		} catch (Exception e) {
			try {
				obj = jsonElement.getAsDouble();
				if (Double.class.isAssignableFrom(obj.getClass())) { return (Double) obj; }
			} catch (Exception e2) {
				try {
					obj = jsonElement.getAsFloat();
					if (Float.class.isAssignableFrom(obj.getClass())) { return (Float) obj; }
				} catch (Exception e3) {
					String ss = jsonElement.getAsString();
					if (ss.equalsIgnoreCase("true")) return Boolean.TRUE;
					else if (ss.equalsIgnoreCase("false")) return Boolean.FALSE;
					else return ss;
				}
			}
		}
		return jsonElement.getAsString();
	}

	public static double toDouble(Object currentValue) {
		try {
			return (Double) currentValue;
		} catch (Exception e) {
			try {
				return (Float) currentValue;
			} catch (Exception e2) {
				try {
					return (Integer) currentValue;
				} catch (Exception e3) {
				}
			}
			return 0.0D;
		}
	}

	public static Object getValueForClickGUI(double width, double min, double max, double newValue, Class objectClass) {
		min -= min;
		if (newValue >= max) {
			newValue = max;
		} else if (newValue <= min) {
			newValue = min;
		}
		double w = width * (newValue - min);
		double f = w / (max - min);

		if (Integer.class.isAssignableFrom(objectClass)) { return MathHelper.floor_double(f); }
		if (Float.class.isAssignableFrom(objectClass)) { return (float) (Math.round((Double) f * 100000) / 100000.0D); }
		return f;
	}

}
