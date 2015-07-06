package net.spookysquad.spookster.mod;

public enum Type {

	COMBAT("Combat"), MOVEMENT("Movement"), RENDER("Render"), EXPLOITS("Exploits"), MISC("Misc"), CORE("");
	
	private final String typeName;

	private Type(String name) {
		typeName = name;
	}

	public String getName() {
		return typeName;
	}

}
