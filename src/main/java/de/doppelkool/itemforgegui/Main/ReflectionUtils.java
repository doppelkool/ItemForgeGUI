package de.doppelkool.itemforgegui.Main;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ReflectionUtils {

	private static final String CRAFTBUKKIT_PACKAGE = "org.bukkit.craftbukkit.";
	private static final String VERSION = getServerVersion();

	private static String getServerVersion() {
		// e.g., org.bukkit.craftbukkit.v1_21_R4 -> v1_21_R4
		String name = Bukkit.getServer().getClass().getPackage().getName();
		return name.substring(name.lastIndexOf('.') + 1);
	}

	public static Class<?> getCraftBukkitClass(String className) throws ClassNotFoundException {
		String fullName = CRAFTBUKKIT_PACKAGE + VERSION + "." + className;
		return Class.forName(fullName);
	}

	public static Object newInstance(Class<?> clazz) throws ReflectiveOperationException {
		return clazz.getDeclaredConstructor().newInstance();
	}

	public static Method getMethod(Class<?> clazz, String name, Class<?>... params) throws NoSuchMethodException {
		Method method = clazz.getDeclaredMethod(name, params);
		method.setAccessible(true);
		return method;
	}

	public static Field getField(Class<?> clazz, String name) throws NoSuchFieldException {
		Field field = clazz.getDeclaredField(name);
		field.setAccessible(true);
		return field;
	}
}
