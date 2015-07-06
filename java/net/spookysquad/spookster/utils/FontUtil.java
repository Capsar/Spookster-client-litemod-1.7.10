package net.spookysquad.spookster.utils;

import java.util.regex.Pattern;

import org.lwjgl.opengl.GL11;

public class FontUtil extends Wrapper {

    public static float drawString(String text, float x, float y, int color) {
    	return getFont().drawString(text, (int) x, (int) y, color, false);
    }
    
    public static float drawStringWithShadow(String text, float x, float y, int color) {
    	return getFont().drawString(text, (int) x, (int) y, color, true);
    }
    
    public static float drawStringWithShadow(String text, float x, float y, int color, float width, int shadowColor) {
    	GL11.glTranslated(width, width, 0);
    	drawString(stripColorCodes(text), x, y, shadowColor);
    	GL11.glTranslated(-width, -width, 0);
    	return drawString(text, x, y, color);
    }

    public static float drawStringWithShadow(String text, float x, float y, int color, float width) {
    	return drawStringWithShadow(text, x, y, color, width, (color & 16579836) >> 2 | color & -16777216);
    }
    
    public static float drawCenteredString(String text, float x, float y, int color) {
    	return drawString(text, x - (getFont().getStringWidth(text) / 2), y, color);
    }
    
    public static float drawCenteredStringWithShadow(String text, float x, float y, int color) {
    	return drawStringWithShadow(text, x - (getFont().getStringWidth(text) / 2), y, color);
    }
    
    public static float drawCenteredStringWithShadow(String text, float x, float y, int color, float width) {
    	return drawStringWithShadow(text, x - (getFont().getStringWidth(text) / 2), y, color, width);
    }
    
    public static float drawCenteredStringWithShadow(String text, float x, float y, int color, float width, int shadowColor) {
    	return drawStringWithShadow(text, x - (getFont().getStringWidth(text) / 2), y, color, width, shadowColor);
    }

    private static final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
    private static final Pattern patternColorCode = Pattern.compile("(?i)\\u00A7[0-9A-F]");

    private static String stripColorCodes(String text) {
        return patternColorCode.matcher(text).replaceAll("");
    }
    
    private static String stripControlCodes(String text) {
        return patternControlCode.matcher(text).replaceAll("");
    }
}
