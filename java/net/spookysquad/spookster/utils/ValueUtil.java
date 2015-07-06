package net.spookysquad.spookster.utils;

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

}
