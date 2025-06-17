package de.doppelkool.itemforgegui.Main;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ReflectionUtils {

	private static final String CRAFTBUKKIT_BASE = "org.bukkit.craftbukkit.";
	private static String VERSION = null;

	// Cached profile class
	@Getter
	private static Class<?> CRAFT_PLAYER_PROFILE_CLASS;

	private static void getServerVersion() {
		String pkg = Bukkit.getServer().getClass().getPackage().getName();
		VERSION = pkg.substring(pkg.lastIndexOf('.') + 1);
	}

	public static void init() throws IllegalStateException {
		if(VERSION != null) {
			return;
		}
		getServerVersion();

		// Try Paper first
		String[] paperClasses = {
			"com.destroystokyo.paper.profile.CraftPlayerProfile",
			"io.papermc.paper.profile.CraftPlayerProfile"
		};
		for (String name : paperClasses) {
			try {
				CRAFT_PLAYER_PROFILE_CLASS = Class.forName(name);
				return;
			} catch (ClassNotFoundException ignored) {}
		}

		// Fallback to standard CraftBukkit
		String cbName = CRAFTBUKKIT_BASE + VERSION + ".profile.CraftPlayerProfile";
		try {
			CRAFT_PLAYER_PROFILE_CLASS = Class.forName(cbName);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Could not find CraftPlayerProfile class in Paper or CraftBukkit!", e);
		}
	}

	// Other helper methods:
	public static Method getMethod(Class<?> clazz, String name, Class<?>... params) throws NoSuchMethodException {
		Method m = clazz.getDeclaredMethod(name, params);
		m.setAccessible(true);
		return m;
	}
	public static Field getField(Class<?> clazz, String name) throws NoSuchFieldException {
		Field f = clazz.getDeclaredField(name);
		f.setAccessible(true);
		return f;
	}
}
